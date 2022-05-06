package de.moltenKt.paper.tool.display.ui.canvas

import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.display.ui.buildInventory
import de.moltenKt.paper.extension.display.ui.set
import de.moltenKt.paper.extension.effect.playSoundEffect
import de.moltenKt.paper.extension.mainLog
import de.moltenKt.paper.extension.tasky.sync
import de.moltenKt.paper.runtime.event.canvas.CanvasClickEvent
import de.moltenKt.paper.runtime.event.canvas.CanvasCloseEvent
import de.moltenKt.paper.runtime.event.canvas.CanvasOpenEvent
import de.moltenKt.paper.runtime.event.canvas.CanvasRenderEvent
import de.moltenKt.paper.tool.display.item.ItemLike
import de.moltenKt.paper.tool.display.ui.panel.PanelFlag
import de.moltenKt.paper.tool.effect.sound.SoundEffect
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
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

	open val onRender: CanvasRenderEvent.() -> Unit = { }
	open val onOpen: CanvasOpenEvent.() -> Unit = { }
	open val onClose: CanvasCloseEvent.() -> Unit = { }
	open val onClicks: Map<Int, CanvasClickEvent.() -> Unit> = emptyMap()

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

	fun display(
		vararg receivers: HumanEntity,
		data: Map<Key, Any> = emptyMap(),
		triggerEvents: Boolean = true,
		triggerSound: Boolean = true
	) {

		val inventoryContent = (0 until canvasSize.size).map { slot -> content[slot]?.asItemStack() }.toTypedArray()

		push()

		receivers.forEach { receiver ->
			if (triggerSound) openSoundEffect?.let { receiver.playSoundEffect(it) }

			CanvasSessionManager.putSession(receiver, key, data)

			var localInstance = buildInventory(canvasSize.size, label) {

				this.contents = inventoryContent

			}

			if (triggerEvents && receiver is Player) CanvasOpenEvent(receiver, this, localInstance, data).let { event ->
				if (event.callEvent()) {
					localInstance = event.inventory
				} else
					return@forEach
			}

			sync { receiver.openInventory(localInstance) }

		}

	}

	fun refresh(triggerEvents: Boolean = false) {
		// DO NOT DISPLAY AGAIN (change open inventory contents of every player inside this session)
	}

	fun push() {
		MoltenCache.canvasActions += key to Reaction(onOpen, onClose, onClicks)
	}

	fun toMutable(
		key: Key = this.key,
		label: TextComponent = this.label,
		canvasSize: CanvasSize = this.canvasSize,
		content: Map<Int, ItemLike> = this.content,
		panelFlags: Set<PanelFlag> = this.panelFlags,
		openSoundEffect: SoundEffect? = this.openSoundEffect,
		onRender: CanvasRenderEvent.() -> Unit = this.onRender,
		onOpen: CanvasOpenEvent.() -> Unit = this.onOpen,
		onClose: CanvasCloseEvent.() -> Unit = this.onClose,
		onClicks: Map<Int, CanvasClickEvent.() -> Unit> = this.onClicks,
	): MutableCanvas = MutableCanvas(key, label, canvasSize, content, panelFlags, openSoundEffect).apply {
		this.onRender = onRender
		this.onOpen = onOpen
		this.onClose = onClose
		this.onClicks = onClicks
	}

	data class Reaction(
		val onOpen: CanvasOpenEvent.() -> Unit = { },
		val onClose: CanvasCloseEvent.() -> Unit = { },
		val onClicks: Map<Int, CanvasClickEvent.() -> Unit> = emptyMap(),
	)

}