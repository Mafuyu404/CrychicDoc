---
authors: ['Gu-meng']
---
# Villager Gift Loot
Villager gift loot is used when the player has Hero of the Village. [Different villagers](../../Digression/LootTableId.md/#villager-gift-loot-tables) give different gifts.
## Override Villager Gifts
```js
ServerEvents.giftLootTables(e=>{
    e.addGift("armorer_gift",l=>{
        l.addPool(p=>{
            p.addItem("diamond")
        })
    })
})
```
This changes the armorer's gift to a diamond.
## Add to Villager Gifts
```js
ServerEvents.giftLootTables(e=>{
    e.modify("armorer_gift",l=>{
        l.addPool(p=>{
            p.addItem("diamond")
        })
    })
})
```
Now the armorer gives an extra diamond each time they give a gift.
