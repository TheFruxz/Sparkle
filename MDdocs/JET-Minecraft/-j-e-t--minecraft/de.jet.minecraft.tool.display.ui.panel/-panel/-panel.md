//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.tool.display.ui.panel](../index.md)/[Panel](index.md)/[Panel](-panel.md)

# Panel

[jvm]\
fun [Panel](-panel.md)(content: [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)&lt;[Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), [Item](../../de.jet.minecraft.tool.display.item/-item/index.md)&gt; = mutableMapOf(), label: Component = Component.text("$YELLOW${BOLD}Panel"), lines: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 3, theme: [ColorType](../../de.jet.minecraft.tool.display.color/-color-type/index.md) = ColorType.GRAY, openSound: [SoundMelody](../../de.jet.minecraft.tool.effect.sound/-sound-melody/index.md)? = null, identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = "", vendor: [Identifiable](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[App](../../de.jet.minecraft.structure.app/-app/index.md)&gt; = system, onReceiveEvent: [PanelReceiveData](../-panel-receive-data/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = { }, icon: [Item](../../de.jet.minecraft.tool.display.item/-item/index.md) = theme.wool.item.apply {
		lore = """
			
			This panel has no icon, override
			this example by replacing the
			icon variable at your panel.
			   
		""".trimIndent()
	}, overridingBorderProtection: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, singleViewLimitation: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false)
