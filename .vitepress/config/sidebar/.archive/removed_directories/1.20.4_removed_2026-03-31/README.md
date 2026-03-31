# Archived Directory: 1.20.4

**Archive Date**: 2026-03-31T22:27:32.005Z
**Original Location**: develop/modding/1.20.4/1.20.4
**Reason**: Physical directory no longer exists in docs structure

## Contents
This archive contains both the configuration files and metadata for a directory that was removed from the physical docs structure.

- `config/` - Contains the JSON configuration files (locales.json, order.json, etc.)
- `metadata/` - Contains the metadata files tracking configuration history

## Restoration
To restore this directory:

1. **Recreate the physical directory**: 
   `mkdir -p docs/en/develop/modding/1.20.4/1.20.4/`

2. **Restore configuration files**:
   `cp -r config/1.20.4/ .vitepress/config/sidebar/en/develop/modding/1.20.4/1.20.4/`

3. **Restore metadata files**:
   `cp -r metadata/1.20.4/ .vitepress/config/sidebar/.metadata/en/develop/modding/1.20.4/1.20.4/`

4. **Restart the development server**

## Archive Structure
```
1.20.4_removed_2026-03-31/
├── README.md (this file)
├── config/1.20.4/     # Original config files
└── metadata/1.20.4/   # Original metadata files
```
