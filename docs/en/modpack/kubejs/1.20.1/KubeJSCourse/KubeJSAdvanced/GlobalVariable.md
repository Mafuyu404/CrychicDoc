---
authors: ['Gu-meng']
---
# `global` Variables
In KubeJS, `global` is a shared variable space that can be accessed from `server`, `startup`, and `client` scripts.

## Quick Way to Understand It
First, create `GlobalTest.js` under the `server` scripts folder, then write:
```js
global.testLog = (message) =>{
    console.log(message)
}
global.testNumber = 10;
global.testMessage = "hello,meng"
global.testList = [
    1,2,3,4
]
global.testObj = {
    h : 1,
    b : 2
}
```

After that, add this in a `client` script:
```js
global.testLog(global.testMessage)
console.log(global.testMessage)
```

Then add this in `startup`:
```js
global.testList.forEach(value=>{
    global.testLog(value)
});
console.log(global.testObj.h)
console.log(global.testObj.b)
```

Now run commands in-game in this order:
1. /kjs reload server_scripts
Because the global variable definitions are in `server`, load this folder first so they are available in KubeJS.
2. /kjs reload client_scripts
After running this, check `/logs/kubejs/server.log` and `/logs/kubejs/client.log`.

In `server.log`, you will see a `"hello,meng"` output line from `global.testLog(global.testMessage)`.

In `client.log`, you will also see `"hello,meng"` from `console.log(global.testMessage)`.
3. /kjs reload startup_scripts
After this, check `/logs/kubejs/server.log` and `/logs/kubejs/startup.log`.

`server.log` prints the array values `1` `2` `3` `4`, showing successful access.

`startup.log` prints `1` and `2`, because it read values from the global object.

## Summary
Normally, calling variables/functions across these script areas is not straightforward.

With `global`, you can access them from all script scopes.

## Use Cases
Global variables are used in the project [Altar Composition](../KubeJSProjects/Meng/AltarComposition.md).
