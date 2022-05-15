package de.moltenKt.paper.tool.display.canvas

import de.moltenKt.core.tool.smart.Producible
import de.moltenKt.paper.runtime.event.canvas.CanvasClickEvent
import de.moltenKt.paper.runtime.event.canvas.CanvasCloseEvent
import de.moltenKt.paper.runtime.event.canvas.CanvasOpenEvent
import de.moltenKt.paper.runtime.event.canvas.CanvasRenderEvent
import de.moltenKt.paper.tool.display.canvas.design.AdaptiveCanvas
import de.moltenKt.paper.tool.display.item.ItemLike
import de.moltenKt.paper.tool.effect.sound.SoundEffect
import net.kyori.adventure.builder.AbstractBuilder
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * This class helps to easily create ui's for players.
 * @param key The key of the canvas, that is used to bind the actions to the canvas.
 * @param label The label, which the viewer will see on top of the inventory.
 * @param canvasSize The size of the canvas.
 * @param content The content, which is placed inside the canvas
 * @param flags The individual habits of the canvas.
 * @param openSoundEffect The sound effect, which is played, when the canvas is opened.
 * @author Fruxz
 * @since 1.0
 */
data class MutableCanvas(
	override val key: Key,
	override var label: TextComponent = Component.empty(),
	override val canvasSize: CanvasSize = CanvasSize.MEDIUM,
	override var content: Map<Int, ItemLike> = emptyMap(),
	override var flags: Set<CanvasFlag> = emptySet(),
	override var openSoundEffect: SoundEffect? = null,
) : Canvas(key, label, canvasSize, content, flags), Producible<Canvas>, AbstractBuilder<Canvas> {

	override var onRender: CanvasRenderEvent.() -> Unit = { }
	override var onOpen: CanvasOpenEvent.() -> Unit = { }
	override var onClose: CanvasCloseEvent.() -> Unit = { }
	override var onClicks: List<CanvasClickEvent.() -> Unit> = emptyList()

	operator fun set(slot: Int, itemLike: ItemLike?) {
		if (itemLike != null) {
			content += slot to itemLike
		} else
			content -= slot
	}

	operator fun set(slots: Iterable<Int>, itemLike: ItemLike?) =
		slots.forEach { set(it, itemLike) }

	operator fun set(vararg slots: Int, itemLike: ItemLike?) =
		set(slots.toList(), itemLike)

	// ItemStack support

	operator fun set(slot: Int, itemStack: ItemStack?) =
		set(slot, itemStack?.let { ItemLike.of(it) })

	operator fun set(slots: Iterable<Int>, itemStack: ItemStack?) =
		set(slots, itemStack?.let { ItemLike.of(it) })

	operator fun set(vararg slots: Int, itemStack: ItemStack?) =
		set(slots.toList(), itemStack?.let { ItemLike.of(it) })

	// Material support

	operator fun set(slot: Int, material: Material?) =
		set(slot, material?.let { ItemLike.of(it) })

	operator fun set(slots: Iterable<Int>, material: Material?) =
		set(slots, material?.let { ItemLike.of(it) })

	operator fun set(vararg slots: Int, material: Material?) =
		set(slots.toList(), material?.let { ItemLike.of(it) })

	// Adaptive support

	operator fun set(slots: Iterable<Int>, adaptiveCanvas: AdaptiveCanvas) =
		adaptiveCanvas.place(this, slots)

	operator fun set(slot: Int, adaptiveCanvas: AdaptiveCanvas) =
		set(listOf(slot), adaptiveCanvas)

	operator fun set(vararg slots: Int, adaptiveCanvas: AdaptiveCanvas) =
		set(slots.toList(), adaptiveCanvas)

	// Interactions

	operator fun set(slot: Int, onClick: CanvasClickEvent.() -> Unit) {
		onClicks = onClicks + {
			if (this.slot == slot) onClick(this)
		}
	}

	operator fun set(slots: Iterable<Int>, onClick: CanvasClickEvent.() -> Unit) =
		slots.forEach { set(it, onClick) }

	operator fun set(vararg slots: Int, onClick: CanvasClickEvent.() -> Unit) =
		set(slots.toList(), onClick)

	operator fun invoke(slot: Int, onClick: CanvasClickEvent.() -> Unit) =
		set(slot, onClick)

	operator fun invoke(slots: Iterable<Int>, onClick: CanvasClickEvent.() -> Unit) =
		set(slots, onClick)

	fun onClick(onClick: (CanvasClickEvent) -> Unit) {
		onClicks = onClicks + onClick
	}

	fun onClickWith(onClick: CanvasClickEvent.() -> Unit) {
		onClicks = onClicks + onClick
	}

	fun onClick(slot: Int, onClick: (CanvasClickEvent) -> Unit) =
		set(slot, onClick)

	fun onClick(slots: Iterable<Int>, onClick: (CanvasClickEvent) -> Unit) =
		slots.forEach { slot -> set(slot, onClick) }

	fun onClickWith(slot: Int, onClick: CanvasClickEvent.() -> Unit) =
		set(slot, onClick)

	fun onClickWith(slots: Iterable<Int>, onClick: CanvasClickEvent.() -> Unit) =
		slots.forEach { slot -> set(slot, onClick) }

	fun onOpen(onOpen: (CanvasOpenEvent) -> Unit) {
		this.onOpen = onOpen
	}

	fun onOpenWith(onOpen: CanvasOpenEvent.() -> Unit) {
		this.onOpen = onOpen
	}

	fun onClose(onClose: (CanvasCloseEvent) -> Unit) {
		this.onClose = onClose
	}

	fun onCloseWith(onClose: CanvasCloseEvent.() -> Unit) {
		this.onClose = onClose
	}

	fun onRender(onRender: (CanvasRenderEvent) -> Unit) {
		this.onRender = onRender
	}

	fun onRenderWith(onRender: CanvasRenderEvent.() -> Unit) {
		this.onRender = onRender
	}

	// Design

	fun border(itemLike: ItemLike) =
		set(canvasSize.borderSlots, itemLike)

	fun border(material: Material) =
		border(ItemLike.of(material))

	fun border(itemStack: ItemStack) =
		border(ItemLike.of(itemStack))

	fun fill(itemLike: ItemLike) =
		set(canvasSize.slots, itemLike)

	fun fill(material: Material) =
		border(ItemLike.of(material))

	fun fill(itemStack: ItemStack) =
		border(ItemLike.of(itemStack))

	fun replace(replaceWith: ItemLike?, search: IndexedValue<ItemLike?>.() -> Boolean) =
		canvasSize.slots.forEach { slot ->
			if (search(IndexedValue(slot, this[slot]))) {
				set(slot, replaceWith)
			}
		}

	fun replace(replaceWith: Material?, search: IndexedValue<ItemLike?>.() -> Boolean) =
		replace(replaceWith?.let { ItemLike.of(it) }, search)

	fun replace(replaceWith: ItemStack?, search: IndexedValue<ItemLike?>.() -> Boolean) =
		replace(replaceWith?.let { ItemLike.of(it) }, search)

	fun background(replaceWith: ItemLike?) =
		replace(replaceWith) { value?.asItemStack()?.type.let { it == null || it.isAir } }

	fun background(replaceWith: Material?) =
		background(replaceWith?.let { ItemLike.of(it) })

	fun background(replaceWith: ItemStack?) =
		background(replaceWith?.let { ItemLike.of(it) })

	// Flags

	fun annexFlags(vararg flags: CanvasFlag) {
		this.flags += flags
	}

	override fun produce(): Canvas = this

	override fun build(): Canvas = this

}

/**
 * This function constructs a new [Canvas], created with the [MutableCanvas] edited
 * inside the given [builder] parameter process.
 * @param key The identity of the [Canvas] to create.
 * @param size The size of the canvas.
 * @param builder The builder function to use to edit the [MutableCanvas].
 * @return The created immutable [Canvas].
 * @author Fruxz
 * @since 1.0
 */
fun buildCanvas(key: Key, size: CanvasSize = CanvasSize.MEDIUM, builder: MutableCanvas.() -> Unit): Canvas =
	MutableCanvas(key, canvasSize = size).apply(builder).build()
