---
authors: ['Gu-meng']
---
# `sounds.json` Structure
**Note: the following content is adapted from mcwiki.**
```json
{
    "sound_event_id": {
        "replace": (true / false),
        "subtitle": "subtitle text",
        "sounds":[
            {
                "name":"sound_path",
                "volume": (0.0 ~ 1.0),
                "pitch": 1.0,
                "weight": 1,
                "stream": (true / false),
                "attenuation_distance": 32,
                "preload": (true / false),
                "type": ("file" / "event")
            }
        ]
    }
}
```
`sound_event_id`: A sound event. Sound event names are usually dot-separated by category (for example: `entity.enderman.stare`). If you want a namespace other than `minecraft`, place `sounds.json` under that namespace folder instead of defining the namespace here.

`replace`: Optional. If set to `true`, the sound list in `sounds` replaces sound definitions for that event from lower-priority resource packs. If set to `false`, it appends to the existing list instead of replacing it. Default is `false`.

`subtitle`: Optional. If "Show Subtitles" is enabled in-game, this string is translated and shown as subtitle text when the sound event plays.

`sounds`: Optional. The list of sounds used by this event. When triggered, the game randomly selects one entry from this list.

`name`: Path from `assets/<namespace>/sounds` to the sound file, or another sound event's namespaced ID.

`volume`: Playback volume. Decimal from `0.0` to `1.0`. Default is `1.0`.

`pitch`: Playback pitch. Default is `1.0`. Can be adjusted higher or lower.

`weight`: Relative weight for this sound when the event triggers. Default is `1`. For example, setting `2` is equivalent to listing this sound twice.

`stream`: If `true`, the sound is streamed. Recommended for long audio to avoid stutter. Most `music` and `record` sounds (except note block sounds) are streamed because they are long. Default is `false`.

`attenuation_distance`: Distance-based attenuation distance for sound volume. Used by things like portals, beacons, and conduits. Default is `16`.

`preload`: If `true`, the sound file is loaded when the resource pack loads, instead of at play time. Used for cases like underwater ambient sounds. Default is `false`.

`type`: Optional, `file` or `event`. `file` means `name` is a file path; `event` means `name` is a sound event ID. Default is `file`.
