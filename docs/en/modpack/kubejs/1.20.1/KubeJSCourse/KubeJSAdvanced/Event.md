---
authors: ['Gu-meng']
---
# Events
## Quick Q&A
### What is an event?
You reading this document is an event. Leaving a like/comment/share on Gu-meng's video is also an event.

### What can you do once you capture an event?
What an event does depends on the developer. For example, "reading the document" triggers an event, and your event logic could then decide to do something next.

In code terms: first, an action happens and triggers an event; then you write logic inside that event callback to define what should happen.

### What are event examples in-game?
* If you right-click TNT with flint and steel, TNT explodes after a delay. This is a [block right-click event](./EventExamples/BlockRightClickedEvent.md): right-clicking TNT triggers the block event, checks whether you are holding flint and steel, and then triggers TNT ignition.
* If an entity is hit by a shulker projectile and starts floating, that is an [entity hurt event](): check the damage source and apply Levitation if it was caused by a shulker.
* If a player eats a golden apple and receives potion effects, that is an [item consumed event](): after eating finishes, apply the configured effects.

## Events Directly Available in KubeJS
There are many events in gameplay. Choosing the right one at the right point lets you add richer mechanics, so your modpack feels more than "just recipe edits."

[All Events and Usage Examples](../KubeJSBasic/AllEvent)
