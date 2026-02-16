---
progress: 100
state: preliminary
title: Java Class
hidden: false
priority: 0
---

# Java

::: tip Note
This entry documents the global Java object provided by KubeJS.
:::

## Object Structure

```ts
class JavaWrapper {
  loadClass(className: string): Internal.any;
  tryLoadClass(className: string): any;
}
```

::: center
Functions and properties that are not used are omitted here
:::

## Method Description

### loadClass

- loadClass(className: string): Internal.any;
- Uses the provided fully qualified class name string (can be understood as a path, but not a path in the system files) to obtain the class object through reflection. Throws an error if the class does not exist.
- Example: Reflect the Entity class.

```js [KubeJS]
const Entity = Java.loadClass('net.minecraft.world.entity.Entity');
```

::: center
This allows you to use this class like a normal JavaScript object
:::

### tryLoadClass

- tryLoadClass(className: string): any;
- Uses the provided fully qualified class name string to obtain the class object through reflection. Returns null if the class does not exist, without throwing an error.

## Notes

::: warning Note
Classes cannot be inherited (extends) in KubeJS, and interfaces cannot be implemented (implements).
In this example, Entity is an abstract class and we cannot inherit from it to create subclasses.
:::

<!--
<llm-only>
This page documents the Java global object in KubeJS for accessing Minecraft/Java classes through reflection.

Key points for LLM understanding:
1. Java.loadClass('fully.qualified.ClassName') - loads class by name, throws if not found
2. Java.tryLoadClass('fully.qualified.ClassName') - loads class, returns null if not found
3. Used to access Minecraft classes like net.minecraft.world.entity.Entity
4. Cannot extend (inherit) Java classes in KubeJS - this is a key limitation
5. Once loaded, classes can be used like regular JavaScript objects

Common usage:
- const Entity = Java.loadClass('net.minecraft.world.entity.Entity');
- const ItemStack = Java.loadClass('net.minecraft.world.item.ItemStack');
</llm-only>
-->
