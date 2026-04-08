---
authors: ['Gu-meng']
---
# Using `Java.loadClass`
> Gu-meng note: `Java.loadClass` is powerful but can be difficult to work with.

Before using `Java.loadClass`, it helps to have basic Java knowledge: classes, class paths, static members, objects, and access modifiers.

Most of the time, you will not need `loadClass`.

`loadClass` is KubeJS support for loading Java classes.

It expands what KubeJS can do. For example, [registering AE storage components and related parts](../KubeJSProjects/Meng/RegComponent&Storage.md) uses `loadClass` heavily.

## Usage
```js
let class = Java.loadClass("class.path");
```
When using ProbeJS, class paths are usually auto-completed. But not every class is indexed there, so in some cases you need to open the mod's GitHub source, find the class, and copy its full path.

After loading a class with `Java.loadClass`, you can directly call its `public static` methods, fields, and constants.

If what you need is not static, instantiate the class with `new`, just like in Java.

Using the same example:
```js
let newClass = new class();
```
`new class()` may or may not require constructor arguments. Follow the target class source.

## Summary
To use `Java.loadClass` well, you need some Java foundation. This topic cannot be fully covered in one short chapter.
