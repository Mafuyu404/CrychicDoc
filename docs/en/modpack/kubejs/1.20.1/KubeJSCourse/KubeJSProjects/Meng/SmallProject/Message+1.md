# Chat Message +1
Main topics in this chapter: `Component` and chat events. All code in this chapter belongs in `server_scripts`.

Mods and versions used:
1. jei-1.20.1-forge-15.3.0.4
2. rhino-forge-2001.2.2-build.18
3. architectury-9.2.14-forge
4. kubejs-forge-2001.6.5-build.14
5. probejs-6.0.1-forge

## Full Code
```js
PlayerEvents.decorateChat(event=>{
    const msg = event.getMessage();
    event.setMessage(Text.of(msg).clickSuggestCommand(msg).hover(Text.of("+1")))
})
```
After a player sends a message, the script rewrites the message component so it can be clicked to suggest the same text in chat input, and hovering shows a `+1` tooltip.
