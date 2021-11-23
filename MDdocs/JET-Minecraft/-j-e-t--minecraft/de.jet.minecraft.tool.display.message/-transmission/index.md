//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.tool.display.message](../index.md)/[Transmission](index.md)

# Transmission

[jvm]\
data class [Transmission](index.md)(prefix: Component, content: TextComponent.Builder, participants: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;CommandSender&gt;, withoutPrefix: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), displayType: [DisplayType](../-display-type/index.md), promptSound: [SoundMelody](../../de.jet.minecraft.tool.effect.sound/-sound-melody/index.md)?, level: [Transmission.Level](-level/index.md), prefixByLevel: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), hoverEvent: HoverEventSource&lt;*&gt;?, clickEvent: ClickEvent?)

## Types

| Name | Summary |
|---|---|
| [Level](-level/index.md) | [jvm]<br>enum [Level](-level/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Transmission.Level](-level/index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [broadcast](broadcast.md) | [jvm]<br>fun [broadcast](broadcast.md)(): [Transmission](index.md) |
| [click](click.md) | [jvm]<br>infix fun [click](click.md)(click: ClickEvent?): [Transmission](index.md) |
| [clickEvent](click-event.md) | [jvm]<br>infix fun [clickEvent](click-event.md)(clickEvent: ClickEvent?): [Transmission](index.md) |
| [content](content.md) | [jvm]<br>infix fun [content](content.md)(content: TextComponent.Builder): [Transmission](index.md) |
| [display](display.md) | [jvm]<br>fun [display](display.md)(): [Transmission](index.md) |
| [displayType](display-type.md) | [jvm]<br>infix fun [displayType](display-type.md)(displayType: [DisplayType](../-display-type/index.md)): [Transmission](index.md) |
| [edit](edit.md) | [jvm]<br>infix fun [edit](edit.md)(action: [Transmission](index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Transmission](index.md) |
| [hover](hover.md) | [jvm]<br>infix fun [hover](hover.md)(hover: HoverEventSource&lt;*&gt;?): [Transmission](index.md) |
| [hoverEvent](hover-event.md) | [jvm]<br>infix fun [hoverEvent](hover-event.md)(hoverEvent: HoverEventSource&lt;*&gt;?): [Transmission](index.md) |
| [participants](participants.md) | [jvm]<br>infix fun [participants](participants.md)(participants: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;CommandSender&gt;): [Transmission](index.md)<br>infix fun [participants](participants.md)(participants: [Collection](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-collection/index.html)&lt;CommandSender&gt;): [Transmission](index.md) |
| [prefix](prefix.md) | [jvm]<br>infix fun [prefix](prefix.md)(prefix: Component): [Transmission](index.md) |
| [promptSound](prompt-sound.md) | [jvm]<br>infix fun [promptSound](prompt-sound.md)(soundMelody: [SoundMelody](../../de.jet.minecraft.tool.effect.sound/-sound-melody/index.md)?): [Transmission](index.md) |
| [toString](to-string.md) | [jvm]<br>open override fun [toString](to-string.md)(): @NotNull[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [withoutPrefix](without-prefix.md) | [jvm]<br>infix fun [withoutPrefix](without-prefix.md)(withoutPrefix: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)): [Transmission](index.md) |

## Properties

| Name | Summary |
|---|---|
| [clickEvent](click-event.md) | [jvm]<br>var [clickEvent](click-event.md): ClickEvent? = null |
| [content](content.md) | [jvm]<br>var [content](content.md): TextComponent.Builder |
| [displayType](display-type.md) | [jvm]<br>var [displayType](display-type.md): [DisplayType](../-display-type/index.md) |
| [hoverEvent](hover-event.md) | [jvm]<br>var [hoverEvent](hover-event.md): HoverEventSource&lt;*&gt;? = null |
| [level](level.md) | [jvm]<br>var [level](level.md): [Transmission.Level](-level/index.md) |
| [participants](participants.md) | [jvm]<br>var [participants](participants.md): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;CommandSender&gt; |
| [prefix](prefix.md) | [jvm]<br>var [prefix](prefix.md): Component |
| [prefixByLevel](prefix-by-level.md) | [jvm]<br>var [prefixByLevel](prefix-by-level.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = true |
| [promptSound](prompt-sound.md) | [jvm]<br>var [promptSound](prompt-sound.md): [SoundMelody](../../de.jet.minecraft.tool.effect.sound/-sound-melody/index.md)? = null |
| [withoutPrefix](without-prefix.md) | [jvm]<br>var [withoutPrefix](without-prefix.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false |
