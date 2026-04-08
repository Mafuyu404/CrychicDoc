---
authors: ['Gu-meng','QiHuang02']
---
# Add a Music Disc

This chapter shows how to create a music disc that can play audio. All JS code in this chapter is placed in `startup_scripts` and `server_scripts`.

## Add a Sound Event

Before adding the disc item, you need to register the sound first. Otherwise, the disc has no playable audio.

```js
StartupEvents.registry("sound_event", (event) => {
    event.create("meng:music.my_music")
})
```

That one line is enough to register the sound event. The argument is the sound id.

### Note: In 1.21.1+

In 1.21.1+, if you want to register a music disc, you also need this `server` registry step to register the audio as a jukebox song.

```js
ServerEvents.registry('jukebox_song', (event) => {
    event.create('music_id')
         .song('sound_event_id', time)

    // Example
    event.create('meng:my_music')
         .song("meng:music.my_music", 103)
})
```

`music_id` is the id of this jukebox song entry. It can be any id, but keep it consistent with later usage.

`sound_event_id` is the sound id you registered in the startup event.

`time` is the playback duration.

## Register the Item

Now you can register the music disc item.

```js
StartupEvents.registry("item", (event) => {
    event.create("meng:my_music_disc", "music_disc")
        .song("meng:music.my_music", 103)
        .tag("music_discs")
})
```

In `.song()`, the first parameter is the registered sound id, and the second is duration (in seconds).

`.tag("music_discs")` is **required**. It tells the game this item belongs to the disc tag so jukeboxes can use it correctly.

### Note: In 1.21.1+

```js
StartupEvents.registry('item', (event) => {
    event.create('item_id')
         .jukeboxPlayable('music_id', true)

    // Example
    event.create('meng:my_music')
         .jukeboxPlayable('meng:my_music', true)
})
```

`item_id` is the item id. Do not confuse it with the jukebox song id above. You can keep them the same to avoid confusion.

`music_id` is the same `music_id` used when registering the jukebox song.

Since Minecraft 1.20.5 introduced [item stack components](https://minecraft.wiki/w/Data_component_format), almost any item can be made jukebox-playable.

## Prepare the Audio

After registering the sound and disc, add the actual audio file.

First, use this website: [mp3->ogg](https://audio.online-convert.com/convert-to-ogg)

Minecraft sound files use `.ogg`, so convert your mp3 to ogg.

### Create the Sound Folder

Then create folders and place your audio file there.

Put the ogg file under `kubejs/assets/meng/sounds`. Here, `meng` is the namespace (the part before `:`). If you do not set a custom namespace, use `kubejs/assets/kubejs/sounds`.

Rename the file. It is best to match the part after `music.` in your id (custom names work, but are not recommended). In this example the file is `my_music.ogg`, full path `kubejs/assets/meng/sounds/my_music.ogg`.

### Configure `sounds.json`

Go to `kubejs/assets/<namespace>`, create `sounds.json`, then edit it.

[For full details, see mcwiki](https://zh.minecraft.wiki/w/Sounds.json?variant=zh-cn)

```json
{
    // Registered sound id
    "music.my_music": {
        // Fixed format
        "sounds": [
            {
                // Sound path folder, i.e. kubejs/assets/<namespace>/sounds above
                "name": "meng:my_music",
                // Stream output, recommended true for music discs
                "stream": true
            }
        ]
    },
}
```

### In 1.21.1+

```json
{
    // 'meng:my_music' here refers to the `music_id` used when registering jukebox_song
    "music.my_music": {
        // Fixed format
        "sounds": [
            {
                // Sound path folder, i.e. kubejs/assets/meng/sounds above
                "name": "meng:my_music",
                // Stream output, recommended true for music discs
                "stream": true
            }
        ]
    },
}
```

[About the `sounds.json` file structure](../../../Digression/SoundsJson)

## Localization and Texture

Next, add localization text and item texture.

Go to `kubejs/assets/meng/lang` and create `zh_ch.json` (or open it if it already exists).

Add translations:

```json
{
    "item.meng.my_music": "Music Disc",
    "item.meng.my_music.desc": "My Music - Private Artist"
}
```

### In 1.21.1+

```json
{
    "item.meng.mymusic": "Music Disc",
    "jukebox_song.meng.mymusic": "My Music - Private Artist",
    "sound.meng.mymusic": "My Music"
}
```

The first key is the text for the disc item itself.

The second key is the song text shown for the disc.

For texture, put your disc image in `kubejs/assets/meng/textures/item` and name it `my_music.png` (matching the item id).

## Notes and Audio Download

If everything above is set correctly (disc inserts into jukebox, no script errors) but there is still no sound, try the sample audio provided by Gu Meng: [my_music.ogg](https://gitee.com/gumengmengs/kubejs-course/blob/main/files/my_music.ogg). If the link is unavailable, contact Gu Meng on [Bilibili](https://space.bilibili.com/16632546).
