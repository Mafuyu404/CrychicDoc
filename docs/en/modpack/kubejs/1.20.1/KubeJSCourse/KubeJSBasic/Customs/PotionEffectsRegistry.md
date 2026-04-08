---
authors: ['Gu-meng']
---
# Register Potions
Registering a potion in KubeJS is not the same as registering a potion effect. A potion is a bottled set of effects applied when consumed. See [register potion effects](./PotionRegistry.md).
```js
StartupEvents.registry("potion",event =>{
    event.create("meng:my_potion")
})
```
One commonly used method is `effect`, which adds potion effects to the potion.
## `effect` Parameters
Below are the parameter positions, required status, and expected types for `effect`.
| Position |            Type          |   Example  |         Description            |     Required      | Default |
| :------: | :----------------------: | :------: | :----------------------------: | :--------------: | :----: |
|  First   |      Effect id           | "speed"  | Potion has Speed effect        |        Yes        |  None  |
|  Second  |      Duration            | 20 * 10  | Duration is 10 seconds         |        No         | 1 tick |
|  Third   |      Amplifier           |    1     | Effect level II (`0` is level I)|       No         |   0    |
|  Fourth  | Beacon effect flag       |   true   | Blue beacon border on icon     | Required if third is set | false  |
|  Fifth   | Show effect particles    |  false   | Particles hidden               |        No         |  true  |
|  Sixth   | Show effect icon         |  false   | Icon hidden                    |        No         |  true  |

The full syntax looks like this:
```js
event.create("meng:my_potion")
    .effect(
        "speed", // Effect id - 1
        20 * 10, // Duration - 2
        20, // Effect amplifier - 3
        true, // Beacon effect flag - 4
        false, // Show particles - 5
        false // Show icon - 6
    )
```
**Note: when passing later parameters, keep argument order consistent; omit only trailing ones as needed.**
