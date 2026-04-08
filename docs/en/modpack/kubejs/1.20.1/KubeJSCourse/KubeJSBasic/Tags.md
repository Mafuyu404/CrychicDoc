---
authors: ['Gu-meng']
---
# Add and Remove Tags

[Tag introduction](../Digression/Tag.md)

In-game, hold **oak log** and run `/kjs hand`. Besides the item id, you will also see ids that start with `#`. These are tag ids.
You can think of tags as "groups". For example, `#minecraft:logs` is the group for all log items.

Common tag types are block tags, fluid tags, and item tags.

You can modify existing tags like this:
```js
ServerEvents.tags("block",event=>{})

ServerEvents.tags("fluid",event=>{})

ServerEvents.tags("item",event=>{})
```
Choose the tag type based on your use case.

## Example
```js
ServerEvents.tags("item",event=>{
    // Remove oak log from the logs tag
    event.remove("minecraft:logs",['minecraft:oak_log']);
    // Remove all entries from the planks tag
    event.removeAll('minecraft:planks');
    // Add bedrock to the logs tag
    event.add("minecraft:logs",['minecraft:bedrock']);
})
```
The example above only shows item tag changes.

Other tag types work the same way, using `remove`, `removeAll`, and `add`.
