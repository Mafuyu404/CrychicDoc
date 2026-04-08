---
authors: ['Gu-meng']
---
# Item Tooltips
In-game, some items (such as smithing templates or enchanted books) show extra hint text in their tooltip.
This chapter shows how to create that effect.
## Basic Usage
Tooltip editing is a client-side rendering feature, so write it in `client_scripts`.

```js
ItemEvents.tooltip((event) =>{
    // Add plain text (shown at the bottom by default)
    event.add('diamond', "Add a normal tooltip line")
    // Add multiple lines with an array
    event.add('diamond', ["Array line 1","Array line 2"])
    // Build dynamic text with string concatenation
    event.add('diamond', Text.of("This item now belongs to ").append(Client.player.username))
    // Change text color by applying color methods
    event.add('diamond', Text.of("This item now belongs to ").append(Client.player.username).red())
})
```


The previous example is the simplest form.
If you want text to appear only while special keys are held, use `addAdvanced`:
```js
ItemEvents.tooltip((event) =>{
    event.addAdvanced("diamond", (item,advanced,text) =>{
        // You can remove existing lines and replace them directly
        // Index 0 is usually the item name line
        text.remove(0)
        text.add(0,"Normal Diamond")
        // Simple multi-color text composition example
        text.add(1,Text.red("C").append(Text.gold("o")).append(Text.darkBlue("l")).append(Text.blue("o")).append(Text.white("r")).append(Text.green("f")).append(Text.gray("u")).append(Text.yellow("l")))

        // Text shown while holding Shift
        if (event.shift){
            text.add(Text.red("You finally held Shift to read this"))
        // Combination keys are supported, but order matters
        // Put combo checks before single-key checks to avoid precedence issues
        }else if(event.alt && event.ctrl){
            text.add(Text.darkPurple("You found a new key combo?!"))
        // Text shown while holding Ctrl
        }else if(event.ctrl){
            text.add(Text.yellow("You finally held Ctrl to read this"))
        // Text shown while holding Alt
        }else if(event.alt){
            text.add(Text.blue("You finally held Alt to read this"))
        }
    })
})
```



## Localization
`tooltip` is very flexible. If your modpack needs multilingual support, localization works here too.

Create your resource-pack-style path under `assets`, then create a `lang` folder.
Example path: `assets/meng/lang`

Example `zh_cn.json`:
```json
{
    "meng.lang.wenben.test" : "This is test text (Chinese locale)"
}
```
Example `en_us.json`:
```json
{
    "meng.lang.wenben.test" : "This is a test text"
}
```

Use it in code like this:
```js
ItemEvents.tooltip((event) =>{
    event.add("diamond",Text.translate("meng.lang.wenben.test"))
})
```

Now the tooltip text changes automatically when the game language changes.
