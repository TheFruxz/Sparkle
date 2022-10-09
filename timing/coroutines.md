---
description: Deep dive into the technology implementation
---

# 🛣 Coroutines

{% hint style="danger" %}
**WARNING!** Due to the fact, that Paper is currently hardly limiting the number of Tasks, which you can use, the Coroutines based on Scheduler Tasks can be quite dangerous if their usage of them gets crazy!
{% endhint %}

Sparkle is engineered to work with the Kotlin coroutines. This gives us the possibility, to shift the thinking from sync to async. Managing tasks and suspended code was never easier to do and never as reliable as it is with the Coroutines.

But how it works? If you want to know more about it from a front perspective (how to use, etc.) then take a look at the [Scheduling & Deferral](scheduling-and-deferral.md) page, this is more like a deep-dive into the technological system.

