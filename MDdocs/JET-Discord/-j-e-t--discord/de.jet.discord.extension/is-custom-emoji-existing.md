//[JET-Discord](../../index.md)/[de.jet.discord.extension](index.md)/[isCustomEmojiExisting](is-custom-emoji-existing.md)

# isCustomEmojiExisting

[jvm]\
fun Server.[isCustomEmojiExisting](is-custom-emoji-existing.md)(emojiName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ignoreCase: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Returns if an emoji with the [emojiName](is-custom-emoji-existing.md) exists in the [server](server.md).

#### Return

true if the emoji exists, false otherwise.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| emojiName | The name of the emoji. |
| ignoreCase | defines if the search is non-case-sensitive. |

[jvm]\
fun Server.[isCustomEmojiExisting](is-custom-emoji-existing.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)

Returns if a emoji with the [id](is-custom-emoji-existing.md) exists / stored at the server.

#### Return

true if the emoji exists, false otherwise.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| id | the id of the custom emoji |
