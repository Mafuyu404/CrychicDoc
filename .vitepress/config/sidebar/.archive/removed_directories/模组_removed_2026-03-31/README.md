# Archived Directory: 模组

**Archive Date**: 2026-03-31T22:27:30.561Z
**Original Location**: mods/custom/模组
**Reason**: Physical directory no longer exists in docs structure

## Contents
This archive contains both the configuration files and metadata for a directory that was removed from the physical docs structure.

- `config/` - Contains the JSON configuration files (locales.json, order.json, etc.)
- `metadata/` - Contains the metadata files tracking configuration history

## Restoration
To restore this directory:

1. **Recreate the physical directory**: 
   `mkdir -p docs/zh/mods/custom/模组/`

2. **Restore configuration files**:
   `cp -r config/模组/ .vitepress/config/sidebar/zh/mods/custom/模组/`

3. **Restore metadata files**:
   `cp -r metadata/模组/ .vitepress/config/sidebar/.metadata/zh/mods/custom/模组/`

4. **Restart the development server**

## Archive Structure
```
模组_removed_2026-03-31/
├── README.md (this file)
├── config/模组/     # Original config files
└── metadata/模组/   # Original metadata files
```
