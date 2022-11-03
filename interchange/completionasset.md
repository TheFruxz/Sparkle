---
description: Generate content on demand
---

# 🧫 CompletionAsset

## What is this?

The CompletionAssets allows you, to prived completion and execution content, which is able to adapt to the current situation, user, and input.

This allows you to generate organic content, adapt to changes inside the server, and give the player the possibility, to react to changing situations.

## Immutability

CompletionAssets are deeply immutable, so you can't change the completion asset after the creation, but you can create modified copies of the assets, to 'modify' them.

## Take a look!

The CompletionAssets do have multiple properties, which allows them to create their result, check the input and transform the input to its representing type.

A CompletionAsset always has a generic type T, which represents the object type, which will be represented by the CompletionAsset. For example, the onlinePlayer completion asset provides a list of online player names. The expected object, which should be returned, if the input is getting transformed, is a Player. So the T of the onlinePlayers CompletionAsset is Player

{% hint style="warning" %}
supportedInputType currently may not really affect the computational processing
{% endhint %}

| Property           | Default        | Description                                                                                                 |
| ------------------ | -------------- | ----------------------------------------------------------------------------------------------------------- |
| vendor             | /              | The app, which provides this asset                                                                          |
| thisIdentity       | /              | The identity, of this exact asset                                                                           |
| refreshing         | /              | If the content generator should generate it over and over, at the current situation, instead of caching it. |
| supportedInputType | listOf(STRING) | The input types, which are accepted, as the input                                                           |
| check              | null           | The check, if the input is valid, to be used, as a parameter at this asset                                  |
| transformer        | null           | Transforms the input of the user into the requested object of type T                                        |
| generator          | /              | The process, which generates the collection of completions, aka. the output.                                |

To set these different properties, you can just pass them through the constructor, or you can choose to create a modified copy with these post-edit functions:

| Function    | Description                                                                                |
| ----------- | ------------------------------------------------------------------------------------------ |
| doCheck     | Creates a copy with this `doCheck`, which checks if the user input is valid for this asset |
| transformer | Creates a copy with this `transformer`, which transforms the user input to the T type      |

If you look at an example, you can really quickly learn, how you can use the CompletionAssets, to provide your own content:

{% hint style="info" %}
The CompletionAsset, which is used inside the InterchangeStructure and StructuredInterchange, both supports the decision, if the input is case-sensitive or not. If you do not have to limit this due to a particiular reason, you should accept this and use the provided ignoreCase boolean, to allow this behavior, to match it at your Asset too.
{% endhint %}

```kts
@JvmStatic
val WORLD_NAME = CompletionAsset<World>(system, "WORLD_NAME", true) {
   worlds.withMap { name }
}.doCheck {
   worlds.any { it.name.equals(input, ignoreCase) }
}.transformer {
   Bukkit.getWorld(input)
}
```

## Provided CompletionAssets

Sparkle itself provides some really helpful completion assets, which you can use in your own projects. These are all contained inside the companion object of the CompletionAsset class.

The example, used above, is also located inside this object, you can just use it, with the following example code:

```kotlin
addContent(CompletionAsset.WORLD_NAME)
```

