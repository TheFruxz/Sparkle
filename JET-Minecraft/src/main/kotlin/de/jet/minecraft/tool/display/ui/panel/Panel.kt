package de.jet.minecraft.tool.display.ui.panel

import de.jet.library.extension.paper.createInventory
import de.jet.library.extension.paper.createKey
import de.jet.library.tool.smart.identification.Identifiable
import de.jet.minecraft.app.JetCache
import de.jet.minecraft.extension.display.BOLD
import de.jet.minecraft.extension.display.YELLOW
import de.jet.minecraft.extension.display.ui.item
import de.jet.minecraft.extension.paper.legacyString
import de.jet.minecraft.extension.system
import de.jet.minecraft.extension.tasky.sync
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.tool.display.color.ColorType
import de.jet.minecraft.tool.display.item.Item
import de.jet.minecraft.tool.display.ui.inventory.Container
import de.jet.minecraft.tool.effect.sound.SoundMelody
import de.jet.minecraft.tool.smart.Logging
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

data class PanelReceiveData(
	var panel: Panel,
	val receiver: Player,
	val receiveParameters: Map<String, Any>,
) {

	fun editPanel(process: Panel.() -> Unit) {
		panel = panel.apply(process)
	}

}

data class Panel(
	override var content: MutableMap<Int, Item> = mutableMapOf(),
	override var label: Component = Component.text("$YELLOW${BOLD}Panel"),
	val lines: Int = 3,
	override var theme: ColorType = ColorType.GRAY,
	override var openSound: SoundMelody? = null,
	override var identity: String = "${UUID.randomUUID()}",
	override var vendor: Identifiable<App> = system,
	var onReceiveEvent: PanelReceiveData.() -> Unit = {  },
	var icon: Item = theme.wool.item.apply {
		lore = """
			
			This panel has no icon, override
			this example by replacing the
			icon variable at your panel.
			   
		""".trimIndent()
	},
	var overridingBorderProtection: Boolean = false,
) : Cloneable, Logging, Container(label = label, size = lines * 9, theme = theme, openSound = openSound) {

	init {
		if (content.isEmpty()) { // do not write a border, if already content is inside
			content = content.apply {
				border(theme.stainedGlassPane.item.blankLabel().apply {
					if (overridingBorderProtection)
						dataPut(system.createKey("panelBorder"), 1)
				})
			}
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

	/**
	 * Available inner slots to set items into
	 */
	val innerSlots by lazy { 0..computedInnerSlots.lastIndex }

	fun onReceive(onReceive: PanelReceiveData.() -> Unit) = apply {
		onReceiveEvent = onReceive
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

	override val rawInventory: Inventory
		get() {
			val inventory = createInventory(null, size, label)

			content.forEach { (key, value) ->
				if (key < inventory.size) {
					inventory.setItem(key, value.produce())
				} else
					System.err.println("Failed to produce item: $value to slot $key because it is higher that the size-content max ${inventory.size}!")
			}

			return inventory
		}

	override fun clone() = copy()

	override fun display(humanEntity: HumanEntity) {
		display(humanEntity, emptyMap())
	}

	override fun display(receiver: Player) =
		display(humanEntity = receiver)

	override fun display(humanEntity: HumanEntity, specificParameters: Map<String, Any>) {
		content = content.apply {
			set(4, icon.apply {
				label = this@Panel.label.legacyString
				dataPut(system.createKey("panelId"), this@Panel.identity, true)
				if (overridingBorderProtection)
					dataPut(system.createKey("panelBorder"), 1)
			})
		}
		sync {
			if (humanEntity is Player) {
				humanEntity.openInventory(
					try {
						copy().content.values.forEach { println(it.material.name) }
						PanelReceiveData(copy(), humanEntity, specificParameters).apply(onReceiveEvent).panel.rawInventory
					} catch (exception: Exception) {
						exception.printStackTrace()
						copy().apply {
							this.fill(Material.RED_STAINED_GLASS.item.apply {
								label = "ERROR"
							})
						}.rawInventory
					}
				)
			} else
				super.display(humanEntity)
		}
	}

	override fun display(receiver: Player, specificParameters: Map<String, Any>) =
		display(humanEntity = receiver, specificParameters)

}