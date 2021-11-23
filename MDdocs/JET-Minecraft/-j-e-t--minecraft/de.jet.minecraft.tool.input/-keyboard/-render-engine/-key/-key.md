//[JET-Minecraft](../../../../../index.md)/[de.jet.minecraft.tool.input](../../../index.md)/[Keyboard](../../index.md)/[RenderEngine](../index.md)/[Key](index.md)/[Key](-key.md)

# Key

[jvm]\
fun [Key](-key.md)(identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), textureIdentity: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), displayTitle: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), displayInline: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), write: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = displayInline)

## Parameters

jvm

| | |
|---|---|
| identity | the unique identity of this key |
| textureIdentity | the skull texture id |
| displayTitle | the display-name (item-name) of this key |
| displayInline | the representative-name of this key (e.g. colored box to describe a color) |
| write | the text written from this key, default the [displayInline](../../../../../../JET-Minecraft/de.jet.minecraft.tool.input/-keyboard/-render-engine/-key/[60]init[62].md) value, but configurable if this does not match (e.g. the colored box) |
