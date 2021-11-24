//[JET-Discord](../../index.md)/[de.jet.discord.extension](index.md)/[removeCustomEmoji](remove-custom-emoji.md)

# removeCustomEmoji

[jvm]\
fun Server.[removeCustomEmoji](remove-custom-emoji.md)(condition: (KnownCustomEmoji) -&gt; [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html))

Removes every custom emoji from the server, where the condition is returning true. Every emoji on the server get piped thru a forEach and the condition is applied & checked. If the condition is true, the emoji will be removed, if false the emoji will be skipped.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| condition | the condition, which will be applied to every single customEmoji |

[jvm]\
fun Server.[removeCustomEmoji](remove-custom-emoji.md)(byName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html))

Removes every custom emoji from the server, which has the name [byName](remove-custom-emoji.md).

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| byName | the exact name of the custom emoji, which you want to remove. |

[jvm]\
fun Server.[removeCustomEmoji](remove-custom-emoji.md)(byId: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html))

Removes every custom emoji from the server, which has the id [byId](remove-custom-emoji.md).

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| byId | the id long of the emoji, which you want to remove. |
