//[JET-Minecraft](../../../../index.md)/[de.jet.minecraft.tool.input](../../index.md)/[Keyboard](../index.md)/[KeyboardRequest](index.md)

# KeyboardRequest

[jvm]\
data class [KeyboardRequest](index.md)&lt;[T](index.md) : HumanEntity&gt;(holder: [T](index.md), keyboardType: [Keyboard.Type](../-type/index.md), message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), extensions: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Keyboard.Extension](../-extension/index.md)&gt;, reaction: ([T](index.md), reaction: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))

## Properties

| Name | Summary |
|---|---|
| [extensions](extensions.md) | [jvm]<br>val [extensions](extensions.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Keyboard.Extension](../-extension/index.md)&gt; |
| [holder](holder.md) | [jvm]<br>val [holder](holder.md): [T](index.md) |
| [keyboardType](keyboard-type.md) | [jvm]<br>val [keyboardType](keyboard-type.md): [Keyboard.Type](../-type/index.md) |
| [message](message.md) | [jvm]<br>val [message](message.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [reaction](reaction.md) | [jvm]<br>val [reaction](reaction.md): ([T](index.md), reaction: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
