import type { FooterConfig } from '../../../utils/content/footer';
import { createIconConfig, createLinkConfig, createGroupConfig } from '../../../utils/content/footer';

export const footerConfig: FooterConfig = {
    beian: {
        showIcon: true,
        icp: {
            icon: createIconConfig(
                'fluent:globe-shield-48-filled',
                'rgba(20, 150, 255, 1)',
                'rgba(100, 200, 255, 1)'
            ),
            number: '晋ICP备2025065291号',
            rel: 'noopener noreferrer',
        },
        police: {
            icon: createIconConfig(
                'fluent:shield-checkmark-48-filled',
                'rgba(50, 200, 50, 1)',
                'rgba(100, 255, 100, 1)'
            ),
            number: '公安备案号 12345678',
            rel: 'noopener noreferrer',
        },
    },
    author: {
        icon: createIconConfig('mdi:copyright', '#999', '#ccc'),
        name: 'M1hono',
        link: 'https://github.com/M1hono',
        rel: 'noopener noreferrer',
        text: '版权所有。',
    },
    group: [
        createGroupConfig(
            '外部链接',
            [
                createLinkConfig(
                    'GitHub',
                    'https://github.com/PickAID/CrychicDoc',
                    'mdi:github',
                    {
                        rel: 'noopener noreferrer',
                        iconColors: { 
                            light: 'rgba(0, 0, 0, 1)',
                            dark: 'rgba(255, 255, 255, 1)'
                        },
                    }
                ),
                createLinkConfig(
                    '模版仓库',
                    'https://m1hono.github.io/M1honoVitepressTemplate/',
                    '/svg/logo.svg',
                    {
                        rel: 'noopener noreferrer',
                        iconColors: { 
                            light: 'rgba(100, 150, 200, 1)',
                            dark: 'rgba(150, 200, 255, 1)'
                        },
                    }
                ),
            ],
            'bx:link',
            { 
                light: 'rgba(255, 87, 51, 1)',
                dark: 'rgba(255, 130, 100, 1)'
            }
        ),
        createGroupConfig(
            'KubeJS',
            [
                createLinkConfig(
                    '1.20.1',
                    '/zh/modpack/kubejs/1.20.1/',
                    '/svg/kubejs.svg',
                    {
                        iconColors: { 
                            light: 'rgba(100, 200, 150, 1)',
                            dark: 'rgba(150, 255, 200, 1)'
                        },
                    }
                ),
                createLinkConfig(
                    '1.21.1',
                    '/zh/modpack/kubejs/1.21/',
                    '/svg/kubejs.svg',
                    {
                        iconColors: { 
                            light: 'rgba(100, 200, 150, 1)',
                            dark: 'rgba(150, 255, 200, 1)'
                        },
                    }
                ),
            ],
            'mdi:database-search',
            { 
                light: 'rgb(109, 37, 109)',
                dark: 'rgb(129, 48, 184)'
            }
        ),
    ],
};

export default footerConfig; 