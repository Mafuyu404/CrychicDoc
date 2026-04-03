# Archived Directory: 目录

**Archive Date**: 2026-04-03T13:40:14.125Z
**Original Location**: mods/adventure/目录
**Reason**: Physical directory no longer exists in docs structure

## Contents
This archive contains both the configuration files and metadata for a directory that was removed from the physical docs structure.

- `config/` - Contains the JSON configuration files (locales.json, order.json, etc.)
- `metadata/` - Contains the metadata files tracking configuration history

## Restoration
To restore this directory:

1. **Recreate the physical directory**: 
   `mkdir -p docs/zh/mods/adventure/目录/`

2. **Restore configuration files**:
   `cp -r config/目录/ .vitepress/config/sidebar/zh/mods/adventure/目录/`

3. **Restore metadata files**:
   `cp -r metadata/目录/ .vitepress/config/sidebar/.metadata/zh/mods/adventure/目录/`

4. **Restart the development server**

## Archive Structure
```
目录_removed_2026-04-03/
├── README.md (this file)
├── config/目录/     # Original config files
└── metadata/目录/   # Original metadata files
```
