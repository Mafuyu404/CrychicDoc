/**
 * @fileoverview Project configuration for VitePress template
 * @author M1hono
 * @version 1.0.0
 */

/**
 * Main project configuration
 * Modify values below to customize your VitePress site
 */
export const projectConfig: ProjectConfig = {
    name: "CrychicDoc",
    
    /**
     * IMPORTANT: Change this to your repository name for GitHub Pages deployment
     * Format: "/your-repo-name/"
     */
    base: "/",
    
    keyWords: ["Minecraft", "Coding", "DataPack", "wiki", "KubeJS", "Modpack", "Modding"],
    version: "2.0.0",
    author: "PickAID",
    license: "CC BY-SA 4.0",

    /**
     * Favicon configuration
     * Can be a local file path (relative to base) or external URL
     */
    favicon: "https://docs.variedmc.cc/favicon.ico", // or "favicon.ico" or "https://example.com/icon.svg"

    /**
     * Logo configuration
     * Can be a simple string path or an object with light/dark theme logos
     */
    logo: {
        light: "/logo.png",
        dark: "/logodark.png",
        alt: "Site Logo",
    },
    repository: {
        type: "git",
        url: "https://github.com/PickAID/CrychicDoc/",
    },
    homepage: "https://docs.variedmc.cc/",

    defaultCurrency: "CNY",

    /**
     * Language configurations for multi-language support
     * Add or modify languages here to enable i18n functionality
     * See LanguageConfig interface below for detailed field documentation
     */
    languages: [
        {
            code: "zh-CN",
            name: "zh-CN",
            displayName: "简体中文",
            isDefault: true,
            link: "/zh/",
            label: "简体中文",
            fileName: "zh.ts",
            giscusLang: "zh-CN",
        },
        {
            code: "en-US",
            name: "en-US",
            displayName: "English",
            isDefault: false,
            link: "/en/",
            label: "English",
            fileName: "en.ts",
            giscusLang: "en",
        },
    ],

    paths: {
        root: ".",
        docs: "./docs",
        src: "./docs/",
        public: "./docs/public",
        vitepress: "./.vitepress",
        config: "./.vitepress/config",
        theme: "./.vitepress/theme",
        scripts: "./.vitepress/scripts",
        utils: "./.vitepress/utils",
        cache: "./.vitepress/cache",
        build: "./.vitepress/dist",
    },

    /**
     * Algolia search configuration
     * Set up your Algolia credentials to enable search
     */
    algolia: {
        appId: "ATKJZ0G8V5",
        apiKey: "f75b80326d9a5599254436f088bcb548",
        indexName: "mihono",
    },

    /**
     * Feature toggles
     * Enable or disable features as needed
     */
    features: {
        search: true,
        gitChangelog: true,
        mermaid: true,
        drawio: true,
        markmap: true,
        multilingual: true,
        autoSidebar: true,
        editLink: false,
    },

    customSnippetFileNames: [],

    /**
     * Configuration for the "Copy Link" button
     */
    copyLinkConfig: {
        removeLanguage: false,
        },
        
        /**
     * Header social media links
     */
    headerSocialLinks: [
        // {
        //     icon: {
        //         svg: '<svg t="1726836997294" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="15518" width="256" height="256"><path d="M930.37056 641.18784a363.8272 363.8272 0 0 0-86.89152-174.40256 156.65664 156.65664 0 0 0-31.23712-112.54272c-8.35072-129.40288-66.304-201.6512-113.9712-239.63648a299.38688 299.38688 0 0 0-182.8352-64.26112h-11.00288a303.55456 303.55456 0 0 0-184.61184 64.4352c-48.01536 37.98528-106.50624 110.32064-114.66752 239.63648a157.79328 157.79328 0 0 0-31.06304 112.36352 364.51328 364.51328 0 0 0-86.0928 172.09344l-0.79872 4.08576v4.16768c-3.10784 74.11712 14.90944 117.68832 53.248 129.49504l13.1328 3.9936 13.4912-2.4832a104.38144 104.38144 0 0 0 40.82688-18.2784l1.95584 3.28192a124.2624 124.2624 0 0 0-43.3152 100.82304v1.24416a93.2352 93.2352 0 0 0 29.91104 62.1312l2.6624 2.39104 2.92864 2.048a232.8064 232.8064 0 0 0 163.84 39.84896 279.28064 279.28064 0 0 0 142.00832-48.19456 291.34336 291.34336 0 0 0 146.80064 48.10752h15.08864c6.48192 0.61952 13.04576 0.88576 19.43552 0.88576a212.12672 212.12672 0 0 0 126.39232-41.35936l2.22208-1.59744 2.03264-1.86368a94.60224 94.60224 0 0 0 30.80192-62.57152v-1.3312a125.056 125.056 0 0 0-42.69056-100.74112l1.68448-2.92864a96.8704 96.8704 0 0 0 46.23872 18.10432l13.40416 1.68448 12.60544-4.87936c64.09216-24.48896 59.8272-74.98752 48.46592-133.74976z m-67.80416 84.49536a43.7504 43.7504 0 0 1-34.08384-24.76032l-28.48768-38.60992-21.03296 42.9568a468.85888 468.85888 0 0 1-27.68896 48.64l-16.68608 25.6512 26.624 15.08864a70.99392 70.99392 0 0 1 36.47488 66.03264 41.58464 41.58464 0 0 1-13.4912 27.42272 160.3072 160.3072 0 0 1-111.73888 30.17728h-15.08352a238.55616 238.55616 0 0 1-126.03392-43.58144l-8.87296-8.87296h-30.44352l-8.87296 8.87296a226.85696 226.85696 0 0 1-122.30144 43.136 179.5328 179.5328 0 0 1-128.34304-30.4384 40.192 40.192 0 0 1-12.86656-26.624 71.9616 71.9616 0 0 1 36.74624-66.47808l26.624-15.08352-16.77312-25.74336a480.15872 480.15872 0 0 1-27.69408-48.63488l-19.43552-40.2944-28.3136 35.50208a51.01056 51.01056 0 0 1-34.61632 26.00448c-7.72096-2.39104-17.75104-24.04864-15.9744-76.32896A311.8848 311.8848 0 0 1 221.056 494.1312l8.87296-8.87808-1.24416-12.86656A105.25696 105.25696 0 0 1 248.832 384.69632l8.34048-7.99232 0.53248-11.44832C267.82208 157.47584 419.328 103.6032 504.44288 103.6032h11.00288c83.43552 0 234.22976 53.25312 244.08064 261.65248l0.53248 11.62752 8.86784 7.9872a104.23296 104.23296 0 0 1 20.15232 87.51616l-1.23904 12.86656 8.87296 9.3184a311.83872 311.83872 0 0 1 81.46944 156.83072c12.25216 63.63136 4.5312 66.47296-15.616 74.28096z" p-id="15519" fill="#9cca86"></path><path d="M365.09696 700.0576c-15.02208 0-31.75936-2.57536-51.16416-7.87456l-0.49664-0.14336c-11.01824-3.67104-20.74112-8.1664-28.928-13.38368l-0.56832-0.37888c-7.54176-5.27872-14.03392-12.29824-19.09248-20.73088-3.46624-6.06208-5.0944-11.76064-6.528-16.78848l-0.14336-0.56832c-1.50528-6.76352-2.29888-14.38208-2.29888-22.04672 0-1.82272 0.1536-3.8144 0.3328-5.97504 0.19456-2.26304 0.34816-4.21376 0.34816-6.31296v-0.73216l0.1024-0.72192c0.28672-1.98656 0.43008-4.10624 0.56832-6.2976l0.04608-0.73728c0.16384-2.59072 0.34304-5.24288 0.65536-7.90528l0.0512-0.53248c0.70656-11.00288 2.05824-23.84384 3.41504-36.08576 2.7392-23.99232 5.4784-45.2096 5.4784-45.2352l1.28-9.93792 50.32448 5.38624-12.66176 12.70272-0.39936 3.5584a50.01216 50.01216 0 0 1-0.44544 3.09248l-0.10752 0.72704c-0.18432 1.70496-0.36864 3.61472-0.56832 5.69344-0.51712 5.31968-1.13152 11.68384-2.06336 18.70336l-0.90624 8.65792c-1.8432 17.6128-3.93728 37.58592-4.51072 50.7904l-0.0256 0.62976-0.1024 0.61952c-0.54272 3.24608-0.54272 6.7072-0.54272 9.23136 0 5.6832 0.5376 10.34752 1.65376 14.24384 1.01376 3.5328 2.31424 5.95456 4.21888 7.8592 2.27328 2.26816 4.10112 4.0192 6.24128 5.2992 4.3008 2.45248 10.02496 4.88448 16.75264 7.14752 13.77792 4.11648 28.26752 6.41024 40.0896 6.41024 5.72928 0 9.71776-0.07168 12.87168-0.96768 1.6384-0.47104 2.80064-0.85504 3.61984-1.152l6.33856-2.2784 0.41984-6.72256c2.03264-32.6656 3.3792-83.0464 3.3792-94.6944v-13.32224l40.96 1.37216v9.9072c0 0.65024-0.00512 16.11776-0.6912 35.85024l-0.31232 9.2672c-0.5888 17.80736-1.2544 37.71904-2.39104 53.95456l-0.0256 0.56832c-0.0768 5.29408-1.03424 10.3936-2.77504 14.75072-1.46944 3.67104-3.13344 7.82336-5.82656 11.54048-4.9408 7.26016-11.4944 12.69248-19.64544 16.38912a52.45952 52.45952 0 0 1-15.34976 4.92544l-0.57856 0.10752a95.50336 95.50336 0 0 1-19.99872 2.14016zM420.93568 217.8304c-23.66464 0-42.05568 27.60192-42.05568 63.08864s18.39616 63.08864 42.05568 63.08864 42.0608-27.60192 42.0608-63.08864-18.40128-63.08864-42.0608-63.08864zM599.60832 279.32672c2.62656-3.9424 3.94752-6.56896 6.56384-7.8848 1.31584-1.31072 2.6368-1.31072 5.26336-1.31072 1.32096 0 2.62656 0 5.25312 1.31072 2.63168 2.62656 6.57408 6.56896 9.19552 14.45888 2.6368 7.8848 3.94752 17.08544 3.94752 27.5968h32.86528c0-21.02784-3.94752-40.74496-13.14816-56.51456-3.94752-7.8848-9.20064-14.45888-15.7696-19.71712-6.57408-5.25824-14.45376-7.8848-22.33856-7.8848s-15.77984 2.62656-22.34368 7.8848c-9.20064 7.88992-17.08544 18.40128-21.03296 31.54432-5.25312 13.13792-7.87968 27.60192-7.87968 43.37152h32.86528c-1.32608-13.13792 1.30048-24.96512 6.55872-32.85504z" p-id="15520" fill="#9cca86"></path><path d="M533.72416 588.11904c-44.39552 0-91.87328-5.56032-137.31328-16.08704-41.92768-9.70752-82.35008-23.01952-113.8176-37.48352-31.98464-14.50496-57.09312-34.65216-74.69568-59.90912a130.60608 130.60608 0 0 1-15.616-28.09344c-3.48672-8.52992-5.38112-15.28832-5.94944-18.48832l-0.13824-0.7936-0.27136-0.768c-0.14848-0.41472-0.16384-0.46592-0.16384-2.21696 0-1.4336 0.47104-3.75808 0.89088-4.95616l0.21504-0.60416 0.13312-0.62464c0.47104-2.23232 1.2032-3.44576 1.42336-3.69664l1.24928-1.17248 0.73728-1.4336 0.86016-0.75264 4.50048-4.23936 1.37216-6.4512 1.53088-7.46496a187.50976 187.50976 0 0 0 24.51968 33.31072c24.18176 27.84256 56.7296 50.62144 96.55808 67.50208 49.50528 20.98176 110.03392 31.62112 179.90144 31.62112h10.87488c66.91328-1.32608 123.84768-11.00288 169.44128-28.76928 30.14144-10.96704 57.05216-25.79456 79.73888-43.99104 31.92832-24.9344 49.99168-50.57024 59.66848-68.26496 0.82944 1.50016 1.62816 3.0464 2.40128 4.63872 5.74464 12.1856 8.43776 23.65952 8.43776 36.03456 0 7.1424-1.03936 14.8992-3.37408 25.1648-0.65024 3.59424-4.77696 10.57792-6.12864 12.87168-5.6064 9.3952-16.12288 20.77184-30.39232 32.86528-21.88288 19.23584-50.14016 37.56544-81.84832 53.14048-34.01216 16.72192-69.03808 28.09344-104.11008 33.81248-20.29056 3.56864-43.35104 5.2992-70.63552 5.2992z" p-id="15521" fill="#9cca86"></path></svg>',
        //     },
        //     link: "https://qm.qq.com/q/CsR20JGLAc",
        // },
        // {
        //     icon: {
        //         svg: '<svg t="1726837363823" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="16717" width="200" height="200"><path d="M593.664 622.08a383.914667 383.914667 0 0 1-20.650667 4.437333 301.226667 301.226667 0 0 1-113.664-0.426666c-6.570667-1.28-15.872-3.541333-27.861333-6.741334l-39.296 51.072c-96.981333-3.114667-133.845333-68.096-133.845333-68.096 0-144.256 63.189333-261.205333 63.189333-261.205333 63.189333-48.341333 123.306667-47.018667 123.306667-47.018667l17.194666 22.4a47.786667 47.786667 0 0 1 4.778667-0.426666 363.818667 363.818667 0 0 1 98.730667 0.426666l18.858666-22.4s60.16-1.322667 123.306667 47.061334c0 0 63.232 116.906667 63.232 261.162666 0 0-37.333333 64.938667-134.314667 68.096l-42.965333-48.384zM429.909333 469.333333C404.266667 469.333333 384 488.533333 384 512s20.693333 42.666667 45.909333 42.666667c25.6 0 45.866667-19.2 45.866667-42.666667 0.426667-23.466667-20.224-42.666667-45.866667-42.666667z m164.181334 0c-25.6 0-45.866667 19.2-45.866667 42.666667s20.693333 42.666667 45.866667 42.666667c25.642667 0 45.909333-19.2 45.909333-42.666667s-20.266667-42.666667-45.909333-42.666667zM896 981.333333l-212.906667-213.333333H810.666667V170.666667H213.333333v597.333333h469.461334l24.32 85.333333H213.333333a85.333333 85.333333 0 0 1-85.333333-85.333333V170.666667a85.333333 85.333333 0 0 1 85.333333-85.333334h597.333334a85.333333 85.333333 0 0 1 85.333333 85.333334v810.666666z" p-id="16718" fill="#9cca86"></path></svg>',
        //     },
        //     link: "https://discord.gg/uPJHxU46td",
        // },
        {
            icon: "github",
            link: "https://github.com/PickAID/CrychicDoc",
        },
        {
            icon: {
                svg: '<svg t="1726513713225" viewBox="65 35 950 950" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4974" width="2000" height="2000"><path d="M512 960c-246.4 0-448-201.6-448-448s201.6-448 448-448 448 201.6 448 448-201.6 448-448 448z" fill="#D81E06" p-id="4975"></path><path d="M721.664 467.968h-235.52a22.272 22.272 0 0 0-20.736 20.736v51.776c0 10.368 10.368 20.736 20.736 20.736H628.48c10.368 0 20.736 10.304 20.736 20.672v10.368c0 33.664-28.48 62.08-62.144 62.08H392.896a22.272 22.272 0 0 1-20.672-20.672V436.928c0-33.664 28.48-62.08 62.08-62.08h287.36a22.272 22.272 0 0 0 20.736-20.736v-51.84a22.272 22.272 0 0 0-20.736-20.672h-287.36A152.96 152.96 0 0 0 281.6 434.368v287.36c0 10.304 10.368 20.672 20.736 20.672h302.848c75.072 0 137.216-62.08 137.216-137.216v-116.48a22.272 22.272 0 0 0-20.736-20.736z" fill="#FFFFFF" p-id="4976"></path></svg>',
            },
            link: "https://gitee.com/PickAID/CrychicDoc",
        },
        {
            icon: {
                svg: '<svg width="128" height="128" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid meet"><g><path id="svg_1" d="m0.22811,8.42244l0,-2.67626c0,-0.13022 0.00485,-0.25928 0.01372,-0.38725l-0.01372,3.06353l0,0l0,-0.00002zm22.13572,-6.35785c0.87539,0.97663 1.40807,2.26682 1.40807,3.68159l0,12.50765c0,3.04754 -2.47054,5.51808 -5.51808,5.51808l-12.50765,0c-1.52088,0 -2.89798,-0.61536 -3.89611,-1.61059l20.51375,-20.09673l0,0l0.00002,0z" fill="rgb(88, 182, 216)" fill-rule="evenodd" stroke="null"/><path id="svg_2" d="m1.88786,22.19821c-1.02398,-1.00178 -1.65975,-2.39874 -1.65975,-3.94439l0,-12.50765c0,-3.04754 2.47054,-5.51808 5.51808,-5.51808l12.50765,0c1.66068,0 3.14985,0.7337 4.16147,1.89447l-20.52744,20.07565l-0.00001,0z" fill="rgb(134, 193, 85)" fill-rule="evenodd" stroke="null"/><path id="svg_3" d="m19.6569,9.39041l-2.886,0c-0.94354,0.19393 -0.81466,1.06567 -0.81466,1.06567l0,3.24521c0.10339,0.93088 1.00853,0.79334 1.00853,0.79334l4.57694,0l0,1.90834l-5.01086,0c-1.95265,-0.10849 -2.36748,-1.44849 -2.36748,-1.44849c-0.19389,-0.43958 -0.1609,-0.87369 -0.1609,-0.87369l0,-3.56376c0.01292,-2.52116 1.7239,-2.874 1.7239,-2.874c0.29728,-0.10345 1.24123,-0.13795 1.24123,-0.13795l4.62009,0l-1.93077,1.88535l0,0l-0.00002,-0.00002zm-8.4846,0.36788l-2.29919,6.5757l-2.09227,0l-2.43714,-6.5757l-0.02299,6.55271l-1.90834,0l0,-8.80594l3.10391,0l2.25321,6.02391l2.23022,-6.02391l3.17291,0l0,8.85193l-2.00031,0l0,-6.59869l0,0l-0.00001,-0.00001z" fill="rgb(255, 255, 255)" fill-rule="evenodd" stroke="null"/></svg>',
            },
            link: "https://www.mcmod.cn/author/32860.html",
        },
    ],

    /**
     * Edit link configuration
     */
    // editLink: {
    //     pattern:
    //         "https://github.com/M1hono/M1honoVitepressTemplate/edit/main/docs/src/:path",
    //     text: "Edit this page on GitHub",
    // },

    /**
     * Configuration for floating social media buttons
     * Add or modify buttons that appear on the side of the page
     */
    socialButtons: [
        {
            name: "qq",
            title: "Join our QQ Group",
            link: "https://qm.qq.com/q/CsR20JGLAc",
            icon: '<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 32 32"><path fill="#ffffff" d="M29.11 26.278c-.72.087-2.804-3.296-2.804-3.296c0 1.959-1.009 4.515-3.191 6.362c1.052.325 3.428 1.198 2.863 2.151c-.457.772-7.844.493-9.977.252c-2.133.24-9.52.519-9.977-.252c-.565-.953 1.807-1.826 2.861-2.151c-2.182-1.846-3.191-4.403-3.191-6.362c0 0-2.083 3.384-2.804 3.296c-.335-.041-.776-1.853.584-6.231c.641-2.064 1.375-3.78 2.509-6.611C5.792 6.13 8.811.001 15.999.001c7.109.001 10.197 6.008 10.017 13.435c1.132 2.826 1.869 4.553 2.509 6.611c1.361 4.379.92 6.191.584 6.231z"/></svg>',
        },
        {
            name: "discord",
            title: "Join our Discord",
            link: "https://discord.gg/uPJHxU46td",
            icon: '<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 24 24"><path fill="#ffffff" d="m22 24l-5.25-5l.63 2H4.5A2.5 2.5 0 0 1 2 18.5v-15A2.5 2.5 0 0 1 4.5 1h15A2.5 2.5 0 0 1 22 3.5V24M12 6.8c-2.68 0-4.56 1.15-4.56 1.15c1.03-.92 2.83-1.45 2.83-1.45l-.17-.17c-1.69.03-3.22 1.2-3.22 1.2c-1.72 3.59-1.61 6.69-1.61 6.69c1.4 1.81 3.48 1.68 3.48 1.68l.71-.9c-1.25-.27-2.04-1.38-2.04-1.38S9.3 14.9 12 14.9s4.58-1.28 4.58-1.28s-.79 1.11-2.04 1.38l.71.9s2.08.13 3.48-1.68c0 0 .11-3.1-1.61-6.69c0 0-1.53-1.17-3.22-1.2l-.17.17s1.8.53 2.83 1.45c0 0-1.88-1.15-4.56-1.15m-2.07 3.79c.65 0 1.18.57 1.17 1.27c0 .69-.52 1.27-1.17 1.27c-.64 0-1.16-.58-1.16-1.27c0-.7.51-1.27 1.16-1.27m4.17 0c.65 0 1.17.57 1.17 1.27c0 .69-.52 1.27-1.17 1.27c-.64 0-1.16-.58-1.16-1.27c0-.7.51-1.27 1.16-1.27Z"/></svg>',
        },
        {
            name: "forum",
            title: "Visit our Forum",
            link: "https://varied.xyebbs.com",
            icon: '<svg t="1754034976464" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2355" width="200" height="200"><path d="M896 256h-85.333333v384H256v85.333333c0 25.6 17.066667 42.666667 42.666667 42.666667h469.333333l170.666667 170.666667V298.666667c0-25.6-17.066667-42.666667-42.666667-42.666667z m-170.666667 256V128c0-25.6-17.066667-42.666667-42.666666-42.666667H128c-25.6 0-42.666667 17.066667-42.666667 42.666667v597.333333l170.666667-170.666666h426.666667c25.6 0 42.666667-17.066667 42.666666-42.666667z" p-id="2356" fill="#ffffff"></path></svg>'
        },
    ],

    /**
     * Special path configurations for the 'Back' button
     * Defines custom navigation behavior for specific URL patterns
     */
    specialBackPaths: [
        {
            regex: "^/(zh|en|jp)/modpack/kubejs/1\\.20\\.1/KubeJSCourse/.+",
            targetPath: "/{1}/modpack/kubejs/1.20.1/",
        },
        {
            regex: "^/(zh|en|jp)/modpack/kubejs/?$",
            targetPath: "/{1}/",
        },
        {
            regex: "^/(zh|en|jp)/modpack/kubejs/1\\.20\\.1/Introduction/Catalogue$",
            targetPath: "/{1}/modpack/kubejs/1.20.1/",
        },
        {
            regex: "^/(zh|en|jp)/modpack/kubejs/1\\.20\\.1/(?!KubeJSCourse).+",
            targetPath: "/{1}/modpack/kubejs/1.20.1/",
        },
    ],

    /**
     * Footer options configuration
     */
    footerOptions: {
        showIcp: true,
        showPolice: false,
        showLicense: true,
        licenseText: "CC BY-SA 4.0",
        licenseLink: "https://creativecommons.org/licenses/by-sa/4.0/",
        showSiteStats: true,
        siteStatsProvider: "busuanzi",
    },

    /**
     * Draw.io plugin configuration
     */
    drawio: {
        width: "100%",
        height: "600px",
        page: 0,
        darkMode: "auto",
        resize: true,
        pages: true,
        zoom: true,
        layers: false,
        lightbox: true,
        highlight: "#0000ff",
        transparent: false,
    },

    /**
     * Markdown Variables plugin configuration
     */
    mdVar: {
        prefix: "-%",
        noVarPrefix: "\\%",
        persistence: true,
        styling: "default",
    },
};

/**
 * Language configuration interface for multi-language support
 * 
 * @example
 * ```ts
 * const enConfig: LanguageConfig = {
 *   code: "en-US",           // Must match VitePress locale key
 *   name: "en-US",           // Internal identifier, usually same as code
 *   displayName: "English",  // What users see in language switcher
 *   isDefault: true,         // Makes this the default language (use "root" in VitePress)
 *   link: "/en-US/",         // URL path, determines VitePress language routing
 *   label: "English",        // Fallback for displayName in UI components
 *   fileName: "en.ts",       // Translation file name in i18n directory
 *   giscusLang: "en"         // Language for Giscus comment system
 * }
 * ```
 */
export interface LanguageConfig {
    /** ISO 639-1 language code (e.g., "en-US", "zh-CN") - must match VitePress locale configuration */
    code: string;
    
    /** Internal identifier for the language, typically same as code for consistency */
    name: string;
    
    /** Human-readable name shown in language switcher dropdown and UI */
    displayName: string;
    
    /** Set to true for default language (becomes "root" in VitePress config) */
    isDefault?: boolean;
    
    /** URL path prefix like "/en-US/" - determines VitePress language routing behavior */
    link?: string;
    
    /** Display label for UI components, falls back to displayName if not set */
    label?: string;
    
    /** Translation file name (e.g., "en.ts", "zh.ts") for i18n message files */
    fileName?: string;
    
    /** Language code for Giscus comment system integration */
    giscusLang?: string;
}

/**
 * File system paths configuration for VitePress project structure
 * 
 * @example
 * ```ts
 * const paths: PathConfig = {
 *   root: ".",                    // Project root, usually current directory
 *   docs: "./src",               // Where your .md files live
 *   src: "./src",                // Source directory (same as docs typically)
 *   public: "./src/public",      // Static assets (images, favicons, etc.)
 *   vitepress: "./.vitepress",   // VitePress configuration folder
 *   config: "./.vitepress/config", // Configuration files location
 *   theme: "./.vitepress/theme",   // Custom theme files
 *   scripts: "./.vitepress/scripts", // Build and utility scripts
 *   utils: "./.vitepress/utils",    // Helper functions and utilities
 *   cache: "./.vitepress/cache",    // Build cache directory
 *   build: "./.vitepress/dist"      // Final build output directory
 * }
 * ```
 */
export interface PathConfig {
    /** Project root directory, typically "." for current directory */
    root: string;
    
    /** Documentation source directory where .md files are located */
    docs: string;
    
    /** Source files directory, usually same as docs for VitePress */
    src: string;
    
    /** Static assets directory for images, favicons, and other public files */
    public: string;
    
    /** VitePress configuration directory containing all VitePress-specific files */
    vitepress: string;
    
    /** Configuration files directory for project settings and options */
    config: string;
    
    /** Custom theme directory for theme overrides and customizations */
    theme: string;
    
    /** Build and utility scripts directory for automation and tooling */
    scripts: string;
    
    /** Utility functions directory for shared helper functions */
    utils: string;
    
    /** Build cache directory for faster subsequent builds */
    cache: string;
    
    /** Final build output directory where generated site files are placed */
    build: string;
}

/**
 * Configuration for the "Copy Link" button
 */
export interface CopyLinkConfig {
    /** Whether to remove the language key from the copied URL */
    removeLanguage: boolean;
}

/**
 * Configuration for a single social media button
 */
export interface SocialButton {
    /** Unique name for the button (used for i18n keys and CSS classes) */
    name: string;
    /** The text to display as a tooltip on hover */
    title: string;
    /** The URL the button links to */
    link: string;
    /** The raw SVG string for the button's icon */
    icon: string;
}

/**
 * Configuration for special 'Back' button navigation paths
 */
export interface SpecialBackPath {
    /** A string representing the regex to match the current path */
    regex: string;
    /** A template for the target path, where {n} is replaced by the nth capture group from the regex */
    targetPath: string;
}

/**
 * Footer options configuration interface
 */
export interface FooterOptionsConfig {
    /** Whether to show ICP filing information */
    showIcp: boolean;
    /** Whether to show police filing information */
    showPolice: boolean;
    /** Whether to show license information */
    showLicense: boolean;
    /** License text to display */
    licenseText: string;
    /** License link URL */
    licenseLink: string;
    /** Whether to show site statistics (visits, page views) */
    showSiteStats: boolean;
    /** Site statistics provider ('busuanzi' | 'vercount' | 'custom') */
    siteStatsProvider: 'busuanzi' | 'vercount' | 'custom';
}

/**
 * Draw.io plugin configuration interface
 */
export interface DrawioConfig {
    /** Default width of diagrams */
    width: string;
    /** Default height of diagrams */
    height: string;
    /** Start page index */
    page: number;
    /** Dark mode setting */
    darkMode: "light" | "dark" | "auto";
    /** Enable toolbar resize */
    resize: boolean;
    /** Enable toolbar change pages */
    pages: boolean;
    /** Enable toolbar zoom */
    zoom: boolean;
    /** Enable toolbar layers */
    layers: boolean;
    /** Enable toolbar lightbox */
    lightbox: boolean;
    /** Highlight color */
    highlight: string;
    /** Transparent background */
    transparent: boolean;
}

/**
 * Markdown Variables plugin configuration interface
 */
export interface MdVarConfig {
    /** Strings that start with this prefix are treated as variables */
    prefix: string;
    /** Strings that start with this prefix are NOT treated as variables */
    noVarPrefix: string;
    /** Enable variable persistence across pages */
    persistence: boolean;
    /** Styling theme: "default", "thr" (The Hacker Recipes), or custom CSS */
    styling: "default" | "thr" | string;
}

/**
 * Deployment configuration interface for different deployment strategies
 * SSH credentials (host, username, private key) are managed via GitHub repository secrets for security
 * 
 * @example
 * ```ts
 * // GitHub Pages deployment (default) - no additional configuration needed
 * const githubDeployment: DeploymentConfig = {
 *   type: 'github-pages',
 *   server: { remotePath: '', port: 22, excludeFiles: [] },
 *   custom: { deployCommand: '', postDeployCommand: '' }
 * }
 * 
 * // Server deployment via SSH - credentials managed via GitHub secrets
 * const serverDeployment: DeploymentConfig = {
 *   type: 'server',
 *   server: {
 *     remotePath: '/var/www/html',              // Where to deploy files on server
 *     port: 22,                                 // SSH port (usually 22)
 *     excludeFiles: ['.git', 'node_modules', '*.log']  // Files to exclude from deployment
 *   },
 *   custom: { deployCommand: '', postDeployCommand: '' }
 * }
 * // Note: Set SSH_HOST, SSH_USERNAME, SSH_PRIVATE_KEY in GitHub repository secrets
 * 
 * // Custom deployment with user-defined commands
 * const customDeployment: DeploymentConfig = {
 *   type: 'custom',
 *   server: { remotePath: '', port: 22, excludeFiles: [] },
 *   custom: {
 *     deployCommand: 'vercel --prod --dir docs/.vitepress/dist',
 *     postDeployCommand: 'curl -X POST https://your-webhook.com/deployed'
 *   }
 * }
 * ```
 */
export interface DeploymentConfig {
    /** Deployment strategy type: 'github-pages' | 'server' | 'custom' */
    type: "github-pages" | "server" | "custom";
    
    /** Server deployment configuration (only used when type is 'server') - SSH credentials managed via GitHub secrets */
    server: {
        /** Remote path on server where files should be deployed (e.g., '/var/www/html', '/home/user/public_html') */
        remotePath: string;
        /** SSH port number (default: 22, some servers use custom ports like 2222) */
        port: number;
        /** Files and directories to exclude from deployment (e.g., ['.git', 'node_modules', '*.log']) */
        excludeFiles: string[];
    };
    
    /** Custom deployment configuration (only used when type is 'custom') */
    custom: {
        /** Custom deployment command (e.g., 'vercel deploy', 'docker push', 'rsync -avz ...') */
        deployCommand: string;
        /** Optional post-deployment command (e.g., webhooks, cache invalidation, service restarts) */
        postDeployCommand: string;
    };
}

/**
 * Complete project configuration interface containing all VitePress site settings
 * 
 * @example
 * ```ts
 * const config: ProjectConfig = {
 *   name: "my-docs",                    // Site title and project name
 *   base: "/my-repo/",                  // GitHub Pages base path (CRITICAL for deployment)
 *   keyWords: ["docs", "guide"],        // SEO keywords for meta tags
 *   description: "My documentation",    // Site description for SEO
 *   version: "1.0.0",                   // Current project version
 *   author: "Your Name",                // Project author for metadata
 *   license: "MIT",                     // License type
 *   repository: {                       // Git repository information
 *     type: "git",
 *     url: "https://github.com/user/repo"
 *   },
 *   homepage: "https://user.github.io/repo/", // Live site URL
 *   defaultCurrency: "USD",             // For financial components
 *   languages: [...],                   // Multi-language configuration
 *   paths: {...},                       // File system paths
 *   features: {...},                    // Feature toggles
 *   sidebarTags: {...},                 // Sidebar tags configuration
 *   algolia: {...},                     // Search configuration
 *   deployment: {...}                   // Deployment configuration
 * }
 * ```
 */
export interface ProjectConfig {
    /** Project name used in site title, metadata, and branding */
    name: string;
    
    /** Base URL path for deployment - MUST match your repository name for GitHub Pages (e.g., "/my-repo/") */
    base: string;
    
    /** SEO keywords array for meta tags and search engine optimization */
    keyWords: string[];
    
    /** Current project version for display and tracking */
    version: string;
    
    /** Project author name for copyright and metadata */
    author: string;
    
    /** License type (e.g., "MIT", "Apache-2.0") for legal information */
    license: string;
    
    /** Favicon path (relative to base) or external URL */
    favicon: string;

    /** Logo configuration - can be string for single logo or object for light/dark themes */
    logo:
        | string
        | {
              /** Logo for light theme */
              light: string;
              /** Logo for dark theme */
              dark: string;
              /** Alt text for the logo */
              alt?: string;
          };

    /** Git repository information for source links and integrations */
    repository: {
        /** Repository type, typically "git" */
        type: string;
        /** Full repository URL for GitHub integration and edit links */
        url: string;
    };
    
    /** Live site URL for canonical links and social media */
    homepage: string;
    
    /** Default currency code for Bills component and financial calculations */
    defaultCurrency: string;
    
    /** Multi-language configuration array for i18n support */
    languages: LanguageConfig[];
    
    /** File system paths configuration for project structure */
    paths: PathConfig;
    
    /** Feature toggle flags for enabling/disabling optional functionality */
    features: {
        /** Enable Algolia search integration (requires algolia config) */
        search: boolean;
        /** Enable Git-based changelog generation from commit history */
        gitChangelog: boolean;
        /** Enable Mermaid diagram support in markdown */
        mermaid: boolean;
        /** Enable Draw.io diagram support in markdown */
        drawio: boolean;
            /** Enable Markmap diagram support in markdown */
        markmap: boolean;
        /** Enable multi-language support and language switcher */
        multilingual: boolean;
        /** Enable automatic sidebar generation from file structure */
        autoSidebar: boolean;
        /** Enable edit link in page footer */
        editLink: boolean;
    };
    
    /** Custom code snippet file names for hero page floating animation effects */
    customSnippetFileNames?: string[];
    
    /** Algolia search service configuration (leave empty to disable search) */
    algolia: {
        /** Algolia application ID from your Algolia dashboard */
        appId: string;
        /** Algolia search API key (public, not admin key) */
        apiKey: string;
        /** Algolia search index name for your documentation */
        indexName: string;
    };

    /** Configuration for floating social media buttons */
    socialButtons: SocialButton[];

    /** Special path configurations for the 'Back' button */
    specialBackPaths: SpecialBackPath[];

    /** Configuration for the "Copy Link" button */
    copyLinkConfig: CopyLinkConfig;

    /** Social media links in header */
    headerSocialLinks?: Array<{
        icon: string | { svg: string };
        link: string;
        ariaLabel?: string;
    }>;

    /** Edit link configuration */
    editLink?: {
        pattern: string;
        text?: string;
    };

    /** Footer options configuration */
    footerOptions: FooterOptionsConfig;

    /** Draw.io plugin configuration */
    drawio: DrawioConfig;

    /** Markdown Variables plugin configuration */
    mdVar: MdVarConfig;
}

/**
 * Get all configured languages from project configuration
 * @returns Array of all language configurations
 * 
 * @example
 * ```ts
 * const languages = getLanguages();
 * console.log(languages.map(lang => lang.displayName)); // ["English", "简体中文"]
 * ```
 */
export function getLanguages(): LanguageConfig[] {
    return projectConfig.languages;
}

/**
 * Get the default language configuration
 * Returns the language marked as default, or first language as fallback
 * @returns Default language configuration
 * 
 * @example
 * ```ts
 * const defaultLang = getDefaultLanguage();
 * console.log(defaultLang.code); // "en-US"
 * ```
 */
export function getDefaultLanguage(): LanguageConfig {
    return (
        projectConfig.languages.find((lang) => lang.isDefault) ||
        projectConfig.languages[0]
    );
}

/**
 * Get array of all language codes for iteration and validation
 * @returns Array of language codes (e.g., ['en-US', 'zh-CN'])
 * 
 * @example
 * ```ts
 * const codes = getLanguageCodes();
 * if (codes.includes('zh-CN')) {
 *   console.log('Chinese is supported');
 * }
 * ```
 */
export function getLanguageCodes(): string[] {
    return projectConfig.languages.map((lang) => lang.code);
}

/**
 * Get all language links
 * @returns Array of language links (e.g., ['/zh/', '/en/'])
 *
 * @example
 * ```ts
 * const links = getLanguageLinks();
 * // Returns: ['/zh/', '/en/']
 * ```
 */
export function getLanguageLinks(): string[] {
    return projectConfig.languages.map((lang) => lang.link);
}

/**
 * Find language configuration by language code
 * @param code - Language code to search for (e.g., 'en-US', 'zh-CN')
 * @returns Language configuration or undefined if not found
 * 
 * @example
 * ```ts
 * const chinese = getLanguageByCode('zh-CN');
 * if (chinese) {
 *   console.log(chinese.displayName); // "简体中文"
 * }
 * ```
 */
export function getLanguageByCode(code: string): LanguageConfig | undefined {
    return projectConfig.languages.find((lang) => lang.code === code);
}

/**
 * Generate VitePress-compatible locales configuration
 * Converts our language config to the format VitePress expects
 * @returns VitePress locales configuration object
 * 
 * @example
 * ```ts
 * const locales = getLocalesConfig();
 * // Returns: { 
 * //   root: { label: 'English', lang: 'en-US', link: '/' },
 * //   'zh-CN': { label: '简体中文', lang: 'zh-CN', link: '/zh-CN/' }
 * // }
 * ```
 */
export function getLocalesConfig() {
    const locales: Record<string, any> = {};
    
    projectConfig.languages.forEach((lang) => {
        const key = lang.isDefault ? "root" : lang.code;
        locales[key] = {
            label: lang.label || lang.displayName,
            lang: lang.name,
            link: lang.link || (lang.isDefault ? "/" : `/${lang.code}/`),
        };
    });
    
    return locales;
}

/**
 * Get the default currency setting for financial components
 * @returns Default currency code (e.g., 'CNY', 'USD', 'EUR')
 * 
 * @example
 * ```ts
 * const currency = getDefaultCurrency();
 * console.log(`Using ${currency} for calculations`); // "Using CNY for calculations"
 * ```
 */
export function getDefaultCurrency(): string {
    return projectConfig.defaultCurrency;
}

/**
 * Get file system paths configuration for build tools and utilities
 * @returns Complete path configuration object
 * 
 * @example
 * ```ts
 * const paths = getPaths();
 * console.log(paths.docs); // "./src"
 * console.log(paths.build); // "./.vitepress/dist"
 * ```
 */
export function getPaths(): PathConfig {
    return projectConfig.paths;
}

/**
 * Check if a specific feature is enabled in the configuration
 * @param feature - Feature name to check (e.g., 'search', 'mermaid', 'gitChangelog')
 * @returns True if feature is enabled, false otherwise
 * 
 * @example
 * ```ts
 * if (isFeatureEnabled('search')) {
 *   console.log('Search is enabled');
 * }
 * 
 * if (isFeatureEnabled('mermaid')) {
 *   // Initialize Mermaid diagrams
 * }
 * ```
 */
export function isFeatureEnabled(
    feature: keyof typeof projectConfig.features
): boolean {
    return projectConfig.features[feature];
}

/**
 * Get consolidated project information for metadata and integrations
 * @returns Object containing basic project info, repository data, and Algolia config
 * 
 * @example
 * ```ts
 * const info = getProjectInfo();
 * console.log(info.name); // "vitepress-M1hono-template"
 * console.log(info.repository.url); // "https://github.com/..."
 * ```
 */
export function getProjectInfo() {
    return {
        name: projectConfig.name,
        base: projectConfig.base,
        version: projectConfig.version,
        author: projectConfig.author,
        license: projectConfig.license,
        favicon: projectConfig.favicon,
        logo: projectConfig.logo,
        repository: projectConfig.repository,
        homepage: projectConfig.homepage,
        headerSocialLinks: projectConfig.headerSocialLinks,
        editLink: projectConfig.editLink,
        footerOptions: projectConfig.footerOptions,
        drawio: projectConfig.drawio,
        mdVar: projectConfig.mdVar,
        algolia: {
            appId: projectConfig.algolia.appId,
            apiKey: projectConfig.algolia.apiKey,
            indexName: projectConfig.algolia.indexName,
        },
    };
}

/**
 * Get the copy link configuration
 * @returns The copy link configuration object
 */
export function getCopyLinkConfig(): CopyLinkConfig {
    return projectConfig.copyLinkConfig || { removeLanguage: true };
}

/**
 * Get the social button configurations
 * @returns Array of social button configurations
 */
export function getSocialButtons(): SocialButton[] {
    return projectConfig.socialButtons || [];
}

/**
 * Get the special back path configurations
 * @returns Array of special back path configurations
 */
export function getSpecialBackPaths(): SpecialBackPath[] {
    return projectConfig.specialBackPaths || [];
}

/**
 * Extracts the language code from a given URL path.
 * It matches the path against the `link` property of the configured languages.
 * @param path - The URL path (e.g., '/zh-CN/guide/').
 * @returns The corresponding language code (e.g., 'zh-CN') or the default language code if no match is found.
 */
export function getLangCodeFromLink(path: string): string {
    const defaultLang = getDefaultLanguage();
    
    // Extract language code from path like /zh/, /en/, /zh-CN/
    const match = path.match(/^\/([a-z]{2}(?:-[A-Z]{2})?)\//);
    if (match) {
        return match[1];
    }
    
    // If no language code found in path, return default
    return defaultLang.code;
}

/**
 * Get the appropriate search locale key for a language
 * Returns 'root' for default language, language code for others
 * @param langCode - Language code (e.g., 'en-US', 'zh-CN')
 * @returns 'root' for default language, or the language code for non-default languages
 * 
 * @example
 * ```ts
 * const searchKey = getSearchLocaleKey('en-US'); // returns 'root' if en-US is default
 * const searchKey = getSearchLocaleKey('zh-CN'); // returns 'zh-CN' if not default
 * ```
 */
export function getSearchLocaleKey(langCode: string): string {
    const defaultLang = getDefaultLanguage();
    return langCode === defaultLang.code ? "root" : langCode;
}

/**
 * Dynamically generate VitePress locales configuration
 * Imports and combines all language configurations from the lang directory
 * @param useRootForDefault - Whether to use 'root' key for default language (VitePress standard) or explicit language codes
 * @returns Promise resolving to VitePress-compatible locales configuration
 * 
 * @example
 * ```ts
 * // In config.mts with standard VitePress i18n
 * import { generateLocalesConfig } from './config/project-config';
 *
 * export default defineConfig({
 *   ...commonConfig,
 *   locales: await generateLocalesConfig(true)
 * });
 *
 * // In config.mts with explicit language codes (legacy)
 * export default defineConfig({
 *   ...commonConfig,
 *   locales: await generateLocalesConfig(false)
 * });
 * ```
 */
export async function generateLocalesConfig(
    useRootForDefault: boolean = false
) {
    const locales: Record<string, any> = {};

    for (const lang of projectConfig.languages) {
        try {
            // Dynamic import based on fileName from language config
            const langModule = await import(
                /* @vite-ignore */ `./lang/${lang.fileName}`
            );

            // Try multiple possible keys to find the language configuration
            const possibleKeys = [
                lang.code.replace("-", "_"), // en-US -> en_US, zh-CN -> zh_CN
                lang.fileName?.replace(".ts", "").replace("-", "_"), // en.ts -> en, zh.ts -> zh
                lang.code, // en-US, zh-CN
                lang.name.replace("-", "_"), // fallback to name field
            ];

            let langConfig = null;

            // Try each possible key until we find a match
            for (const key of possibleKeys) {
                if (langModule[key as keyof typeof langModule]) {
                    langConfig = langModule[key as keyof typeof langModule];
                    break;
                }
            }

            if (langConfig) {
                // Choose locale key based on mode:
                // - useRootForDefault: use 'root' for default language (VitePress standard)
                // - !useRootForDefault: use explicit language codes (legacy compatibility)
                const localeKey =
                    useRootForDefault && lang.isDefault ? "root" : lang.code;
                locales[localeKey] = {
                    label: lang.displayName,
                    ...(langConfig as any),
                };
            } else {
                console.warn(
                    `Language configuration not found for ${
                        lang.code
                    }. Available exports: ${Object.keys(langModule).join(", ")}`
                );
            }
        } catch (error) {
            console.warn(
                `Failed to load language configuration for ${lang.code}:`,
                error
            );
        }
    }

    return locales;
}

/**
 * Automatically discover and import all language modules
 * Uses dynamic imports based on project configuration
 * @returns Promise resolving to object containing all language configurations and search locales
 * 
 * @example
 * ```ts
 * const result = await autoDiscoverLanguageModules();
 * console.log(result.langModules); // { en_US: {...}, zh_CN: {...} }
 * console.log(result.searchLocales); // { root: {...}, 'zh-CN': {...} }
 * ```
 */
export async function autoDiscoverLanguageModules(): Promise<{
    langModules: Record<string, any>;
    searchLocales: Record<string, any>;
}> {
    const langModules: Record<string, any> = {};
    const searchLocales: Record<string, any> = {};

    for (const lang of projectConfig.languages) {
        if (!lang.fileName) {
            console.warn(
                `No fileName specified for language ${lang.code}, skipping`
            );
            continue;
        }

        try {
            // Dynamic import based on fileName from language config
            const langModule = await import(
                /* @vite-ignore */ `./lang/${lang.fileName}`
            );

            // Try multiple possible export keys for language config
            const possibleKeys = [
                lang.code.replace("-", "_"), // en-US -> en_US, zh-CN -> zh_CN
                lang.fileName.replace(".ts", "").replace("-", "_"), // en.ts -> en, zh.ts -> zh
                lang.code, // en-US, zh-CN
                lang.name.replace("-", "_"), // fallback to name field
            ];

            // Find the actual export and map it to the expected key
            let foundConfig = null;
            for (const key of possibleKeys) {
                if (langModule[key]) {
                    foundConfig = langModule[key];
                    // Use standardized key format (lang_code with underscores)
                    langModules[lang.code.replace("-", "_")] = foundConfig;
                    break;
                }
            }

            if (!foundConfig) {
                console.warn(
                    `No valid export found for ${
                        lang.code
                    }. Available exports: ${Object.keys(langModule).join(", ")}`
                );
            }

            // Also try to extract search configuration if it exists
            if (langModule.search) {
                // Use the search configuration directly as exported
                // The language files should manage their own key structure
                Object.assign(searchLocales, langModule.search);
            }
        } catch (error) {
            console.warn(
                `Failed to load language module for ${lang.code}:`,
                error
            );
        }
    }

    return { langModules, searchLocales };
}

/**
 * Fully automated VitePress locales configuration generation
 * Automatically discovers, imports, and configures all language modules
 * @param useRootForDefault - Whether to use 'root' key for default language (VitePress standard) or explicit language codes
 * @returns Promise resolving to VitePress-compatible locales configuration and search locales
 * 
 * @example
 * ```ts
 * // In config.mts - completely automated!
 * import { generateLocalesConfigAuto } from './config/project-config';
 *
 * const { locales, searchLocales } = await generateLocalesConfigAuto(false);
 * export default defineConfig({
 *   ...commonConfig,
 *   locales
 * });
 * ```
 */
export async function generateLocalesConfigAuto(
    useRootForDefault: boolean = false
) {
    const { langModules, searchLocales } = await autoDiscoverLanguageModules();
    const locales = generateLocalesConfigFromModules(
        langModules,
        useRootForDefault
    );
    return { locales, searchLocales };
}

/**
 * Generate locales configuration from provided language modules
 * @param langModules - Object containing imported language configurations
 * @param useRootForDefault - Whether to use 'root' key for default language (VitePress standard) or explicit language codes
 * @returns VitePress-compatible locales configuration object
 * 
 * @example
 * ```ts
 * // Manual approach (when you want to control module imports)
 * import { en_US } from './config/lang/en';
 * import { zh_CN } from './config/lang/zh';
 * import { generateLocalesConfigFromModules } from './config/project-config';
 *
 * const langModules = { en_US, zh_CN };
 *
 * export default defineConfig({
 *   ...commonConfig,
 *   locales: generateLocalesConfigFromModules(langModules, false)
 * });
 * ```
 */
export function generateLocalesConfigFromModules(
    langModules: Record<string, any>,
    useRootForDefault: boolean = false
) {
    const locales: Record<string, any> = {};

    for (const lang of projectConfig.languages) {
        // Try multiple possible keys to find the language configuration
        const possibleKeys = [
            lang.code.replace("-", "_"), // en-US -> en_US, zh-CN -> zh_CN
            lang.fileName?.replace(".ts", "").replace("-", "_"), // en.ts -> en, zh.ts -> zh
            lang.code, // en-US, zh-CN
            lang.name.replace("-", "_"), // fallback to name field
        ];

        let langConfig = null;
        let usedKey = "";

        // Try each possible key until we find a match
        for (const key of possibleKeys) {
            if (langModules[key as keyof typeof langModules]) {
                langConfig = langModules[
                    key as keyof typeof langModules
                ] as any;
                usedKey = key as string;
                break;
            }
        }

        if (langConfig) {
            // Choose locale key based on mode:
            // - useRootForDefault: use 'root' for default language, link-based codes for others
            // - !useRootForDefault: use explicit language codes (legacy compatibility)
            let localeKey: string;
            if (useRootForDefault && lang.isDefault) {
                localeKey = "root";
            } else if (useRootForDefault) {
                // Use language code extracted from link (e.g., '/en/' -> 'en')
                localeKey = getLangCodeFromLink(lang.link);
            } else {
                localeKey = lang.code;
            }
            locales[localeKey] = {
                label: lang.displayName,
                ...(langConfig as any),
            };
        } else {
            console.warn(
                `Language configuration not found for ${
                    lang.code
                }. Tried keys: ${possibleKeys.join(", ")}`
            );
            console.warn(
                `Available keys in langModules: ${Object.keys(langModules).join(
                    ", "
                )}`
            );
        }
    }

    return locales;
}

/**
 * Create auto-import helper for language modules
 * Generates import statements and langModules object based on project configuration
 * @returns Object containing import code and langModules object structure
 * 
 * @example
 * ```ts
 * // This helps you generate the imports automatically
 * const helper = createAutoImportHelper();
 * console.log(helper.imports); // Shows what imports you need
 * console.log(helper.moduleMapping); // Shows the langModules structure
 * ```
 */
export function createAutoImportHelper() {
    const imports: string[] = [];
    const moduleMapping: string[] = [];

    for (const lang of projectConfig.languages) {
        if (!lang.fileName) continue;

        // Generate import statement
        const moduleVarName = lang.code.replace("-", "_");
        const filePath = `./config/lang/${lang.fileName.replace(".ts", "")}`;
        imports.push(`import { ${moduleVarName} } from "${filePath}"`);

        // Generate module mapping
        moduleMapping.push(`    ${moduleVarName}`);
    }

    const langModulesCode = `const langModules = {\n${moduleMapping.join(
        ",\n"
    )}\n};`;

    return {
        imports: imports.join("\n"),
        langModulesCode,
        moduleMapping,
        // For immediate use
        getRequiredImports: () => {
            const result: Record<string, any> = {};
            // This would be used in conjunction with actual imports
            return result;
        },
    };
}

/**
 * @deprecated Use generateLocalesConfigFromModules or generateLocalesConfigAuto instead
 * Synchronously generate VitePress locales configuration
 * For use in environments where dynamic imports are not available
 */
export function generateLocalesConfigSync(
    langModules: Record<string, any>,
    useRootForDefault: boolean = false
) {
    console.warn(
        "generateLocalesConfigSync is deprecated. Use generateLocalesConfigFromModules or generateLocalesConfigAuto instead."
    );
    return generateLocalesConfigFromModules(langModules, useRootForDefault);
}