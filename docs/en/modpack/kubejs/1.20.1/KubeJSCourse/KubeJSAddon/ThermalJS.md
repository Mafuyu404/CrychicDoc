---
authors: ['Gu-meng']
---
## Thermal Expansion and KubeJS
This chapter introduces how to use KubeJS to modify Thermal Expansion recipes.

### Recipe Type Table (not complete, only common ones; `[]` means multiple inputs/outputs or arrays)
**Some recipe types are inconsistent in implementation.**
**Not all listed types are visible in normal gameplay.**
**Some types are incomplete (for example, `ingredients` without `results`).**

Most recipes support `.energy(int)` to set energy cost (default `2000`).
|                           Recipe Format                           |      Machine Type      | Extra Notes |
| :-----------------------------------------------------------: | :----------: | :------: |
|               `thermal.press(output[], input)`                |      Multiservo Press      |     -      |
|            `thermal.bottler(output, hive(block))`             |         Bottler          |     -      |
|         `thermal.hive_extractor(output, hive(block))`         |      Hive Extractor      | Hive block |
| `thermal.tree_extractor(output, trunk(block), leaves(block))` |      Tree Extractor      | next to trunk |
|               `thermal.furnace(output, input)`                |       Redstone Furnace       |     -      |
|          `thermal.smelter_recycle(output, input[])`           |      Induction Smelter      |     -      |
|        `thermal.pulverizer_catalyst(output[], input)`         |        Pulverizer         |     -      |
|            `thermal.crystallizer(output, input[])`            |       Crystallizer        |     -      |
|               `thermal.crucible(output, input)`               |         Crucible          |     -      |
|              `thermal.chiller(output, input[])`               |          Chiller          |     -      |
|            `thermal.insolator(output[], input[])`             |         Insolator         |     -      |
|               `thermal.brewer(output, input[])`               |          Brewer           |     -      |
|              `thermal.refinery(output, input[])`              |         Refinery          |     -      |
|             `thermal.centrifuge(output[], input)`             |        Centrifuge         |     -      |
|              `thermal.sawmill(output[], input)`               |          Sawmill          |     -      |
|   `thermal.rock_gen(output, below(block), adjacent(block))`   |     Igneous Extruder      |     -      |
|           `thermal.numismatic_fuel(input, energy)`            |      Numismatic Dynamo      | Generator  |
|            `thermal.gourmand_fuel(input, energy)`             |       Gourmand Dynamo       | Generator  |
|            `thermal.magmatic_fuel(input, energy)`             |       Magmatic Dynamo       | Generator  |
|            `thermal.lapidary_fuel(input, energy)`             |       Lapidary Dynamo       | Generator  |
|            `thermal.stirling_fuel(input, energy)`             |       Stirling Dynamo       | Generator  |
|         `thermal.disenchantment_fuel(input, energy)`          |     Disenchantment Dynamo    | Generator  |
|           `thermal.compression_fuel(input, energy)`           |     Compression Dynamo     | Generator  |
