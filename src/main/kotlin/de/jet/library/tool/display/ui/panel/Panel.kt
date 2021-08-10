package de.jet.library.tool.display.ui.panel

import de.jet.app.JetCache
import de.jet.library.JET
import de.jet.library.extension.display.LIGHT_GRAY
import de.jet.library.structure.app.App
import de.jet.library.tool.display.color.ColorType
import de.jet.library.tool.display.item.Item
import de.jet.library.tool.display.ui.inventory.Container
import de.jet.library.tool.effect.sound.SoundMelody
import de.jet.library.tool.smart.Identifiable
import de.jet.library.tool.smart.Logging
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.*

class Panel(
	label: Component = Component.text("${LIGHT_GRAY}Panel"),
	val lines: Int = 3,
	theme: ColorType = ColorType.GRAY,
	openSound: SoundMelody? = null,
	override var id: String = "${UUID.randomUUID()}",
	override var vendor: Identifiable<App> = JET.appInstance
) : Logging, Container(label = label, size = lines*9, theme = theme, openSound = openSound) {

	override val sectionLabel = "Panel/$id"

	var panelFlags: Set<PanelFlag>
		get() = JetCache.registeredPanelFlags[id] ?: emptySet()
		set(value) {
			JetCache.registeredPanelFlags[id] = value
		}

	val computedInnerSlots: List<Int> by lazy {
		mutableListOf<Int>().apply {
			for (x in 1..(lines - 2)) {
				for (x2 in ((1 + (x*9))..(7 + (x*9))))
					add(x2)
			}
		}
	}

	fun placeInner(slot: Int, item: Item) {
		content[computedInnerSlots[slot]] = item
	}

	fun placeInner(slot: Int, stack: ItemStack) = placeInner(slot = slot, item = Item(stack))

	fun placeInner(slot: Int, material: Material) = placeInner(slot = slot, item = Item(material))

	fun placeInner(rangeSlots: IntRange, item: Item) = rangeSlots.forEach {
		placeInner(it, item)
	}

	fun placeInner(rangeSlots: IntRange, itemStack: ItemStack) = rangeSlots.forEach {
		placeInner(it, itemStack)
	}

	fun placeInner(rangeSlots: IntRange, material: Material) = rangeSlots.forEach {
		placeInner(it, material)
	}

	fun placeInner(arraySlots: Array<Int>, item: Item) = arraySlots.forEach {
		placeInner(it, item)
	}

	fun placeInner(arraySlots: Array<Int>, itemStack: ItemStack) = arraySlots.forEach {
		placeInner(it, itemStack)
	}

	fun placeInner(arraySlots: Array<Int>, material: Material) = arraySlots.forEach {
		placeInner(it, material)
	}

	fun placeInner(arraySlots: Collection<Int>, item: Item) = arraySlots.forEach {
		placeInner(it, item)
	}

	fun placeInner(arraySlots: Collection<Int>, itemStack: ItemStack) = arraySlots.forEach {
		placeInner(it, itemStack)
	}

	fun placeInner(arraySlots: Collection<Int>, material: Material) = arraySlots.forEach {
		placeInner(it, material)
	}

	fun placeInner(listSlots: List<Int>, item: Item) = listSlots.forEach {
		placeInner(it, item)
	}

	fun placeInner(listSlots: List<Int>, material: Material) = listSlots.forEach {
		placeInner(it, material)
	}

	fun placeInner(map: Map<Int, Item>) = map.forEach { (key, value) ->
		placeInner(key, value)
	}

}