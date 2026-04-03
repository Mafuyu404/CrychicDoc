# Archived Directory: modpack

**Archive Date**: 2026-04-03T13:40:16.211Z
**Original Location**: modpack/modpack
**Reason**: Physical directory no longer exists in docs structure

## Contents
This archive contains both the configuration files and metadata for a directory that was removed from the physical docs structure.

- `config/` - Contains the JSON configuration files (locales.json, order.json, etc.)
- `metadata/` - Contains the metadata files tracking configuration history

## Restoration
To restore this directory:

1. **Recreate the physical directory**: 
   `mkdir -p docs/en/modpack/modpack/`

2. **Restore configuration files**:
   `cp -r config/modpack/ .vitepress/config/sidebar/en/modpack/modpack/`

3. **Restore metadata files**:
   `cp -r metadata/modpack/ .vitepress/config/sidebar/.metadata/en/modpack/modpack/`

4. **Restart the development server**

## Archive Structure
```
modpack_removed_2026-04-03/
├── README.md (this file)
├── config/modpack/     # Original config files
└── metadata/modpack/   # Original metadata files
```
