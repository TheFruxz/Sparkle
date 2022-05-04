package de.moltenKt.paper.tool.display.ui.canvas

import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.display.ui.buildInventory
import de.moltenKt.paper.extension.display.ui.set
import de.moltenKt.paper.extension.effect.playSoundEffect
import de.moltenKt.paper.extension.mainLog
import de.moltenKt.paper.runtime.event.PanelClickEvent
import de.moltenKt.paper.runtime.event.PanelCloseEvent
import de.moltenKt.paper.runtime.event.PanelOpenEvent
import de.moltenKt.paper.tool.display.item.ItemLike
import de.moltenKt.paper.tool.display.ui.panel.PanelFlag
import de.moltenKt.paper.tool.effect.sound.SoundEffect
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.entity.HumanEntity
import org.bukkit.inventory.Inventory

open class Canvas(
	open val key: Key,
	open val label: TextComponent = Component.empty(),
	open val canvasSize: CanvasSize = CanvasSize.MEDIUM,
	open val content: Map<Int, ItemLike> = emptyMap(),
	open val panelFlags: Set<PanelFlag> = emptySet(),
	open val openSoundEffect: SoundEffect? = null,
) : Identifiable<Canvas> {

	override val identity: String
		get() = key.asString()

	open val onOpen: PanelOpenEvent.() -> Unit = { }
	open val onClose: PanelCloseEvent.() -> Unit = { }
	open val onClicks: Map<Int, PanelClickEvent.() -> Unit> = emptyMap()

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

	operator fun get(slot: Int): ItemLike? {
		return content[slot]
	}

	operator fun get(slots: Iterable<Int>): List<ItemLike?> =
		slots.map(this::get)

	operator fun get(vararg slots: Int) =
		get(slots.toList())

	fun display(vararg receivers: HumanEntity, triggerEvents: Boolean = true): Unit = display(receivers = receivers, data = emptyMap())

	fun display(vararg receivers: HumanEntity, data: Map<Key, Any>, triggerEvents: Boolean = true, triggerSound: Boolean = true): Unit = receivers.forEach { receiver ->
		if (triggerSound) openSoundEffect?.let { receiver.playSoundEffect(it) }

		CanvasSessionManager.putSession(receiver, key, data)

	}

	fun refresh(triggerEvents: Boolean = false) {
		// DO NOT DISPLAY AGAIN (change open inventory contents of every player inside this session)
	}

	fun push() {
		MoltenCache.canvasActions += key to Reaction(onOpen, onClose, onClicks)
	}

	data class Reaction(
		val onOpen: PanelOpenEvent.() -> Unit = { },
		val onClose: PanelCloseEvent.() -> Unit = { },
		val onClicks: Map<Int, PanelClickEvent.() -> Unit> = emptyMap(),
	)

}