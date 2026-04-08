---
authors: ['Gu-meng']
---
# Tag Overview

[mcwiki](https://zh.minecraft.wiki/w/%E6%A0%87%E7%AD%BE?variant=zh-cn) already provides an official-style explanation, so this page briefly introduces how tags are used in modpack customization.

## What Tags Do
In vanilla, many systems check tags directly, which greatly reduces duplicated logic.

In modpack scripting, assigning tags to items/blocks also reduces code volume.  
For example, sticks can be crafted from any plank. Writing a separate recipe for every plank would be tedious.

Vanilla solves this by tagging all planks, then using the plank tag as recipe input to avoid repeated definitions.

In Create, you can pick up some vanilla redstone machines with a wrench because those blocks are tagged with `create:wrench_pickup`.  
The wrench logic only needs to check whether the block has that tag.

Vanilla has similar cases too, such as block mining level tiers, which are tag-based. See [mcwiki](https://zh.minecraft.wiki/w/%E5%B7%A5%E5%85%B7%E6%9D%90%E6%96%99).
