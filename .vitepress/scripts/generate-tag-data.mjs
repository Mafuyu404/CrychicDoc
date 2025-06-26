import glob from "fast-glob";
import matter from "gray-matter";
import { readFileSync, writeFileSync, existsSync, mkdirSync } from "fs";
import { resolve, dirname } from "path";
import { fileURLToPath } from "url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

/**
 * Language configuration
 * Add new languages here to support them automatically
 */
const SUPPORTED_LANGUAGES = [
    { code: 'zh', name: '简体中文', dir: 'zh', isDefault: true },
    { code: 'en', name: 'English', dir: 'en' },
    // Add more languages here as needed:
    // { code: 'ja', name: '日本語', dir: 'ja' },
    // { code: 'ko', name: '한국어', dir: 'ko' },
    // { code: 'fr', name: 'Français', dir: 'fr' },
];

/**
 * Extract language from file path with support for configured languages
 */
function extractLanguageFromPath(filePath) {
    const pathParts = filePath.split('/');
    // Get the first directory part
    if (pathParts.length > 0) {
        const firstPart = pathParts[0];
        // Check if it matches any configured language directory
        const langConfig = SUPPORTED_LANGUAGES.find(lang => lang.dir === firstPart);
        if (langConfig) {
            return langConfig.code;
        }
    }
    // Return default language if no match found
    return SUPPORTED_LANGUAGES.find(lang => lang.isDefault)?.code || SUPPORTED_LANGUAGES[0].code;
}

/**
 * Generate tag data for a specific language
 */
function generateLanguageTagData(docsDir, language = null) {
    const tagData = {};

    // Find all markdown files
    const markdownFiles = glob.sync("**/*.md", {
        cwd: docsDir,
        ignore: ["**/node_modules/**", "**/.vitepress/**"],
    });

    markdownFiles.forEach((file) => {
        try {
            const fullPath = resolve(docsDir, file);
            const content = readFileSync(fullPath, "utf-8");
            const { data: frontmatter } = matter(content);

            // Extract language from file path
            const fileLanguage = extractLanguageFromPath(file);
            
            // Skip files that don't match the requested language
            if (language && fileLanguage !== language) {
                return;
            }

            if (frontmatter.tags && Array.isArray(frontmatter.tags)) {
                const pageInfo = {
                    title: frontmatter.title || file.replace(".md", ""),
                    description: frontmatter.description,
                    path: "/" + file.replace(".md", "").replace(/\/index$/, ""),
                    tags: frontmatter.tags,
                    progress: frontmatter.progress,
                    language: fileLanguage,
                };

                frontmatter.tags.forEach((tag) => {
                    if (!tagData[tag]) {
                        tagData[tag] = {
                            name: tag,
                            count: 0,
                            pages: [],
                        };
                    }

                    tagData[tag].count++;
                    tagData[tag].pages.push(pageInfo);
                });
            }
        } catch (error) {
            console.warn(`⚠️  Failed to process ${file}:`, error.message);
        }
    });

    // Sort tags by count (most used first)
    const sortedTags = Object.values(tagData).sort((a, b) => b.count - a.count);

    return {
        tags: tagData,
        sortedTags,
        totalTags: Object.keys(tagData).length,
        totalPages: Object.values(tagData).reduce(
            (sum, tag) => sum + tag.count,
            0
        ),
        language,
        generatedAt: new Date().toISOString(),
    };
}

/**
 * Generate tag data for all supported languages
 */
function generateAllLanguageTagData() {
    console.log("🏷️  Generating tag data for all languages...");
    console.log(`🌐 Supported languages: ${SUPPORTED_LANGUAGES.map(l => l.code).join(', ')}\n`);

    const docsDir = resolve(__dirname, "../../docs");
    const outputDir = resolve(__dirname, "../../docs/public");

    // Ensure output directory exists
    if (!existsSync(outputDir)) {
        mkdirSync(outputDir, { recursive: true });
    }

    const results = {};

    // Generate tag data for each supported language
    for (const langConfig of SUPPORTED_LANGUAGES) {
        try {
            console.log(`📋 Processing ${langConfig.name} (${langConfig.code})...`);
            
            const data = generateLanguageTagData(docsDir, langConfig.code);
            results[langConfig.code] = data;

            // Write language-specific tag data file
            const outputPath = resolve(outputDir, `tag-data-${langConfig.code}.json`);
            writeFileSync(outputPath, JSON.stringify(data, null, 2), 'utf-8');
            
            console.log(`   ✅ Generated ${data.totalTags} tags for ${data.totalPages} pages`);
        } catch (error) {
            console.error(`   ❌ Failed to generate tag data for ${langConfig.name}:`, error);
        }
    }

    // Also generate a combined index file with all languages
    const indexPath = resolve(outputDir, 'tag-data-index.json');
    const indexData = {
        languages: SUPPORTED_LANGUAGES,
        generatedAt: new Date().toISOString(),
        summary: Object.fromEntries(
            Object.entries(results).map(([lang, data]) => [
                lang, 
                { 
                    totalTags: data.totalTags, 
                    totalPages: data.totalPages,
                    language: data.language 
                }
            ])
        )
    };
    writeFileSync(indexPath, JSON.stringify(indexData, null, 2), 'utf-8');

    console.log(`\n🎉 Tag data generation completed! Files written to ${outputDir}`);
    console.log('📊 Summary:');
    
    Object.entries(results).forEach(([lang, data]) => {
        console.log(`   ${lang}: ${data.totalTags} tags, ${data.totalPages} pages`);
    });

    // Print top tags for each language
    console.log('\n🔥 Top tags by language:');
    Object.entries(results).forEach(([lang, data]) => {
        const langConfig = SUPPORTED_LANGUAGES.find(l => l.code === lang);
        console.log(`\n   ${langConfig?.name || lang}:`);
        data.sortedTags.slice(0, 5).forEach((tag, index) => {
            console.log(`     ${index + 1}. ${tag.name} (${tag.count} pages)`);
        });
    });

    return results;
}

/**
 * Legacy function for backward compatibility
 * Generates combined tag data for all languages (old behavior)
 */
function generateTagData() {
    console.log("🏷️  Generating combined tag data (legacy mode)...");

    const docsDir = resolve(__dirname, "../../docs");
    const outputPath = resolve(__dirname, "../../docs/public/tag-data.json");

    const data = generateLanguageTagData(docsDir, null);
    writeFileSync(outputPath, JSON.stringify(data, null, 2));

    console.log(
        `✅ Generated tag data with ${data.totalTags} tags and ${data.totalPages} tagged pages`
    );
    console.log(`📁 Output: ${outputPath}`);

    // Print top tags
    console.log("\n🔥 Top tags:");
    data.sortedTags.slice(0, 10).forEach((tag, index) => {
        console.log(`   ${index + 1}. ${tag.name} (${tag.count} pages)`);
    });

    return data;
}

// Run if this script is executed directly
if (process.argv[1] === fileURLToPath(import.meta.url)) {
    const args = process.argv.slice(2);
    
    if (args.includes('--all-languages') || args.includes('-a')) {
        generateAllLanguageTagData();
    } else if (args.includes('--help') || args.includes('-h')) {
        console.log(`
🏷️  Tag Data Generator

Usage:
  node generate-tag-data.mjs [options]

Options:
  --all-languages, -a    Generate tag data for all supported languages
  --help, -h            Show this help message

Examples:
  node generate-tag-data.mjs                    # Generate combined tag data (legacy)
  node generate-tag-data.mjs --all-languages   # Generate language-specific tag data

Supported Languages:
${SUPPORTED_LANGUAGES.map(l => `  ${l.code}: ${l.name} (${l.dir}/)`).join('\n')}
        `);
    } else {
        // Default behavior for backward compatibility
        generateTagData();
    }
}

export { generateTagData, generateAllLanguageTagData, SUPPORTED_LANGUAGES };
