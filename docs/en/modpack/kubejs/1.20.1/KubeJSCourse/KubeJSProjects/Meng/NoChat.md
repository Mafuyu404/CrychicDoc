---
authors: ['Gu-meng']
---
# Mute Players
Topics covered: player chat events, command registration, and `JsonIO`. All code in this chapter belongs in `server_scripts`.

Mods and versions used:
1. jei-1.20.1-forge-15.3.0.4
2. rhino-forge-2001.2.2-build.18
3. architectury-9.2.14-forge
4. kubejs-forge-2001.6.5-build.14
5. probejs-7.0.1-forge

## Command Registration
```js
// Register a command to mute a player
ServerEvents.commandRegistry(event => {
    const { commands: Commands, arguments: Arguments } = event
    event.register(
        Commands.literal("jy")
            .then(
                Commands.argument('playerName', Arguments.PLAYER.create(event))
                    .then(
                        Commands.argument('minute', Arguments.INTEGER.create(event))
                            .executes(value => {
                                const player = Arguments.PLAYER.getResult(value, "playerName")
                                const minute = Arguments.INTEGER.getResult(value, "minute")
                                console.log(player + minute);
                                setPlayerNoChat(player.username,minute)
                                player.tell("You have been muted by an admin for " + minute + " minute(s)")
                                value.source.player.tell(player.username + " has been muted for " + minute + " minute(s)")
                                return 1
                            })
                    )
            )
    )
})
```

## Mute Logic
```js
const { $JsonArray } = require("packages/com/google/gson/$JsonArray")
const { $JsonObject } = require("packages/com/google/gson/$JsonObject")
// File path
const fileUrl = "./meng/jy.json"

function setPlayerNoChat(player, time) {
    let json = JsonIO.readJson(fileUrl)
    let arr = json.get("data").getAsJsonArray()
    /**
     * @type {$JsonObject}
     */
    let playerValue = getFilePlayer(arr,player)
    console.log(playerValue == null);
    if (playerValue == null) {
        addNoChat(player, time, json, new Date().getTime().toString())
    } else {
        time += playerValue.get("time").asInt
        console.log(time);
        updateNoChat(json,player, time)
    }
}

function getPlayerChatState(player){
    let json = JsonIO.readJson(fileUrl)
    let arr = json.get("data").getAsJsonArray()
    let playerValue = getFilePlayer(arr,player)
    if (playerValue != null) {
        if (playerValue.get("isNoChat").asBoolean){
            let value = compareTimestamps(
                playerValue.get("NoChatTime").asString,
                playerValue.get("time").asInt,
            )
            if (value){
                return true
            }else{
                updateNoChat(json,player,-1)
            }
        }
    }
    return false
}
/**
 * Check whether the mute should still be active.
 * @param {*} currentTimestamp timestamp
 * @param {*} minutes minutes
 * @returns true if in the future, false if in the past
 */
function compareTimestamps(currentTimestamp, minutes) {
    const currentDate = new Date(Number(currentTimestamp));
    const futureDate = new Date(currentDate.getTime() + minutes * 60000);
    const futureTimestamp = futureDate.getTime();
    const newDate = new Date().getTime()
    return newDate < futureTimestamp ? true : false;
}

/**
 * Add a mute entry
 */
function addNoChat(player, time, file, dateString) {
    let arr = file.get("data").getAsJsonArray()
    /**
     * @type {$JsonArray}
     */
    arr["add(com.google.gson.JsonElement)"]({
        playerName: player,
        isNoChat: true,
        time: time,
        NoChatTime: dateString
    })
    JsonIO.write(fileUrl, file)
}

/**
 * Update mute status
 */
function updateNoChat(file, player, time) {
    let arr = file.get("data").getAsJsonArray()
    for (let index = 0; index < arr.size(); index++) {
        /**
         * @type {$JsonObject}
         */
        let obj = arr.get(index)
        if (obj.get("playerName").asString == player) {
            if (time == -1) {
                obj.add("time", 0)
                obj.add("isNoChat", false)
            } else {
                if (!obj.get("isNoChat").asBoolean){
                    obj.add("NoChatTime",new Date().getTime().toString())
                }
                obj.add("time", time)
                obj.add("isNoChat", true)
            }
        }
    }
    JsonIO.write(fileUrl, file)
}

/**
 * Check whether the player exists in the mute list
 * @returns matching mute record
 */
function getFilePlayer(arr,playerName) {
    for (let index = 0; index < arr.size(); index++) {
        if (arr.get(index).get("playerName").asString == playerName){
            return arr.get(index)
        }
    }
    return null
}
```

## Block Muted Player Chat
```js
PlayerEvents.chat(event=>{
    if (getPlayerChatState(event.getUsername())){
        event.getPlayer().tell("You cannot speak right now. You are muted.")
        event.cancel()
    }
})
```

## Notes
1. This project is only an example; many parts are not necessarily optimal and can be improved.
2. If you improve this project, you can upload your revised code to the [Gitee repository](https://gitee.com/gumengmengs/kubejs-course).
