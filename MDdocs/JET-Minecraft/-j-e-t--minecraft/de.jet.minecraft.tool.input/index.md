//[JET-Minecraft](../../index.md)/[de.jet.minecraft.tool.input](index.md)

# Package de.jet.minecraft.tool.input

## Types

| Name | Summary |
|---|---|
| [Keyboard](-keyboard/index.md) | [jvm]<br>object [Keyboard](-keyboard/index.md) |

## Functions

| Name | Summary |
|---|---|
| [requestKeyboardInput](request-keyboard-input.md) | [jvm]<br>@[JvmName](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-name/index.html)(name = "entityRequestKeyboardInput")<br>fun &lt;[T](request-keyboard-input.md) : HumanEntity&gt; [T](request-keyboard-input.md).[requestKeyboardInput](request-keyboard-input.md)(keyboardType: [Keyboard.Type](-keyboard/-type/index.md) = ANY, message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = "", requestIdentity: [Identity](../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Keyboard](-keyboard/index.md)&gt; = Identity("${UUID.randomUUID()}"), vararg extensions: [Keyboard.Extension](-keyboard/-extension/index.md) = emptyArray(), onReaction: ([T](request-keyboard-input.md), reaction: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Breakable](../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.type/-breakable/index.md)&lt;[Keyboard](-keyboard/index.md), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;?<br>fun &lt;[T](request-keyboard-input.md) : HumanEntity&gt; [requestKeyboardInput](request-keyboard-input.md)(target: [T](request-keyboard-input.md), keyboardType: [Keyboard.Type](-keyboard/-type/index.md) = ANY, message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = "", requestIdentity: [Identity](../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Keyboard](-keyboard/index.md)&gt; = Identity("${UUID.randomUUID()}"), vararg extensions: [Keyboard.Extension](-keyboard/-extension/index.md) = emptyArray(), onReaction: ([T](request-keyboard-input.md), reaction: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Breakable](../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.type/-breakable/index.md)&lt;[Keyboard](-keyboard/index.md), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;? |
