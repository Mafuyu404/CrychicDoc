import type { DefaultTheme } from 'vitepress';
import { getProjectInfo, getLanguageByCode, getLangCodeFromLink, getSearchLocaleKey } from '../project-config';
import { getSidebarSync } from '../../utils/sidebar';

const projectInfo = getProjectInfo();
const langConfig = getLanguageByCode('en-US')!;

export const en_US = <DefaultTheme.Config>{
    label: langConfig.displayName,
    lang: langConfig.giscusLang,
    link: langConfig.link,
    title: 'CryChicDoc',
    description: 'A Minecraft development documentation website.',
    themeConfig: {
        nav: [
            {
                text: "KubeJS",
                items: [
                    {
                        text: "Index",
                        link: "/en/modpack/kubejs/",
                    },
                    {
                        text: "Docs",
                        items: [
                            {
                                text: "1.21",
                                link: "/en/modpack/kubejs/1.21/",
                            },
                            {
                                text: "1.20.1",
                                link: "/en/modpack/kubejs/1.20.1/",
                                activeMatch: "/en/modpack/kubejs/1.20.1/",
                            },
                            {
                                text: "1.19.2-Planning",
                                link: "...",
                            },
                            {
                                text: "1.18.2-Planning",
                                link: "...",
                            },
                        ],
                    },
                    {
                        text: "Third Party Docs",
                        items: [
                            {
                                text: "gumeng",
                                link: "/en/modpack/kubejs/1.20.1/KubeJSCourse/README",
                                activeMatch: "/en/modpack/kubejs/1.20.1/",
                            },
                            {
                                text: "Wudji-1.19.2",
                                link: "en/modpack/kubejs/1.19.2/XPlusKubeJSTutorial/README",
                            },
                            {
                                text: "Wudji-1.18.2",
                                link: "en/modpack/kubejs/1.18.2/XPlusKubeJSTutorial/README",
                            },
                        ],
                    },
                ],
            },
            {text: "Cooperation Guide", link: "/en/doc/rules"},
            {text: "Guide", items: [
                {text: "Minecraft", link: "/en/doc/guide/minecraft"},
                {text: "KubeJS", link: "/en/doc/guide/KubeJS"},
                {text: "Pixelart", link: "/en/doc/guide/pixelart"},
                {text: "Art Resource", link: "/en/doc/guide/resource"},
                {text: "Community", link: "/en/doc/guide/community"},
            ]},
            {text: "Discussion", link: "/en/info"},
            {text: "Tag", link: "/en/tags"},
            // {text: "About Us", link: "/en/about"},
        ],
        sidebar: getSidebarSync(getLangCodeFromLink(langConfig.link!)),
        outline: {
            level: "deep",
            label: "Page Content",
        },
        docFooter: {
            prev: "Previous Page",
            next: "Next Page",
        },
        lastUpdated: {
            text: "Last Updated",
            formatOptions: {
                dateStyle: "short",
                timeStyle: "medium",
            },
        },
        editLink: {
            text: "Edit this page on GitHub",
        },
        langMenuLabel: "Change Language",
        darkModeSwitchLabel: "Switch Theme",
        lightModeSwitchTitle: "Switch to light mode",
        darkModeSwitchTitle: "Switch to dark mode",
        returnToTopLabel: "Return to top",
        sidebarMenuLabel: "Menu",
    },
};

export const search: DefaultTheme.AlgoliaSearchOptions["locales"] = {
    [getSearchLocaleKey(langConfig.code)]: {
        placeholder: "Search docs",
        translations: {
            button: {
                buttonText: "Search",
                buttonAriaLabel: "Search",
            },
            modal: {
                searchBox: {
                    resetButtonTitle: "Clear the query",
                    resetButtonAriaLabel: "Clear the query",
                    cancelButtonText: "Cancel",
                    cancelButtonAriaLabel: "Cancel",
                },
                startScreen: {
                    recentSearchesTitle: "Recent",
                    noRecentSearchesText: "No recent searches",
                    saveRecentSearchButtonTitle: "Save this search",
                    removeRecentSearchButtonTitle: "Remove this search from history",
                    favoriteSearchesTitle: "Favorites",
                    removeFavoriteSearchButtonTitle: "Remove this search from favorites",
                },
                errorScreen: {
                    titleText: "Unable to fetch results",
                    helpText: "You might want to check your network connection",
                },
                footer: {
                    selectText: "to select",
                    navigateText: "to navigate",
                    closeText: "to close",
                    searchByText: "Search by",
                },
                noResultsScreen: {
                    noResultsText: "No results for",
                    suggestedQueryText: "Try searching for",
                    reportMissingResultsText: "Believe this query should return results?",
                    reportMissingResultsLinkText: "Let us know",
                },
            },
        },
    },
};
