package de.moltenKt.paper.tool.display.ui.canvas

import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.display.ui.buildInventory
import de.moltenKt.paper.extension.display.ui.set
import de.moltenKt.paper.extension.effect.playSoundEffect
import de.moltenKt.paper.extension.mainLog
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.extension.tasky.asSync
import de.moltenKt.paper.extension.tasky.sync
import de.moltenKt.paper.runtime.event.canvas.CanvasClickEvent
import de.moltenKt.paper.runtime.event.canvas.CanvasCloseEvent
import de.moltenKt.paper.runtime.event.canvas.CanvasOpenEvent
import de.moltenKt.paper.runtime.event.canvas.CanvasRenderEvent
import de.moltenKt.paper.tool.display.item.ItemLike
import de.moltenKt.paper.tool.display.ui.canvas.CanvasFlag.NO_OPEN
import de.moltenKt.paper.tool.effect.sound.SoundEffect
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * This class helps to easily create ui's for players.
 * @param key The key of the canvas, that is used to bind the actions to the canvas.
 * This should be unique for each canvas, but if you want to share a canvas habits & actions, use the same.
 * Using shared keys can also improve memory-usage, if you do it cleverly. Why? because each registered action
 * and session is identified by the key!
 * @param label The label, which the viewer will see on top of the inventory.
 * @param canvasSize The size of the canvas.
 * @param content The content, which is placed inside the canvas
 * @param flags The individual habits of the canvas.
 * @param openSoundEffect The sound effect, which is played, when the canvas is opened.
 * ***NOTE: If you want to edit a canvas, use the [MutableCanvas] class, maybe the [toMutable] function helps!***
 * @author Fruxz
 * @since 1.0
 */
open class Canvas(
	open val key: Key,
	open val label: TextComponent = Component.empty(),
	open val canvasSize: CanvasSize = CanvasSize.MEDIUM,
	open val content: Map<Int, ItemLike> = emptyMap(),
	open val flags: Set<CanvasFlag> = emptySet(),
	open val openSoundEffect: SoundEffect? = null,
) : Identifiable<Canvas> {

	override val identity: String
		get() = key.asString()

	open val onRender: CanvasRenderEvent.() -> Unit = { }
	open val onOpen: CanvasOpenEvent.() -> Unit = { }
	open val onClose: CanvasCloseEvent.() -> Unit = { }
	open val onClicks: List<CanvasClickEvent.() -> Unit> = emptyList()

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
		triggerOpenEvent: Boolean = true,
		triggerSound: Boolean = true
	) = system.coroutineScope.launch {
		if (flags.contains(NO_OPEN)) cancel()

		val inventoryContent = async { asSync { (0 until canvasSize.size).map { slot -> content[slot]?.asItemStack() }.toTypedArray() } }

		push()

		receivers.distinctBy { it.uniqueId }.forEach { receiver ->
			if (triggerSound) openSoundEffect?.let { receiver.playSoundEffect(it) }
			var localInstance = buildInventory(canvasSize.size, label) {
				this.contents = runBlocking { inventoryContent.await() }
			}

			if (receiver is Player) {

				CanvasRenderEvent(receiver, this@Canvas, localInstance, false).let { event ->
					event.callEvent()
					localInstance = event.renderResult
				}

				if (triggerOpenEvent) CanvasOpenEvent(receiver, this@Canvas, localInstance, data).let { event ->
					if (event.callEvent()) {
						localInstance = event.inventory

						sync {
							receiver.openInventory(localInstance)
							CanvasSessionManager.putSession(receiver, key, data)
						}

						openSoundEffect?.play(receiver)

						onOpen(event)

					} else {
						return@forEach
					}
				}

			}

		}

	}

	fun push() {
		MoltenCache.canvas += key to this
		MoltenCache.canvasActions += key to Reaction(onOpen, onClose, onClicks)
	}

	fun toMutable(
		key: Key = this.key,
		label: TextComponent = this.label,
		canvasSize: CanvasSize = this.canvasSize,
		content: Map<Int, ItemLike> = this.content,
		panelFlags: Set<CanvasFlag> = this.flags,
		openSoundEffect: SoundEffect? = this.openSoundEffect,
		onRender: CanvasRenderEvent.() -> Unit = this.onRender,
		onOpen: CanvasOpenEvent.() -> Unit = this.onOpen,
		onClose: CanvasCloseEvent.() -> Unit = this.onClose,
		onClicks: List<CanvasClickEvent.() -> Unit> = this.onClicks,
	): MutableCanvas = MutableCanvas(key, label, canvasSize, content, panelFlags, openSoundEffect).apply {
		this.onRender = onRender
		this.onOpen = onOpen
		this.onClose = onClose
		this.onClicks = onClicks
	}

	data class Reaction(
		val onOpen: CanvasOpenEvent.() -> Unit = { },
		val onClose: CanvasCloseEvent.() -> Unit = { },
		val onClicks: List<CanvasClickEvent.() -> Unit> = emptyList(),
	)

}