# ProbeJS Basics
> Note (Gu-meng): this page does not teach VS Code basics. If you are not familiar with VS Code, learn it first.

First, [download ProbeJS](https://www.mcmod.cn/class/6486.html). You also need [VS Code (Visual Studio Code)](https://code.visualstudio.com). In VS Code, install the extension: [ProbeJS](https://marketplace.visualstudio.com/items?itemName=Prunoideae.probejs).

**Disclaimer:** this page uses ProbeJS 6 as the example. Issues specific to other ProbeJS versions are out of scope.

## What ProbeJS Is For
From the MCBBS/MCMod.cn description:
**It collects information about blocks, items, etc. in your modpack for VS Code to use.**

**After installing the mod and running a single command, VS Code can provide code completion and hover hints for your KubeJS scripts and other config files.**

In short: it makes writing KubeJS scripts much easier.

## Using The ProbeJS Mod
The [ProbeJS](https://www.mcmod.cn/class/6486.html) page already explains usage in detail.

Here is the short version:

The first time you use ProbeJS, launch the game once, then run `/probejs dump` in-game. Wait a moment and the mod will generate files. Then open your `kubejs` folder with VS Code (you can also drag the folder into VS Code).
> Note (Gu-meng): you can also open it from your game installation directory.

If you later add/remove mods, run `/probejs dump` again so ProbeJS regenerates the files.

> From the wiki: if VS Code does not refresh auto-completion after you rerun the command, press `F1` in VS Code and run `TypeScript: Restart TS server` to force a refresh.

## Common Usage In VS Code

### Code Completion
If the steps above are correct, VS Code can use the generated `probejs` files to provide auto-completion.

For example, without completion it is easy to mistype `ServerEvents.recipes`, which can break your script.

With the mod and extension working, you can type `ser` and VS Code will suggest `ServerEvents` and related entries. After you type `.`, VS Code will list methods under `ServerEvents`. You can then select `recipes` (or type `re` or more letters to filter), reducing typing mistakes.

Besides completing identifiers, VS Code can also complete method arguments. When you need to pass an item id, block id, or other values, VS Code can suggest candidates based on the parameter types.

### Viewing Methods
When writing scripts, you may not know what methods a class provides, or whether it contains a method returning the type you need. In VS Code, you can hold `Ctrl` and click a method to jump to its definition. For example:

```js
ItemEvents.firstLeftClicked(event=>{})
```
In this code, if you do not know what values are available on `event`, hold `Ctrl` and left-click `firstLeftClicked`. You will see something like:

```js
/**
 * Invoked when a player right clicks with an item **without targeting anything**.
 *
 * Not to be confused with `BlockEvents.rightClick` or `ItemEvents.entityInteracted`.
 * 
 * @at *server, client*
 */
firstLeftClicked(extra: Internal.Item_, handler: (event: Internal.ItemClickedEventJS) => void):void,
firstLeftClicked(handler: (event: Internal.ItemClickedEventJS) => void):void,
```
How to read this:
* `firstLeftClicked` is the "item first left click" event.
* There are two overloads: one that filters by a specific item, and one that triggers for any item.
* `handler: (event: Internal.ItemClickedEventJS)` means your handler function receives an event object of type `ItemClickedEventJS`.
* The trailing `void` means the method returns nothing.
* Pay attention to `@at` in the comment: it tells you where the event runs (for example `server`, `client`, or both).

Next, you typically inspect `ItemClickedEventJS` (the `event` in `event => {}`) to see what it provides.

After clicking into it, you will see many members. Here are a few important ones:
```ts
class ItemClickedEventJS extends Internal.PlayerEventJS {
    constructor(player: Internal.Player_, hand: Internal.InteractionHand_, item: Internal.ItemStack_)
    /**
     * The hand that the item was clicked with.
     */
    getHand(): Internal.InteractionHand;
    get hand(): Internal.InteractionHand
}
```
Notes:
* `extends Internal.PlayerEventJS` means `ItemClickedEventJS` inherits methods from `PlayerEventJS` as well.
* The `constructor(...)` is the class constructor. When using the event you can usually ignore it.
* `getHand()` and `get hand()` are equivalent from the caller perspective: `event.hand` is the same as `event.getHand()`.
* `get hand()` looks strange at first, but it is a getter property. If there were a `set hand(...)`, you could assign it via `event.hand = ...`. Since there is only `get`, it is read-only here.
* The return type `Internal.InteractionHand` means the value is an `InteractionHand`.

If you Ctrl-click into `InteractionHand`, you'll see things like:
```js
compareTo(arg0: Internal.InteractionHand_): number;
"compareTo(net.minecraft.world.InteractionHand)"(arg0: Internal.InteractionHand_): number;
"compareTo(java.lang.Object)"(arg0: any): number;
static valueOf(arg0: string): Internal.InteractionHand;
static readonly MAIN_HAND: (Internal.InteractionHand) & (Internal.InteractionHand);
```
How to read this:
* The first three lines are similar. The quoted overload names exist because KubeJS cannot always disambiguate methods with the same name but different parameter types. In that case you can explicitly select an overload, for example: `event.hand["compareTo(net.minecraft.world.InteractionHand)"](arg)`.
* `static valueOf(arg0: string)` is a static method.
* `static readonly MAIN_HAND` is a static, read-only field of type `InteractionHand`.
* To use static members, you usually load the class via `Java.loadClass()`, for example `Java.loadClass("net.minecraft.world.InteractionHand")`, and then access its static methods/fields.
```js
const $InteractionHand = Java.loadClass("net.minecraft.world.InteractionHand")
ItemEvents.firstLeftClicked(event=>{
    let hand = event.hand
    if ($InteractionHand.MAIN_HAND == hand){
        // code
    }
})
```
This compares whether the hand from the event is the main hand or off hand via the static `MAIN_HAND` field. (This is more about `Java.loadClass()` than ProbeJS itself.)

### Quick Insert
After installing the extension, you can use `@` snippets. Common ones include `@item`, `@dimension`, `@block`, `@entity`, etc. After you type them, VS Code will insert a suggested value at the cursor. For example, typing `@item` can insert an item id string.

### JSDoc
This is slightly out of scope, but worth mentioning: ProbeJS types are sometimes not specific enough, which can reduce completion quality. In those cases, you can tell VS Code the type you want using JSDoc, for example:
```js
ItemEvents.firstLeftClicked(event=>{
    event.player
})
```
If you look at `get player(): Internal.Player`, it claims the return type is `Player`. But is it really?

You can log it:

```js
ItemEvents.firstLeftClicked(event=>{
    console.log(event.player);
})
```
You may see output like `... [net.minecraft.server.level.ServerPlayer]`. The important part is the runtime type: `net.minecraft.server.level.ServerPlayer` (i.e. `ServerPlayer`), not the generic `Player` ProbeJS displayed. That means some `ServerPlayer` methods will not autocomplete unless you specify the type yourself:

```js
ItemEvents.firstLeftClicked(event=>{
    /**
     * @type {Internal.ServerPlayer}
     */
    let serverPlayer = event.player;
})
```
This uses `@type` to specify that `serverPlayer` should be treated as `Internal.ServerPlayer`.

In ProbeJS, most classes are available under `Internal`. If a class you need does not show up, ProbeJS may not have indexed it; in that case you may need to read the Java source for that class. (This is uncommon for vanilla content.)

[See more JSDoc usage examples](https://docs.variedmc.cc/zh/modpack/kubejs/1.20.1/Introduction/Addon/ProbeJS/JSDoc)
