# Archived Directory: doc

**Archive Date**: 2025-08-27T13:20:20.806Z
**Original Location**: doc/doc
**Reason**: Physical directory no longer exists in docs structure

## Contents
This archive contains both the configuration files and metadata for a directory that was removed from the physical docs structure.

- `config/` - Contains the JSON configuration files (locales.json, order.json, etc.)
- `metadata/` - Contains the metadata files tracking configuration history

## Restoration
To restore this directory:

1. **Recreate the physical directory**: 
   `mkdir -p docs/en/doc/doc/`

2. **Restore configuration files**:
   `cp -r config/doc/ .vitepress/config/sidebar/en/doc/doc/`

3. **Restore metadata files**:
   `cp -r metadata/doc/ .vitepress/config/sidebar/.metadata/en/doc/doc/`

4. **Restart the development server**

## Archive Structure
```
doc_removed_2025-08-27/
├── README.md (this file)
├── config/doc/     # Original config files
└── metadata/doc/   # Original metadata files
```
