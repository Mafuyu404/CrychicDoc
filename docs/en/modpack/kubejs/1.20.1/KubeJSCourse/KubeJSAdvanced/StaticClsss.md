# Global Static Classes
KubeJS provides the following global static classes and corresponding methods.

## Utils
| Method | Method Parameters | Purpose | Return Type | Notes |
| :--: | :--: | :--: | :--: | :--: |
| toTitleCase(string) | -\> | Convert the first letters of words in a string to title case | Processed string | Excepts "a", "an", "the", "of", "on", "in", "and", "or", "but", and "for" |
| getStat(ResourceLocation) | ~ | ~ | Stat<ResourceLocation\> | To be supplemented later |
| snakeCaseToTitleCase(string) | -\> | Convert snake_case to Title Case | Processed string | e.g. `a_bc_def` -> `A Bc Def` |
| newCountingMap() | - | Get a new `CountingMap` | CountingMap | - |
| toTitleCase(string,bool) | -\> | When set to true, also capitalize the excluded words above | Processed string | - |
| parseDouble(any,number) | -\> | Convert the first parameter to `Double`; return the second if conversion fails | number | - |
| getRandom() | - | Get a `Random` instance | Random | - |
| newList() | - | Get a list | List<T\> | - |
| rollChestLoot(ResourceLocation) | Loot table ID | Get item stacks generated from the specified loot table | List<ItemStack\> | - |
| newRandom(number) | -\> | Create a `Random` using the number as seed | Random | - |
| getRegistryIds(ResourceLocation) | -\> | Get all IDs in a specified registry | List<ResourceLocation\> | - |
| emptyList() | - | Get an immutable empty list? | List<T\> | - |
| getSystemTime() | - | Get current system time in milliseconds | number | - |
| supplyAsync(Supplier<any\>) | ~ | ~ | CompletableFuture<any\> | To be supplemented later |
| id(string,string) | -\> | Convert strings into a `ResourceLocation` | ResourceLocation | - |
| lazy(Supplier<T\>) | ~ | ~ | Lazy<T\> | ? |
| isWrapped(any) | -\> | Check whether input object is `WrappedJS` | boolean | - |
| snakeCaseToCamelCase(string) | -\> | Convert snake_case to camelCase | Processed string | e.g. `a_bc_def` -> `aBcDef` |
| findCreativeTab(ResourceLocation) | id | Get the creative tab for this ID | CreativeModeTab | - |
| emptyMap() | - | Get an immutable empty map? | Map<K, V\> | - |
| expiringLazy(Supplier<T\>,number) | ~ | ~ | Lazy<T\> | To be supplemented later |
| getSound(ResourceLocation) | id | Get `SoundEvent` from ID | SoundEvent | - |
| randomOf(Random,Collection<any\>) | ~ | Use given params to get a random object from a collection? | any | ? |
| newMap() | - | Get a map | Map<any, any\> | - |
| getRegistry(ResourceLocation) | id | Get registry info by ID | RegistryInfo<any\> | - |
| particleOptions(any) | ~ | ~ | ParticleOptions | - |
| copy(any) | -\> | Copy the input object; return original if not copyable | any | - |
| regex(string,number) | string pattern / number flags | ~ | Pattern | - |
| id(string) | -\> | Wrap a string directly into `ResourceLocation` | ResourceLocation | - |
| regex(any) | -\> | ~ | Pattern | - |
| runAsync(Runnable) | -\> | ~ | CompletableFuture<void\> | - |
| parseBlockState(any) | -\> | Parse `BlockState` from input; may throw on invalid input | BlockState | - |
| queueIO(Runnable) | -\> | Run the passed runnable immediately in a try-catch and log exceptions? | void | - |
| parseInt(any,number) | -\> | Convert the first parameter to integer; return the second if conversion fails | number | - |
| getServer() | - | Get Minecraft server; returns null where server is unavailable (`startup`/`client`) | MinecraftServer | - |
| rollChestLoot(ResourceLocation,Entity) | Loot table ID, trigger entity | Generate loot table items using the specified entity context | List<ItemStack\> | Not limited to chests |

## JsonIO
| Method | Method Parameters | Purpose | Return Type | Notes |
| :--: | :--: | :--: | :--: | :--: |
| readJson(path) | Path string | Read file at the path | JsonElement | File must be JSON |
| toPrettyString(JsonElement) | -\> | Convert JSON to formatted string | string | - |
| getJsonHashString(JsonElement) | -\> | Get JSON hash string | string | - |
| toObject(JsonElement) | -\> | Convert JSON to object??? | any | Needs further testing |
| primitiveOf(any) | ? | ? | JsonPrimitive | Needs further testing |
| readString(path) | Path | Read file at the path | string | Reads as string format |
| writeJsonHash(DataOutputStream,JsonElement) | ? | ~ | void | Needs further testing |
| parseRaw(string) | ? | ? | JsonElement | Needs further testing |
| write(path,JsonObject) | -\> | Write JSON object to file path | void | Must be JSON object, not array |
| read(path) | -\> | Read file at the path | Map<any,any\> | `readJson` is recommended |
| toArray(JsonElement) | -\> | Convert `JsonElement` to `JsonArray` | JsonArray | - |
| parse(string) | -\> | ? | any | Needs further testing |
| toPrimitive(JsonElement) | -\> | ? | any | Needs further testing |
| copy(JsonElement) | -\> | Copy `JsonElement` | JsonElement | - |
| toString(JsonElement) | -\> | Convert JSON to string | string | - |
| getJsonHashBytes(JsonElement) | -\> | Convert JSON to hash bytes | number[] | - |
| of(any) | ? | ? | JsonElement | - |

## BlockPos
## Block
## Vec3d
## KMath
## RotationAxis
## ResourceLocation
## Items
## SoundType
## Stats
## Duration
## OutputItem
## Fluid
## InputItem
## DamageSource
## Platform
## Vec3f
## Vec4f
## Notification
## Quaternionf
## JavaMath
## BlockProperties
## Vec3i
## Matrix3f
## Matrix4f
## Blocks
## Component
