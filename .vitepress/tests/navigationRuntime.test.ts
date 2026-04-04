import assert from "node:assert/strict";
import test from "node:test";

import {
    buildBreadcrumbItems,
    resolveBaseAwareHref,
} from "../utils/vitepress/runtime/navigation/linkResolution";
import {
    buildKnownPagePathSet,
    buildKnownPagePathSetFromSidebar,
} from "../utils/vitepress/runtime/navigation/pageRouteIndex";

test("buildBreadcrumbItems handles /en/ locale roots with Description landing pages", () => {
    const items = buildBreadcrumbItems({
        routePath: "/en/doc/developmentWorkflow.html",
        siteBase: "/",
        homeLink: "/en/",
        homeText: "Home",
        pageTitle: "Development Workflow",
        localeCodes: ["en-US", "zh-CN"],
        knownPagePaths: new Set([
            "/en/doc/Description/",
            "/en/doc/developmentWorkflow/",
        ]),
        navTree: [
            {
                text: "Documentation Hub",
                link: "/doc/Description/",
            },
            {
                text: "Development Workflow",
                link: "/doc/developmentWorkflow/",
            },
        ],
        resolveLinkPath: (path) =>
            path === "/en/doc/" ? "/en/doc/Description/" : path,
    });

    assert.deepEqual(items, [
        { text: "Home", link: "/en/" },
        { text: "Documentation Hub", link: "/en/doc/Description/" },
        { text: "Development Workflow", link: "/en/doc/developmentWorkflow/" },
    ]);
});

test("buildKnownPagePathSet strips docs roots and keeps Description/Catalogue pages", () => {
    const knownPagePaths = buildKnownPagePathSet([
        "/docs/en/index.md",
        "../../../../docs/en/doc/developmentWorkflow.md",
        "../../../../docs/en/modpack/kubejs/1.20.1/Introduction/Recipe/Catalogue.md",
        "../../../../docs/zh/develop/plugin/Description.md",
    ]);

    assert.deepEqual([...knownPagePaths].sort(), [
        "/en/",
        "/en/doc/developmentWorkflow/",
        "/en/modpack/kubejs/1.20.1/Introduction/Recipe/Catalogue/",
        "/zh/develop/plugin/Description/",
    ]);
});

test("buildKnownPagePathSetFromSidebar includes section roots and nested links", () => {
    const knownPagePaths = buildKnownPagePathSetFromSidebar({
        "/en/develop/": [
            {
                text: "Develop",
                link: "/en/develop/Description.html",
                items: [
                    {
                        text: "plugin",
                        link: "/en/develop/plugin/Description.html",
                        items: [
                            {
                                text: "setup",
                                link: "/en/develop/plugin/setup.html",
                            },
                        ],
                    },
                ],
            },
        ],
    });

    assert.deepEqual([...knownPagePaths].sort(), [
        "/en/develop/",
        "/en/develop/Description/",
        "/en/develop/plugin/Description/",
        "/en/develop/plugin/setup/",
    ]);
});

test("buildBreadcrumbItems resolves sidebar landing routes for parent crumbs", () => {
    const items = buildBreadcrumbItems({
        routePath: "/zh/develop/plugin/setup",
        siteBase: "/",
        homeLink: "/zh/",
        homeText: "Home",
        pageTitle: "Setup",
        localeCodes: ["en-US", "zh-CN"],
        knownPagePaths: new Set([
            "/zh/develop/Description/",
            "/zh/develop/plugin/Description/",
            "/zh/develop/plugin/setup/",
        ]),
        navTree: [
            {
                text: "Develop",
                link: "/develop/Description/",
            },
            {
                text: "plugin",
                link: "/develop/plugin/Description/",
            },
            {
                text: "Setup",
                link: "/develop/plugin/setup/",
            },
        ],
        resolveLinkPath: (path) => {
            if (path === "/zh/develop/") return "/zh/develop/Description/";
            if (path === "/zh/develop/plugin/") {
                return "/zh/develop/plugin/Description/";
            }
            return path;
        },
    });

    assert.deepEqual(items, [
        { text: "Home", link: "/zh/" },
        { text: "Develop", link: "/zh/develop/Description/" },
        { text: "plugin", link: "/zh/develop/plugin/Description/" },
        { text: "Setup", link: "/zh/develop/plugin/setup/" },
    ]);
});

test("buildBreadcrumbItems keeps the first nav label when landing hrefs repeat", () => {
    const items = buildBreadcrumbItems({
        routePath: "/en/develop/plugin/setup",
        siteBase: "/",
        homeLink: "/en/",
        homeText: "Home",
        pageTitle: "Setup",
        localeCodes: ["en-US", "zh-CN"],
        knownPagePaths: new Set([
            "/en/develop/Description/",
            "/en/develop/plugin/Description/",
            "/en/develop/plugin/setup/",
        ]),
        navTree: [
            {
                text: "Develop",
                link: "/develop/Description/",
                items: [
                    {
                        text: "Description",
                        link: "/develop/Description/",
                    },
                    {
                        text: "plugin",
                        link: "/develop/plugin/Description/",
                        items: [
                            {
                                text: "Description",
                                link: "/develop/plugin/Description/",
                            },
                            {
                                text: "Setup",
                                link: "/develop/plugin/setup/",
                            },
                        ],
                    },
                ],
            },
        ],
        resolveLinkPath: (path) => {
            if (path === "/en/develop/") return "/en/develop/Description/";
            if (path === "/en/develop/plugin/") {
                return "/en/develop/plugin/Description/";
            }
            return path;
        },
    });

    assert.deepEqual(items, [
        { text: "Home", link: "/en/" },
        { text: "Develop", link: "/en/develop/Description/" },
        { text: "plugin", link: "/en/develop/plugin/Description/" },
        { text: "Setup", link: "/en/develop/plugin/setup/" },
    ]);
});

test("resolveBaseAwareHref prefixes only internal footer links", () => {
    const applyBase = (value: string) => `/CrychicDoc${value}`;

    assert.equal(
        resolveBaseAwareHref("/en/modpack/kubejs/1.20.1/", applyBase),
        "/CrychicDoc/en/modpack/kubejs/1.20.1/",
    );
    assert.equal(
        resolveBaseAwareHref("https://docs.variedmc.cc", applyBase),
        "https://docs.variedmc.cc",
    );
});
