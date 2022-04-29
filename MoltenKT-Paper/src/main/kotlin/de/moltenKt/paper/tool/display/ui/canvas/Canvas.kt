package de.moltenKt.paper.tool.display.ui.canvas

import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.paper.extension.display.ui.buildInventory
import de.moltenKt.paper.extension.display.ui.set
import de.moltenKt.paper.extension.mainLog
import de.moltenKt.paper.runtime.event.PanelCloseEvent
import de.moltenKt.paper.runtime.event.PanelOpenEvent
import de.moltenKt.paper.tool.display.color.ColorType
import de.moltenKt.paper.tool.display.item.ItemLike
import de.moltenKt.paper.tool.display.ui.panel.PanelFlag
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.TextComponent
import org.bukkit.entity.HumanEntity
import org.bukkit.inventory.Inventory

open class Canvas(
	val key: Key,
	val label: TextComponent,
	val canvasSize: CanvasSize,
	val content: Map<Int, ItemLike>,
	val panelFlags: Set<PanelFlag>,
	val colorTheme: ColorType,

	val onReceive: CanvasViewContext.() -> Unit,
	val onOpen: PanelOpenEvent.() -> Unit,
	val onClose: PanelCloseEvent.() -> Unit,
) : Identifiable<Canvas> {

	override val identity: String
		get() = key.asString()

	private val computedInnerSlots: List<Int> by lazy {
		mutableListOf<Int>().apply {
			for (x in 1..(canvasSize.lines - 2)) {
				for (x2 in ((1 + (x * 9))..(7 + (x * 9))))
					add(x2)
			}
		}
	}

	val innerSlots: List<Int> by lazy {
		computedInnerSlots
	}

	val availableInnerSlots by lazy {
		0..computedInnerSlots.lastIndex
	}

	val rawInventory: Inventory
		get() = buildInventory(canvasSize.size, label) {

			content.forEach { (slot, itemLike) ->
				if (slot < size) {

					this[slot] = itemLike.asItemStack()

				} else mainLog.warning("Failed to produce item: ${itemLike.asItem()} to slot $slot because it is higher than the size-content max ${size}!")
			}

		}

	fun display(vararg receiver: HumanEntity): Unit = display(receiver = receiver, data = emptyMap())

	fun display(vararg receiver: HumanEntity, data: Map<Key, Any>) {

	}

}