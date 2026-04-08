# Key Registration and Usage
Topics covered: key registration, client-to-server packets, and key handling.
Mods and versions used:
1. rhino-forge-2001.2.2-build.18
2. architectury-9.2.14-forge
3. kubejs-forge-2001.6.5-build.14
4. probejs-6.0.1-forge

## Code in `startup`
Key registration. **Important: although this is written in `startup`, it is still client-side content. Remove this file when building a dedicated server package. Removing it for server packaging will not affect clients.**
```js
const $KeyMappingRegistry = Java.loadClass("dev.architectury.registry.client.keymappings.KeyMappingRegistry");
const $KeyMapping = Java.loadClass("net.minecraft.client.KeyMapping");
const $GLFWkey = Java.loadClass("org.lwjgl.glfw.GLFW");

ClientEvents.init(() => {
    global.regKeyB = new $KeyMapping(
        "key.meng.packsack", // key category
        $GLFWkey.GLFW_KEY_B,
        "key.keybinding.meng.packsack" // key name
    );
  $KeyMappingRegistry.register(global.regKeyB);
});
```
The key category and key name are language keys, so they should be translated in the corresponding lang file, for example:

```json
{
    "key.meng.packsack":"Backpack",
    "key.keybinding.meng.packsack":"Open Curios backpack"
}
```
These values are fully customizable.

You can find the `GLFWkey` key map table at the end of this page.

## Code in `client`
```js
ClientEvents.tick(event => {
    const key = global.regKeyB;
    if (key.isDown()) {
        if (!event.player.getPersistentData().getBoolean("openBackpack")) {
            event.player.sendData("openBackpack")
            event.player.getPersistentData().putBoolean("openBackpack", true);
        }
    } else {
        if (event.player.getPersistentData().getBoolean("openBackpack")) {
            event.player.getPersistentData().putBoolean("openBackpack", false);
        }
    }
})
```
This sends a packet to the server to notify that the player pressed the key. Otherwise, the server cannot know the key state. A simple check is included to prevent continuous packet spam while holding the key.

This code sends the packet only on the initial key press.

## Code in `server`
```js
NetworkEvents.dataReceived("openBackpack", event => {
    const player = event.player
    // Your handling logic here
})
```
On the server side, you only need to receive this packet, since no extra payload is sent.

## Notes
1. This project is only an example; many parts are not necessarily optimal and can be improved.
2. If you improve this project, you can upload your revised code to the [Gitee repository](https://gitee.com/gumengmengs/kubejs-course).
3. When building a dedicated server package, remove all code from this file in `startup`, including `loadClass` calls.

## GLFWkey Mapping
|      Key Constant       |         Mapped Key         |
| :--------------------: | :------------------------: |
|     GLFW_KEY_SPACE     |           Space            |
|  GLFW_KEY_APOSTROPHE   |             '              |
|     GLFW_KEY_COMMA     |             ,              |
|     GLFW_KEY_MINUS     |             -              |
|    GLFW_KEY_PERIOD     |             .              |
|     GLFW_KEY_SLASH     |             /              |
|       GLFW_KEY_0       |             0              |
|       GLFW_KEY_1       |             1              |
|       GLFW_KEY_2       |             2              |
|       GLFW_KEY_3       |             3              |
|       GLFW_KEY_4       |             4              |
|       GLFW_KEY_5       |             5              |
|       GLFW_KEY_6       |             6              |
|       GLFW_KEY_7       |             7              |
|       GLFW_KEY_8       |             8              |
|       GLFW_KEY_9       |             9              |
|   GLFW_KEY_SEMICOLON   |             ;              |
|     GLFW_KEY_EQUAL     |             =              |
|       GLFW_KEY_A       |             A              |
|       GLFW_KEY_B       |             B              |
|       GLFW_KEY_C       |             C              |
|       GLFW_KEY_D       |             D              |
|       GLFW_KEY_E       |             E              |
|       GLFW_KEY_F       |             F              |
|       GLFW_KEY_G       |             G              |
|       GLFW_KEY_H       |             H              |
|       GLFW_KEY_I       |             I              |
|       GLFW_KEY_J       |             J              |
|       GLFW_KEY_K       |             K              |
|       GLFW_KEY_L       |             L              |
|       GLFW_KEY_M       |             M              |
|       GLFW_KEY_N       |             N              |
|       GLFW_KEY_O       |             O              |
|       GLFW_KEY_P       |             P              |
|       GLFW_KEY_Q       |             Q              |
|       GLFW_KEY_R       |             R              |
|       GLFW_KEY_S       |             S              |
|       GLFW_KEY_T       |             T              |
|       GLFW_KEY_U       |             U              |
|       GLFW_KEY_V       |             V              |
|       GLFW_KEY_W       |             W              |
|       GLFW_KEY_X       |             X              |
|       GLFW_KEY_Y       |             Y              |
|       GLFW_KEY_Z       |             Z              |
| GLFW_KEY_LEFT_BRACKET  |             [              |
|   GLFW_KEY_BACKSLASH   |             \              |
| GLFW_KEY_RIGHT_BRACKET |             ]              |
| GLFW_KEY_GRAVE_ACCENT  |             `              |
|    GLFW_KEY_ESCAPE     |            ESC             |
|     GLFW_KEY_ENTER     |            Enter           |
|      GLFW_KEY_TAB      |            Tab             |
|   GLFW_KEY_BACKSPACE   |         Backspace          |
|    GLFW_KEY_INSERT     |           Insert           |
|    GLFW_KEY_DELETE     |           Delete           |
|     GLFW_KEY_RIGHT     |         Arrow Right        |
|     GLFW_KEY_LEFT      |         Arrow Left         |
|     GLFW_KEY_DOWN      |         Arrow Down         |
|      GLFW_KEY_UP       |          Arrow Up          |
|    GLFW_KEY_PAGE_UP    |        PageUp/PgUp         |
|   GLFW_KEY_PAGE_DOWN   |       PageDown/PgOn        |
|     GLFW_KEY_HOME      |            Home            |
|      GLFW_KEY_END      |            End             |
|   GLFW_KEY_CAPS_LOCK   |         Caps Lock          |
|  GLFW_KEY_SCROLL_LOCK  |      Scroll Lock/ScLk      |
|   GLFW_KEY_NUM_LOCK    |          Num Lock          |
| GLFW_KEY_PRINT_SCREEN  |        Print Screen        |
|     GLFW_KEY_PAUSE     |      Pause Break/PaBk      |
|      GLFW_KEY_F1       |             F1             |
|      GLFW_KEY_F2       |             F2             |
|      GLFW_KEY_F3       |             F3             |
|      GLFW_KEY_F4       |             F4             |
|      GLFW_KEY_F5       |             F5             |
|      GLFW_KEY_F6       |             F6             |
|      GLFW_KEY_F7       |             F7             |
|      GLFW_KEY_F8       |             F8             |
|      GLFW_KEY_F9       |             F9             |
|      GLFW_KEY_F10      |            F10             |
|      GLFW_KEY_F11      |            F11             |
|      GLFW_KEY_F12      |            F12             |
|     GLFW_KEY_KP_0      |         Numpad 0           |
|     GLFW_KEY_KP_1      |         Numpad 1           |
|     GLFW_KEY_KP_2      |         Numpad 2           |
|     GLFW_KEY_KP_3      |         Numpad 3           |
|     GLFW_KEY_KP_4      |         Numpad 4           |
|     GLFW_KEY_KP_5      |         Numpad 5           |
|     GLFW_KEY_KP_6      |         Numpad 6           |
|     GLFW_KEY_KP_7      |         Numpad 7           |
|     GLFW_KEY_KP_8      |         Numpad 8           |
|     GLFW_KEY_KP_9      |         Numpad 9           |
|  GLFW_KEY_KP_DECIMAL   |         Numpad .           |
|   GLFW_KEY_KP_DIVIDE   |         Numpad /           |
|  GLFW_KEY_KP_MULTIPLY  |         Numpad *           |
|  GLFW_KEY_KP_SUBTRACT  |         Numpad -           |
|    GLFW_KEY_KP_ADD     |         Numpad +           |
|   GLFW_KEY_KP_ENTER    |        Numpad Enter        |
|   GLFW_KEY_KP_EQUAL    |         Numpad =           |
|  GLFW_KEY_LEFT_SHIFT   |        Left Shift          |
| GLFW_KEY_LEFT_CONTROL  |        Left Ctrl           |
|   GLFW_KEY_LEFT_ALT    |        Left Alt            |
|  GLFW_KEY_LEFT_SUPER   | Left Windows / Left Command (Mac) |
|  GLFW_KEY_RIGHT_SHIFT  |       Right Shift          |
| GLFW_KEY_RIGHT_CONTROL |       Right Ctrl           |
|   GLFW_KEY_RIGHT_ALT   |       Right Alt            |
|  GLFW_KEY_RIGHT_SUPER  | Right Windows / Right Command (Mac) |
