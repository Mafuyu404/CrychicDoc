---
authors: ['Gu-meng']
---
# Using ForgeEvent
> Gu-meng note: Be careful with Forge events; they are powerful but more complex.

`ForgeEvent` must be written in **startup** scripts. This is important.

`ForgeEvent` is not limited to events provided by Forge itself. If another mod defines an event class that directly extends `Event` in Java source, it can also be captured by ForgeEvent. Example: [Create pipe fluid changes and fluid-generating blocks](../KubeJSProjects/Meng/SmallProject/CreatePipeCollision.md)

ForgeEvent can also be used together with [loadClass](./JavaLoadClass.md).

## Events Provided by Forge
Forge provides many events for capturing game behavior, but browsing all of them manually is tedious. A practical approach is to use the [CRT docs](https://docs.blamejared.com/1.20.1/en) to find matching events.

The `forge -> event` section in the [CRT docs](https://docs.blamejared.com/1.20.1/en) is a good ForgeEvent reference.

You can first locate an event in CRT docs, then find its class in [Forge GitHub source](https://github.com/MinecraftForge/MinecraftForge/tree/1.20.1/src/main/java/net/minecraftforge/event).

## Usage
```js
ForgeEvents.onEvent("EventClass",event=>{
    // Event-handling code
})
```
Each event may expose different methods, so there is no single universal example. For method details, refer to the specific event docs in CRT.

## Event "Hot Reload"
Most event-handling logic can be hot-reloaded, but you need a small pattern.

As shown in [global variables](./GlobalVariable.md), you can route event handling through a `global` function:
```js
ForgeEvents.onEvent("EventClass",event=>{
    global.eventTest(event);
})

global.eventTest = event =>{
    // event handling
}
```
Then in-game, run `/kjs reload startup_scripts` to hot-reload the handler.

## Notes
`startup` scripts are fragile. Small mistakes can cause crashes and Rhino errors.

During testing, wrap logic with `try/catch` to reduce hard crashes.

Example (continuing the previous code):

```js
global.eventTest = event =>{
    try{
        // event handling
    }catch(err){
        console.error(err);
    }
}
```
When errors happen, check `/logs/kubejs/startup.log` to see the cause and adjust your code. Do not ignore caught errors unless they are expected and properly handled to avoid repeated error loops.
