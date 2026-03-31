# Archived Directory: 整合包

**Archive Date**: 2026-03-31T22:27:30.233Z
**Original Location**: modpack/整合包
**Reason**: Physical directory no longer exists in docs structure

## Contents
This archive contains both the configuration files and metadata for a directory that was removed from the physical docs structure.

- `config/` - Contains the JSON configuration files (locales.json, order.json, etc.)
- `metadata/` - Contains the metadata files tracking configuration history

## Restoration
To restore this directory:

1. **Recreate the physical directory**: 
   `mkdir -p docs/zh/modpack/整合包/`

2. **Restore configuration files**:
   `cp -r config/整合包/ .vitepress/config/sidebar/zh/modpack/整合包/`

3. **Restore metadata files**:
   `cp -r metadata/整合包/ .vitepress/config/sidebar/.metadata/zh/modpack/整合包/`

4. **Restart the development server**

## Archive Structure
```
整合包_removed_2026-03-31/
├── README.md (this file)
├── config/整合包/     # Original config files
└── metadata/整合包/   # Original metadata files
```
