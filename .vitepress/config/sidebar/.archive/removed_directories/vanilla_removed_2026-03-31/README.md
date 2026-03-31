# Archived Directory: vanilla

**Archive Date**: 2026-03-31T22:45:28.101Z
**Original Location**: develop/vanilla/vanilla
**Reason**: Physical directory no longer exists in docs structure

## Contents
This archive contains both the configuration files and metadata for a directory that was removed from the physical docs structure.

- `config/` - Contains the JSON configuration files (locales.json, order.json, etc.)
- `metadata/` - Contains the metadata files tracking configuration history

## Restoration
To restore this directory:

1. **Recreate the physical directory**: 
   `mkdir -p docs/en/develop/vanilla/vanilla/`

2. **Restore configuration files**:
   `cp -r config/vanilla/ .vitepress/config/sidebar/en/develop/vanilla/vanilla/`

3. **Restore metadata files**:
   `cp -r metadata/vanilla/ .vitepress/config/sidebar/.metadata/en/develop/vanilla/vanilla/`

4. **Restart the development server**

## Archive Structure
```
vanilla_removed_2026-03-31/
├── README.md (this file)
├── config/vanilla/     # Original config files
└── metadata/vanilla/   # Original metadata files
```
