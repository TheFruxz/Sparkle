---
description: Front view onto the use of Coroutines
---

# ⏳ Scheduling & Deferral

{% hint style="info" %}
**More interested in the technological concept?** Then you may take a look at the [Coroutines](../apps/the-apps/coroutines.md) page, where exactly that is discussed!
{% endhint %}

## Introduction

If you want to develop your plugins with Sparkle, you should say bye-bye to your legacy Bukkit scheduler, it's finally time to move on!

Our system is built with Coroutines in mind, if you want to know more about exactly that, visit the [Coroutines](../apps/the-apps/coroutines.md) page right now!

Not only because most of our systems are asynchronously built, but you may want to simply use sync & async code in your project, you can use our enhanced sync & async APIs to make your life easier.

We go through some of our functions, which are helping to handle sync & async:

## `asSync` - suspend function

The asSync suspend function helps to execute and return code in a synchronous environment and wait for the output.

Because this can take up quite some time, this is a suspend function, which should be used, if you are dependent on the result **right now**!

You can also move the execution of the code further into the future, by increasing the optional delay parameter, which is Duration. By default, the duration is set to Duration.ZERO. If this parameter is positive, the duration time is used, to delay the computation of the process result.

## `asSyncDeferred` - function

The asSyncDeferred function is using the [`asSync`](scheduling-and-deferral.md#assync-suspend-function) function, but wrapped the whole thing into an async block of the vendor's coroutineScope, so the code is not blocking anymore and is returning a Deferred of the type of the result.

Instead of [`asSync`](scheduling-and-deferral.md#assync-suspend-function), this can be used in your code, if you are not dependent on the result **right now**!

## `doSync` - function

The doSync function is like the asSyncDeferred function, by executing the process without blocking the current code, but it is not returning a result.

This function is used to only execute the code contained inside the process, without having to rely on and fill out a result. The use case is you want to execute something synchronously, without having to return something. But you can still execute a `.join()` function on the returning `Job` block, to await the completion of the contained code block. This might be the case if you have something to update synchronously and then get the updated code asynchronously.

This function can also delay the computation of the process block, but not only that. With the Bukkit scheduler, you can specify, that you want to repeat the code in repetitions with specific time intervals. With the `doSync` function, you can do this too.

To repeat your code, you can not only define the optional delay parameter, but also an optional interval parameter. Like the delay parameter, the interval parameter gets intact with being positive.  This means, if the interval gets positive, the code repeats itself until it is getting canceled.

## `asAsync` - function





