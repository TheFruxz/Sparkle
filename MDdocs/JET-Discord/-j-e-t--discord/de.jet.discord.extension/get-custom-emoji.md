//[JET-Discord](../../index.md)/[de.jet.discord.extension](index.md)/[getCustomEmoji](get-custom-emoji.md)

# getCustomEmoji

[jvm]\
fun Server.[getCustomEmoji](get-custom-emoji.md)(emojiName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ignoreCase: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false): KnownCustomEmoji?

Returns the KnownCustomEmoji with the given name or null if it does not exist. The [ignoreCase](get-custom-emoji.md) makes the search ignoreCase or if false it is case-sensitive.

#### Return

The KnownCustomEmoji with the given name or null if it does not exist.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| emojiName | The name of the emoji. |
| ignoreCase | if the search is non-case-sensitive. |

[jvm]\
fun Server.[getCustomEmoji](get-custom-emoji.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): KnownCustomEmoji?

Returns the KnownCustomEmoji with the given [id](get-custom-emoji.md) or nul if no emoji with the given [id](get-custom-emoji.md) exists.

#### Return

the emoji or null if not exists

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| id | the id of the emoji stored on the server |
