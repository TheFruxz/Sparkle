//[JET-Discord](../../index.md)/[de.jet.discord.extension](index.md)/[createCustomEmoji](create-custom-emoji.md)

# createCustomEmoji

[jvm]\
fun Server.[createCustomEmoji](create-custom-emoji.md)(emojiName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), resource: [ByteArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html), replaceExisting: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, process: CustomEmojiBuilder.() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = {}): KnownCustomEmoji?

Creates a custom emoji using the CustomEmojiBuilder and returns the created emoji. If an emoji with the name [emojiName](create-custom-emoji.md) already exists, the property [replaceExisting](create-custom-emoji.md) will decide, if the new one will be created besides the existing one, or if the existing one will be immediatly be returned without the creation of a new emoji.

#### Return

the newly created one or the already existing one if [replaceExisting](create-custom-emoji.md) is false

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| emojiName | the (new) name of the emoji. |
| resource | the image file of the emoji (png) as a byte array |
| replaceExisting | if true it will create a new emoji named with [emojiName](create-custom-emoji.md) + some other stuff or if false it returns the already existing one |
| process | a custom function which can be used to modify the CustomEmojiBuilder after all parameters set into it. |
