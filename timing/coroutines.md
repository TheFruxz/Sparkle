---
description: Deep dive into the technology implementation
---

# 🛣 Coroutines

{% hint style="danger" %}
**WARNING!** Due to the fact, that Paper is currently hardly limiting the number of Tasks, which you can use, the Coroutines based on Scheduler Tasks can be quite dangerous if their usage of them is getting out of hand!
{% endhint %}

## Introduction

Sparkle is engineered to work with the Kotlin coroutines. This gives us the possibility, to shift the thinking from sync to async. Managing tasks and suspended code was never easier to do and never as reliable as it is with the Coroutines.

But how it works? If you want to know more about it from a front perspective (how to use, etc.) then take a look at the [Scheduling & Deferral](scheduling-and-deferral.md) page, this is more like a deep-dive into the technological system.

## Open system

Because Sparkle wants to help, to create plugins, which fit your needs, we don't want to provide a closed system on purpose.

With Sparkle, you can specify your own coroutine contexts and systems in many situations if you want to choose a different system instead.

Just take a look at the constructors, overridable properties/functions, and optional parameters, to see, if you can change the underlying coroutines setup.&#x20;

If you have a suggestion, where we can open up some more API and underlying systems, to help make the whole thing more configurable to your needs, just create an Issue on the GitHub repository and we will take a look at it, how/if we can implement the requested change!

{% hint style="warning" %}
**What?** For the following topics, you may need some experience or background knowledge with Kotlin coroutines!
{% endhint %}

## The Tree-System

The coroutine Setup of Sparkle works like a Tree, the roots are the Sparkle setup and the other plugins are like branches. The components of the Apps are like the branches of the branches. If the app dies, the whole tree does not die, but if Sparkle itself dies, everything else dies.

How we did do that without linking everything together?

We have a CoroutineScope, working with a SupervisorJob, which also helps to be resistant to partial crashes. This also allows us to work with this Coroutine Scope, without having to worry about destroying something else, by doing bad things with our partial area.

Each CoroutineScope is defined by the Companion of each plugin, which is elevated through every layer of Service, Component, and App.

## Running

Each app depends on its own branch of coroutineScope, so, many things of Sparkle are coroutine based and indirect consequences asynchronously processed. This is something you have to keep in mind, because it is not in the nature of Bukkit, to process commands, plugin startups, and events asynchronously by default.



