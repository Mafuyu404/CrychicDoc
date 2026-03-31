# Archived Directory: develop

**Archive Date**: 2026-03-31T22:45:27.972Z
**Original Location**: develop/develop
**Reason**: Physical directory no longer exists in docs structure

## Contents
This archive contains both the configuration files and metadata for a directory that was removed from the physical docs structure.

- `config/` - Contains the JSON configuration files (locales.json, order.json, etc.)
- `metadata/` - Contains the metadata files tracking configuration history

## Restoration
To restore this directory:

1. **Recreate the physical directory**: 
   `mkdir -p docs/en/develop/develop/`

2. **Restore configuration files**:
   `cp -r config/develop/ .vitepress/config/sidebar/en/develop/develop/`

3. **Restore metadata files**:
   `cp -r metadata/develop/ .vitepress/config/sidebar/.metadata/en/develop/develop/`

4. **Restart the development server**

## Archive Structure
```
develop_removed_2026-03-31/
├── README.md (this file)
├── config/develop/     # Original config files
└── metadata/develop/   # Original metadata files
```
