---
authors: ['Gu-meng']
---
# Server Daily Sign-In
Topics covered: player chat events, command registration, and `JsonIO`. All code in this chapter belongs in `server_scripts`.

Mods and versions used:
1. jei-1.20.1-forge-15.3.0.4
2. rhino-forge-2001.2.2-build.18
3. architectury-9.2.14-forge
4. kubejs-forge-2001.6.5-build.14
5. probejs-7.0.1-forge

## Full Code
Create a `meng` folder in the game directory first, then create an empty `qd.json` file.
```js
ServerEvents.commandRegistry(event =>{
    const { commands: Commands, arguments: Arguments } = event
    event.register(
        Commands.literal("qd").executes(value =>{
            let username = value.source.getPlayer().username
            let qdJson = JsonIO.readJson("./meng/qd.json").getAsJsonObject()
            let time = qdJson.get("time").getAsString()
            let timeF = qdJson.get("timeF").getAsString()
            let players = qdJson.get("players").getAsJsonArray()
            let i = 0
            for (let index = 0; index < players.size(); index++) {
                let player = players.get(index).getAsJsonObject()
                let playerName = player.get("name").getAsString()
                if (playerName == username){
                    let endQdTime = player.get("endQdTime").getAsString()
                    if (timeCompare(endQdTime)){
                        let qdDay = player.get("qdDay").getAsNumber()
                        let newQdDay = qdDay + 1
                        player.addProperty("qdDay",newQdDay)
                        player.addProperty("endQdTime",new Date().getTime().toString())
                        let strDay = newQdDay.toString().split(".")[0]
                        Utils.server.tell("Player " + username + " signed in successfully on " + timeF + ". Total sign-in days: " + strDay)
                    }else{
                        value.source.getPlayer().tell("You have already signed in today. No need to sign in again.")
                    }
                    i = 1
                    break
                }
            }
            if(i == 0){
                players["add(com.google.gson.JsonElement)"]({name:username,qdDay:"1.0",endQdTime:new Date().getTime().toString()})
                Utils.server.tell("Player " + username + " signed in successfully on " + timeF + ". Total sign-in days: 1")
            }
            JsonIO.write("./meng/qd.json",qdJson)
            return 1
        })
    )
})


function getTime(str) {
    let currentTime = new Date(Number(str))
    return currentTime.getFullYear() + "-" + (currentTime.getMonth() + 1) + "-" + currentTime.getDate()
}

function timeCompare(str){
    let oldTime = new Date(Number(str))
    let newTime = new Date()
    return oldTime.getFullYear() < newTime.getFullYear() || 
            oldTime.getMonth() < newTime.getMonth() ||
            oldTime.getDate() < newTime.getDate()
}
```

## Notes
1. This project is only an example; many parts are not necessarily optimal and can be improved.
2. If you improve this project, you can upload your revised code to the [Gitee repository](https://gitee.com/gumengmengs/kubejs-course).
