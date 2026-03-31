# Archived Directory: custom-test

**Archive Date**: 2026-03-31T22:27:31.946Z
**Original Location**: mods/custom/custom-test
**Reason**: Physical directory no longer exists in docs structure

## Contents
This archive contains both the configuration files and metadata for a directory that was removed from the physical docs structure.

- `config/` - Contains the JSON configuration files (locales.json, order.json, etc.)
- `metadata/` - Contains the metadata files tracking configuration history

## Restoration
To restore this directory:

1. **Recreate the physical directory**: 
   `mkdir -p docs/en/mods/custom/custom-test/`

2. **Restore configuration files**:
   `cp -r config/custom-test/ .vitepress/config/sidebar/en/mods/custom/custom-test/`

3. **Restore metadata files**:
   `cp -r metadata/custom-test/ .vitepress/config/sidebar/.metadata/en/mods/custom/custom-test/`

4. **Restart the development server**

## Archive Structure
```
custom-test_removed_2026-03-31/
├── README.md (this file)
├── config/custom-test/     # Original config files
└── metadata/custom-test/   # Original metadata files
```
