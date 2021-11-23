//[JET-Minecraft](../../index.md)/[de.jet.minecraft.tool.input](index.md)/[requestKeyboardInput](request-keyboard-input.md)

# requestKeyboardInput

[jvm]\
fun &lt;[T](request-keyboard-input.md) : HumanEntity&gt; [requestKeyboardInput](request-keyboard-input.md)(target: [T](request-keyboard-input.md), keyboardType: [Keyboard.Type](-keyboard/-type/index.md) = ANY, message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = "", requestIdentity: [Identity](../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Keyboard](-keyboard/index.md)&gt; = Identity("${UUID.randomUUID()}"), vararg extensions: [Keyboard.Extension](-keyboard/-extension/index.md) = emptyArray(), onReaction: ([T](request-keyboard-input.md), reaction: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Breakable](../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.type/-breakable/index.md)&lt;[Keyboard](-keyboard/index.md), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;?

#### Return

[Breakable](../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.type/-breakable/index.md)<[Keyboard](-keyboard/index.md),[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)> the keyboard object & the last input, which got entered (empty or unfinished if break is called!)

## Parameters

jvm

| | |
|---|---|
| target | the player, which receives the keyboard UI, also the ability to send some text to this result |
| keyboardType | the possible input type |
| message | the title-content of the keyboard UI |
| extensions | the extensions, that defines the UI and capabilities of the keyboard (***BETA***) |

[jvm]\

@[JvmName](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-name/index.html)(name = "entityRequestKeyboardInput")

fun &lt;[T](request-keyboard-input.md) : HumanEntity&gt; [T](request-keyboard-input.md).[requestKeyboardInput](request-keyboard-input.md)(keyboardType: [Keyboard.Type](-keyboard/-type/index.md) = ANY, message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = "", requestIdentity: [Identity](../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identity/index.md)&lt;[Keyboard](-keyboard/index.md)&gt; = Identity("${UUID.randomUUID()}"), vararg extensions: [Keyboard.Extension](-keyboard/-extension/index.md) = emptyArray(), onReaction: ([T](request-keyboard-input.md), reaction: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Breakable](../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.type/-breakable/index.md)&lt;[Keyboard](-keyboard/index.md), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;?
