---
authors: ['Gu-meng']
---
# Understanding The KubeJS Folder Structure

After KubeJS runs for the first time, it creates a modding folder named **`kubejs`** under your instance/version directory. Inside it you will see folders like the following. This page explains what each folder is used for.

**`assets`**  
**`data`**  
**`client_scripts`**  
**`config`**  
**`server_scripts`**  
**`startup_scripts`**  

## Assets (`assets`)

**`assets`** has the structure `assets/<namespace>/...`.

The `namespace` is your namespace (you can have more than one).

For example, vanilla uses the **`minecraft`** namespace. This is where resource files live, such as:  
**`lang`** and **`textures`**.

For details, see [Minecraft Wiki (zh): Resource pack file structure](https://zh.minecraft.wiki/w/%E8%B5%84%E6%BA%90%E5%8C%85#%E6%96%87%E4%BB%B6%E7%BB%93%E6%9E%84).

::: justify
> [!NOTE] Note
It is recommended to only use these resource files for content you create via KubeJS, and not to override other mods/resource packs using the same namespace and paths. In other words: use your own unique namespace to avoid collisions. The reason is explained later in the data/resource pack priority section.
:::

## Data (`data`)

**`data`** has the structure `data/<namespace>/...`.

The `namespace` is your namespace (you can have more than one).

For example, vanilla uses the **`minecraft`** namespace. This is where data files live, such as:  
**`tags`**, **`advancements`**, **`loot_tables`**, etc.

For details, see [Minecraft Wiki (zh): Data pack file structure](https://zh.minecraft.wiki/w/%E6%95%B0%E6%8D%AE%E5%8C%85#%E6%96%87%E4%BB%B6%E5%A4%B9%E7%BB%93%E6%9E%84).

::: justify
> [!NOTE] Note
It is recommended to only use these data files for content you create via KubeJS, and not to override other mods/data packs using the same namespace and paths. In other words: use your own unique namespace to avoid collisions. The reason is explained later in the data/resource pack priority section.
:::

## Data/Resource Pack Details

### Priority

:::: warning **Data/resource pack loading priority**
::: justify
In the game settings, if you click Options -> Resource Packs..., you will open the UI for choosing resource packs. Data packs have a similar UI when creating a new world: More -> Data Packs. For the priority rules discussed here, they behave the same, so we will use resource packs as the more familiar example.
:::
:::: 

::::: details Loading priority

> Note: the definition of priority here is based on the wiki page [Command /datapack (zh)](https://zh.minecraft.wiki/w/%E5%91%BD%E4%BB%A4/datapack?variant=zh-cn)

:::info **Prerequisites**
1. Priority here does not mean "load order priority", but "effective priority" (which content wins).
2. Lower priority loads first; higher priority loads later.
3. Later-loaded packs override earlier packs when they share the same namespace and path.
4. In the resource pack/data pack UI: packs higher in the list have higher priority (and load later); packs lower in the list have lower priority (and load earlier).  
Another way to describe it: if pack A is listed after pack B, then B loads first (lower priority) and A loads later (higher priority). A can override B when they share the same namespace and path.
:::

:::: info **How to understand priority**
::: justify
You might find it confusing that "higher priority" loads later. The confusion comes from mixing up two different meanings of priority. Here, priority means which content wins when files collide. If a pack loads later, it has a higher chance to take effect because fewer packs can override it afterwards. So "higher priority" here means "more likely to be effective", not "loads first".
:::
::::
:::::

### Structure


::: details **Why only recommend using KubeJS `assets`/`data` for your own content, instead of overriding others?**  

- [Review](#priority): later-loaded packs override earlier packs when they share the same namespace and path.  
- That means if you want to override another mod, you must ensure your pack loads after the one you want to override.  
- By default (without extra tooling), KubeJS's own `assets`/`data` often load before most mods, which does not satisfy the "load after" requirement for overriding.

:::

::: details **Will my resources/data be overridden by resource packs/data packs installed by the player?**  

- [Review](#priority): later-loaded packs override earlier packs when they share the same namespace and path.  
- As long as you use your own unique namespace, other packs installed by the player are very unlikely to use the exact same namespace, so they will not override your files.  
- Edge cases exist, but they are rare.

:::

::: info **Global data/resource packs**
Sometimes you do want "global" data/resource pack features: overriding, shipping third-party packs, etc. Here are some known mods that provide this functionality:
- [Paxi](https://www.mcmod.cn/class/4615.html)
- [Global Data & Resourcepacks](https://www.mcmod.cn/class/2826.html)
- [Json Things](https://www.mcmod.cn/class/7734.html)
:::

## Config (`config`)

**`config`** stores configuration files. You can put your own config files here.

> [!IMPORTANT] This is limited to configuration files supported by KubeJS itself, such as changing the icon and window title.

::: tabs

==client_scripts

Client-side code. Besides UI-side logic, this is where you can write client-only behaviors such as item rendering.  

Reload with `F3+T`, or use `/kubejs reload client_scripts`.  

(Commonly used for PonderJS and similar mods.)

==server_scripts 

Server-side code. Most gameplay logic runs on the server.

So most scripts live in this folder.

You can reload with `/reload` in-game.

==startup_scripts

Code that runs during game startup, such as registering items, blocks, fluids, etc.  

Reload with `/kubejs reload startup_scripts`.  

(Registering new things ==**cannot**== be hot-reloaded; you must restart the game.)

:::
