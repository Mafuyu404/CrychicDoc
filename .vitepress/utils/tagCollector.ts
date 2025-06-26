import glob from "fast-glob";
import matter from "gray-matter";
import { readFileSync, writeFileSync, existsSync } from "fs";
import { resolve, dirname } from "path";
import { mkdirSync } from "fs";

export interface TagInfo {
    name: string;
    count: number;
    pages: PageInfo[];
}

export interface PageInfo {
    title: string;
    description?: string;
    path: string;
    tags: string[];
    progress?: number;
    language?: string;
}

export interface TagData {
    [key: string]: TagInfo;
}

export interface LanguageConfig {
    /** Language code (e.g., 'zh', 'en', 'ja') */
    code: string;
    /** Display name for the language */
    name: string;
    /** Directory name in docs folder */
    dir: string;
    /** Whether this is the default/fallback language */
    isDefault?: boolean;
}

/**
 * Default language configuration
 * Add new languages here to support them automatically
 */
export const SUPPORTED_LANGUAGES: LanguageConfig[] = [
    { code: 'zh', name: '简体中文', dir: 'zh', isDefault: true },
    { code: 'en', name: 'English', dir: 'en' },
    // Add more languages here as needed:
    // { code: 'ja', name: '日本語', dir: 'ja' },
    // { code: 'ko', name: '한국어', dir: 'ko' },
    // { code: 'fr', name: 'Français', dir: 'fr' },
];

/**
 * Get supported language codes
 */
export function getSupportedLanguages(): string[] {
    return SUPPORTED_LANGUAGES.map(lang => lang.code);
}

/**
 * Get language configuration by code
 */
export function getLanguageConfig(code: string): LanguageConfig | undefined {
    return SUPPORTED_LANGUAGES.find(lang => lang.code === code);
}

/**
 * Get default language
 */
export function getDefaultLanguage(): LanguageConfig {
    return SUPPORTED_LANGUAGES.find(lang => lang.isDefault) || SUPPORTED_LANGUAGES[0];
}

/**
 * Extract language from file path with support for configured languages
 */
function extractLanguageFromPath(filePath: string): string {
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
    return getDefaultLanguage().code;
}

/**
 * Validate if a language is supported
 */
export function isLanguageSupported(languageCode: string): boolean {
    return SUPPORTED_LANGUAGES.some(lang => lang.code === languageCode);
}

/**
 * Collect all tags from markdown files with optional language filtering
 */
export function collectTags(docsDir: string = "./docs", language?: string): TagData {
    const tagData: TagData = {};

    // Validate language if provided
    if (language && !isLanguageSupported(language)) {
        console.warn(`Unsupported language: ${language}. Supported languages: ${getSupportedLanguages().join(', ')}`);
        return tagData;
    }

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
                const pageInfo: PageInfo = {
                    title: frontmatter.title || file.replace(".md", ""),
                    description: frontmatter.description,
                    path: "/" + file.replace(".md", ""),
                    tags: frontmatter.tags,
                    progress: frontmatter.progress,
                    language: fileLanguage,
                };

                frontmatter.tags.forEach((tag: string) => {
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
            console.warn(`Failed to process ${file}:`, error);
        }
    });

    return tagData;
}

/**
 * Generate static tag data for client-side usage with language support
 */
export function generateTagData(docsDir: string = "./docs", language?: string) {
    const tagData = collectTags(docsDir, language);

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
 * Generate tag data files for all supported languages
 */
export function generateAllLanguageTagData(docsDir: string = "./docs", outputDir: string = "./docs/public") {
    const results: Record<string, any> = {};
    
    console.log(`🏷️  Generating tag data for languages: ${getSupportedLanguages().join(', ')}`);
    
    // Ensure output directory exists
    if (!existsSync(outputDir)) {
        mkdirSync(outputDir, { recursive: true });
    }

    // Generate tag data for each supported language
    for (const langConfig of SUPPORTED_LANGUAGES) {
        try {
            console.log(`   📋 Processing ${langConfig.name} (${langConfig.code})...`);
            
            const data = generateTagData(docsDir, langConfig.code);
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

    console.log(`🎉 Tag data generation completed! Files written to ${outputDir}`);
    return results;
}

/**
 * Get pages by tag with language filtering
 */
export function getPagesByTag(
    tagName: string,
    docsDir: string = "./docs",
    language?: string
): PageInfo[] {
    const tagData = collectTags(docsDir, language);
    return tagData[tagName]?.pages || [];
}

/**
 * Get all unique tags with language filtering
 */
export function getAllTags(docsDir: string = "./docs", language?: string): string[] {
    const tagData = collectTags(docsDir, language);
    return Object.keys(tagData).sort();
}

