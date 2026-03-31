# Archived Directory: adventure-mods

**Archive Date**: 2026-03-31T22:45:28.173Z
**Original Location**: mods/adventure/adventure-mods
**Reason**: Physical directory no longer exists in docs structure

## Contents
This archive contains both the configuration files and metadata for a directory that was removed from the physical docs structure.

- `config/` - Contains the JSON configuration files (locales.json, order.json, etc.)
- `metadata/` - Contains the metadata files tracking configuration history

## Restoration
To restore this directory:

1. **Recreate the physical directory**: 
   `mkdir -p docs/en/mods/adventure/adventure-mods/`

2. **Restore configuration files**:
   `cp -r config/adventure-mods/ .vitepress/config/sidebar/en/mods/adventure/adventure-mods/`

3. **Restore metadata files**:
   `cp -r metadata/adventure-mods/ .vitepress/config/sidebar/.metadata/en/mods/adventure/adventure-mods/`

4. **Restart the development server**

## Archive Structure
```
adventure-mods_removed_2026-03-31/
├── README.md (this file)
├── config/adventure-mods/     # Original config files
└── metadata/adventure-mods/   # Original metadata files
```
