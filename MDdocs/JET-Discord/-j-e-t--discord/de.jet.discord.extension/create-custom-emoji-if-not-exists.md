//[JET-Discord](../../index.md)/[de.jet.discord.extension](index.md)/[createCustomEmojiIfNotExists](create-custom-emoji-if-not-exists.md)

# createCustomEmojiIfNotExists

[jvm]\
fun Server.[createCustomEmojiIfNotExists](create-custom-emoji-if-not-exists.md)(emojiName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), resource: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html), process: CustomEmojiBuilder.() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = {}): KnownCustomEmoji

Creates a custom emoji using the CustomEmojiBuilder and returns the created emoji. If a emoji with the name [emojiName](create-custom-emoji-if-not-exists.md) already exists, the existing emoji will be returned and no new emoji will be created and stored on the server.

#### Return

the created or already existing emoji

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| emojiName | the (new) name of the emoji |
| resource | the image file of the emoji (png) as a byte array |
| process | a custom function which can be used to modify the CustomEmojiBuilder after all parameters set into it. |
