//[JET-Minecraft](../../../../index.md)/[de.jet.minecraft.tool.input](../../index.md)/[Keyboard](../index.md)/[RenderEngine](index.md)

# RenderEngine

[jvm]\
object [RenderEngine](index.md)

## Types

| Name | Summary |
|---|---|
| [Key](-key/index.md) | [jvm]<br>data class [Key](-key/index.md)(identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), textureIdentity: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), displayTitle: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), displayInline: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), write: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Identifiable](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[Keyboard.RenderEngine.Key](-key/index.md)&gt; |
| [KeyConfiguration](-key-configuration/index.md) | [jvm]<br>data class [KeyConfiguration](-key-configuration/index.md)(darkModeKeys: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Keyboard.RenderEngine.Key](-key/index.md)&gt;, lightModeKeys: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Keyboard.RenderEngine.Key](-key/index.md)&gt;) |
| [RenderingState](-rendering-state/index.md) | [jvm]<br>data class [RenderingState](-rendering-state/index.md)(mainKeyboard: [Panel](../../../de.jet.minecraft.tool.display.ui.panel/-panel/index.md), symbolKeyboard: [Panel](../../../de.jet.minecraft.tool.display.ui.panel/-panel/index.md), numberKeyboard: [Panel](../../../de.jet.minecraft.tool.display.ui.panel/-panel/index.md), extensionKeyboard: [Panel](../../../de.jet.minecraft.tool.display.ui.panel/-panel/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [renderKey](render-key.md) | [jvm]<br>fun [renderKey](render-key.md)(keyObject: [Keyboard.RenderEngine.Key](-key/index.md)): [Item](../../../de.jet.minecraft.tool.display.item/-item/index.md)<br>fun [renderKey](render-key.md)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Item](../../../de.jet.minecraft.tool.display.item/-item/index.md) |
| [renderKeyboard](render-keyboard.md) | [jvm]<br>fun [renderKeyboard](render-keyboard.md)(holder: HumanEntity, keyboardType: [Keyboard.Type](../-type/index.md) = ANY, message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = "", vararg extensions: [Keyboard.Extension](../-extension/index.md) = emptyArray()): [Keyboard.RenderEngine.RenderingState](-rendering-state/index.md) |
