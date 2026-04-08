---
authors: ['Gu-meng']
---
# Export All Recipe JSONs to Files
Main topics in this chapter: iterating over all recipes and `JsonIO`. All code in this chapter belongs in `server_scripts`.

Mods and versions used:
1. jei-1.20.1-forge-15.3.0.4
2. rhino-forge-2001.2.2-build.18
3. architectury-9.2.14-forge
4. kubejs-forge-2001.6.5-build.14
5. probejs-6.0.1-forge

## Full Code
Create a `recipes` folder in the game directory path ahead of time, otherwise writing files will fail with a missing-path error.
```js
ServerEvents.recipes(event=>{
    event.forEachRecipe({},recipe=>{
        JsonIO.write("./recipes/" + String(recipe.getId()).replace(/:|\//g, '_') + ".json",recipe.json)
    })
})
```
