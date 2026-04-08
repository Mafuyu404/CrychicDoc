---
authors: ['Gu-meng', 'Qi-Month']
---
# Overview

> What is a PonderTag?

PonderTags create categories in the Ponder index.  
Ponder entries in the same category can cross-link, which is very convenient.
## 1. Create a New Tag

> Create a new PonderTag

```js
Ponder.tags((event) => {
  event.createTag(
    "kubejs:iron_golem", // Your PonderTag id (must be lowercase)
    "minecraft:iron_block", // Your PonderTag icon
    "Iron Golem", // Your PonderTag title
    "Iron golems are large, strong friendly mobs that protect players and villagers.", // Your PonderTag description
    ["minecraft:iron_block", "minecraft:carved_pumpkin"] // **Optional** linked entries; if there is only one, [] can be omitted
  );
});
```

![Image](/imgs/PonderJS/PonderTag.gif)

## 2. Add Linked Entries to an Existing Tag

> Add linked entries to an existing PonderTag

```js
Ponder.tags((event) => {
  event.add(
    "kubejs:iron_golem", // PonderTag to edit
    ["minecraft:iron_ingot", "minecraft:poppy"] // Entries to add; if there is only one, [] can be omitted
  );
});
```

EX: Set the PonderTag while creating a Ponder entry.  
Here is a snippet from [iron_golem.js](https://gitee.com/gumengmengs/kubejs-course/tree/main/Code/Ponder/kubejs/client_scripts/Ponder/iron_golem.js):

```js
Ponder.registry(event => {
    ↓
    ==
    ↑
    event.create("minecraft:iron_block")
        .tag("kubejs:iron_golem")  // Only existing PonderTags can be added; for multiple tags, no [] is needed, just separate by commas
        .scene("kubejs:iron_golem_1", "Summon an Iron Golem", (scene, util) => {
    ↓
    ==
    ↑
       });
});
```

## 3. Remove Linked Entries from a Tag

> Remove linked entries from a PonderTag

```js
Ponder.tags((event) => {
  event.remove(
    "kubejs:iron_golem", // PonderTag to edit
    ["minecraft:iron_ingot", "minecraft:poppy"] // Entries to remove; if there is only one, [] can be omitted
  );
});
```

## 4. Remove a Tag

> Remove an existing PonderTag

```js
Ponder.tags((event) => {
  event.removeTag(
    "kubejs:iron_golem" // PonderTag to remove; for multiple tags, no [] is needed, just separate by commas
  );
});
```

## 5. Built-in Create PonderTags

> Built-in [PonderTags](https://github.com/Creators-of-Create/Create/blob/mc1.18/dev/src/main/java/com/simibubi/create/infrastructure/ponder/AllPonderTags.java) from Create:

|           Tag           |    Icon    |          Title           |                              Description                               |
| :--------------------: | :--------: | :--------------------: | :----------------------------------------------------------------: |
|    "kinetic_relays"    |    Cogwheel    |       "Kinetic Blocks"       |                       "Components used to transmit rotational force"                       |
|   "kinetic_sources"    |    Water Wheel    |        "Power Sources"        |                       "Components capable of generating rotational force"                       |
|  "kinetic_appliances"  | Mechanical Press |       "Kinetic Devices"       |                  "These components can operate using rotational force"                  |
|        "fluids"        |  Fluid Pipe  |     "Fluid Handling"     |          "These components transport fluids and use fluids in processing"          |
|      "logistics"       |    Chest    |       "Item Logistics"       |                      "These components assist with item transport"                      |
|       "redstone"       |   Redstone Dust   |       "Logic Components"       |                 "These components are useful in redstone systems"                 |
|      "decoration"      |   Rose Bush   |         "Decoration"         |                     "Decoration is a common use for these components"                     |
|       "creative"       | Creative Crate |       "Creative Mode"       |                   "Some things are not obtainable in survival mode"                   |
|   "movement_anchor"    |  Mechanical Piston  |       "Movement Anchors"       |   "Components used to build moving contraptions and move connected structures in multiple ways"   |
|  "contraption_actor"   | Mechanical Harvester |     "Contraption Actors"     |                "Components that perform special actions on moving contraptions"                |
| "contraption_assembly" |   Super Glue   |     "Block Connection"     |             "Tools and components that connect blocks to move together"             |
|    "windmill_sails"    |  Windmill Bearing  |   "Windmill Sails"   | "Blocks counted as windmill sail strength during windmill assembly; all have equal efficiency" |
|     "arm_targets"      |   Mechanical Arm   |   "Arm Targets"   |                "Components that can be used as input/output points for mechanical arms"                |
|    "train_related"     |  Train Track  |       "Railway Equipment"       |                   "Components for building or managing train systems"                   |
|   "recently_updated"   |   Clipboard   |      "Recently Updated"      |            "Components newly added or significantly changed in recent Create versions"            |
|   "display_sources"    | Display Link | "Display Sources" |         "Components or blocks that provide data readable by Display Links"         |
|   "display_targets"    | Display Link | "Display Targets" |        "Components or blocks that process and display data from Display Links"        |
