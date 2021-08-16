package de.jet.library.tool.display.ui.panel

import de.jet.app.JetCache
import de.jet.library.extension.display.BOLD
import de.jet.library.extension.display.YELLOW
import de.jet.library.extension.display.ui.item
import de.jet.library.extension.system
import de.jet.library.extension.paper.createKey
import de.jet.library.extension.paper.legacyString
import de.jet.library.structure.app.App
import de.jet.library.tool.display.color.ColorType
import de.jet.library.tool.display.item.Item
import de.jet.library.tool.display.ui.inventory.Container
import de.jet.library.tool.effect.sound.SoundMelody
import de.jet.library.tool.smart.Identifiable
import de.jet.library.tool.smart.Logging
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

class Panel(
	label: Component = Component.text("${YELLOW}${BOLD}Panel"),
	val lines: Int = 3,
	theme: ColorType = ColorType.GRAY,
	openSound: SoundMelody? = null,
	override var identity: String = "${UUID.randomUUID()}",
	override var vendor: Identifiable<App> = system,
	var playerSpecificUI: Panel.(player: Player) -> Panel = { this },
	var icon: Item = theme.wool.item.apply {
		lore = """
			
			This panel has no icon, override
			this example by replacing the
			icon variable at your panel.
			   
		""".trimIndent()
	},
) : Logging, Container(label = label, size = lines * 9, theme = theme, openSound = openSound) {

	init {
		content = content.apply {
			border(theme.stainedGlassPane.item.blankLabel())
		}
	}

	override val sectionLabel = "Panel/$identity"

	var panelFlags: Set<PanelFlag>
		get() = JetCache.registeredPanelFlags[identity] ?: emptySet()
		set(value) {
			JetCache.registeredPanelFlags[identity] = value
		}

	private val computedInnerSlots: List<Int> by lazy {
		mutableListOf<Int>().apply {
			for (x in 1..(lines - 2)) {
				for (x2 in ((1 + (x * 9))..(7 + (x * 9))))
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

	override fun display(humanEntity: HumanEntity) {
		content = content.apply {
			set(4, icon.apply {
				label = this@Panel.label.legacyString
				dataPut(system.createKey("panelId"), this@Panel.identity, true)
			})
		}
		if (humanEntity is Player) {
			humanEntity.openInventory(playerSpecificUI(this, humanEntity).rawInventory)
		} else
			super.display(humanEntity)
	}

	override fun display(receiver: Player) =
		display(humanEntity = receiver)

}