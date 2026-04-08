# Detect a Player's First Login
Main topics in this chapter: player login events and game stages. All code in this chapter belongs in `server_scripts`.

Mods and versions used:
1. jei-1.20.1-forge-15.3.0.4
2. rhino-forge-2001.2.2-build.18
3. architectury-9.2.14-forge
4. kubejs-forge-2001.6.5-build.14
5. probejs-6.0.1-forge

## Full Code
```js
PlayerEvents.loggedIn(event=>{
    const player = event.player;
    if(!player.stages.has("oneInGame")){
        player.tell("Welcome to the game for your first time")
        player.stages.add("oneInGame")
    }
})
```
This code detects a player's first login, sends a message, and uses KubeJS built-in stages to record that state.
