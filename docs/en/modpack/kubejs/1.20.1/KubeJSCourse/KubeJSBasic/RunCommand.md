---
authors: ['Gu-meng']
---
# Run In-Game Commands
KubeJS provides server-side methods that can run Minecraft commands directly.

You can use the global static class `Utils`, access its `server` property, and call `runCommand`.

Example:
```js
Utils.server.runCommand('kill @e[type="item"]');
```
Each time this line runs, it executes the command and removes all dropped items.

However, `runCommand` prints feedback in chat. If you want to hide that from players, use:

```js
Utils.server.runCommandSilent('kill @e[type="item"]');
```

This runs the command without chat output.

Tip: if you can get the server object from an event, use that server instance to call `runCommand` / `runCommandSilent` instead of `Utils.server`. This helps avoid hard-to-debug issues.
