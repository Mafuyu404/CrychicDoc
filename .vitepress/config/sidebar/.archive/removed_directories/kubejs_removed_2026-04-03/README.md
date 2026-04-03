# Archived Directory: kubejs

**Archive Date**: 2026-04-03T13:40:16.480Z
**Original Location**: info/KubeJS/kubejs
**Reason**: Physical directory no longer exists in docs structure

## Contents
This archive contains both the configuration files and metadata for a directory that was removed from the physical docs structure.

- `config/` - Contains the JSON configuration files (locales.json, order.json, etc.)
- `metadata/` - Contains the metadata files tracking configuration history

## Restoration
To restore this directory:

1. **Recreate the physical directory**: 
   `mkdir -p docs/en/info/KubeJS/kubejs/`

2. **Restore configuration files**:
   `cp -r config/kubejs/ .vitepress/config/sidebar/en/info/KubeJS/kubejs/`

3. **Restore metadata files**:
   `cp -r metadata/kubejs/ .vitepress/config/sidebar/.metadata/en/info/KubeJS/kubejs/`

4. **Restart the development server**

## Archive Structure
```
kubejs_removed_2026-04-03/
├── README.md (this file)
├── config/kubejs/     # Original config files
└── metadata/kubejs/   # Original metadata files
```
