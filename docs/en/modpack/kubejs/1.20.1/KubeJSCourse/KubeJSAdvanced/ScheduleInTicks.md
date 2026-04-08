---
authors: ['Gu-meng']
---
# Scheduled Tasks
In many cases, you want logic to run after 1 second (or n seconds), instead of running immediately.

That is where scheduled tasks are useful.

The scheduler is the `scheduleInTicks` method on the server object.

It is simple to call. If you have a `server` object, use it like this:

```js
Utils.server.scheduleInTicks(20 * 5, () => {
    Utils.server.tell("hello -- scheduled task triggered")
})
```
This sends a server message after 5 seconds.

This example uses `Utils.server` directly. If you can access `server` from an event, use the event-provided value. If not, using `Utils.server` is an alternative.
