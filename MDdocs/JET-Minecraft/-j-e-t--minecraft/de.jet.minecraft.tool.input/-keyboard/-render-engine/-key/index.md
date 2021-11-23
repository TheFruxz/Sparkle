//[JET-Minecraft](../../../../../index.md)/[de.jet.minecraft.tool.input](../../../index.md)/[Keyboard](../../index.md)/[RenderEngine](../index.md)/[Key](index.md)

# Key

[jvm]\
data class [Key](index.md)(identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), textureIdentity: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), displayTitle: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), displayInline: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), write: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Identifiable](../../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[Keyboard.RenderEngine.Key](index.md)&gt;

## Parameters

jvm

| | |
|---|---|
| identity | the unique identity of this key |
| textureIdentity | the skull texture id |
| displayTitle | the display-name (item-name) of this key |
| displayInline | the representative-name of this key (e.g. colored box to describe a color) |
| write | the text written from this key, default the [displayInline](display-inline.md) value, but configurable if this does not match (e.g. the colored box) |

## Constructors

| | |
|---|---|
| [Key](-key.md) | [jvm]<br>fun [Key](-key.md)(identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), textureIdentity: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), displayTitle: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), displayInline: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), write: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = displayInline) |

## Properties

| Name | Summary |
|---|---|
| [displayInline](display-inline.md) | [jvm]<br>val [displayInline](display-inline.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [displayTitle](display-title.md) | [jvm]<br>val [displayTitle](display-title.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identity](identity.md) | [jvm]<br>open override val [identity](identity.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [identityObject](../../../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290) | [jvm]<br>open val [identityObject](../../../../de.jet.minecraft.tool.timing.cooldown/-cooldown/index.md#-527806782%2FProperties%2F-726029290): [Identity](../../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Keyboard.RenderEngine.Key](index.md)&gt; |
| [textureIdentity](texture-identity.md) | [jvm]<br>val [textureIdentity](texture-identity.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [write](write.md) | [jvm]<br>val [write](write.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
