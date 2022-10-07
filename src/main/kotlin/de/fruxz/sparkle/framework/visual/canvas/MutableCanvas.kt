package de.fruxz.sparkle.framework.visual.canvas

import de.fruxz.ascend.extension.data.RandomTagType.MIXED_CASE
import de.fruxz.ascend.extension.data.buildRandomTag
import de.fruxz.ascend.tool.smart.Producible
import de.fruxz.sparkle.framework.event.canvas.CanvasClickEvent
import de.fruxz.sparkle.framework.event.canvas.CanvasCloseEvent
import de.fruxz.sparkle.framework.event.canvas.CanvasOpenEvent
import de.fruxz.sparkle.framework.event.canvas.CanvasUpdateEvent
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.server.SparkleApp
import de.fruxz.sparkle.framework.visual.canvas.CanvasFlag.*
import de.fruxz.sparkle.framework.visual.canvas.design.AdaptiveCanvasCompose
import de.fruxz.sparkle.framework.visual.item.ItemLike
import de.fruxz.sparkle.framework.effect.sound.SoundEffect
import de.fruxz.sparkle.framework.extension.coroutines.asSync
import de.fruxz.sparkle.framework.extension.system
import de.fruxz.sparkle.framework.visual.canvas.CanvasBase.Companion
import de.fruxz.stacked.extension.asStyledComponent
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import net.kyori.adventure.builder.AbstractBuilder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

/**
 * This class helps to easily create ui's for players.
 * @param identityKey The key of the canvas, that is used to bind the actions to the canvas.
 * @param label The label, which the viewer will see on top of the inventory.
 * @param base The size of the canvas.
 * @param content The content, which is placed inside the canvas
 * @param flags The individual habits of the canvas.
 * @param openSoundEffect The sound effect, which is played, when the canvas is opened.
 * @author Fruxz
 * @since 1.0
 */
data class MutableCanvas(
	override var label: Component = Component.empty(),
	override var base: CanvasBase = CanvasBase.ofLines(3),
	override var pagination: PaginationType<*> = PaginationType.none(),
	override var content: Map<Int, ItemLike> = emptyMap(),
	override var flags: Set<CanvasFlag> = emptySet(),
	override var openSoundEffect: SoundEffect? = null,
	override var asyncItems: Map<Int, Deferred<ItemLike>> = emptyMap(),
) : Canvas(label, base, pagination, content, flags, openSoundEffect, asyncItems), Producible<Canvas>, AbstractBuilder<Canvas> {

	override var onRender: CanvasRender = CanvasRender {  }
	override var onOpen: CanvasOpenEvent.() -> Unit = { }
	override var onUpdate: CanvasUpdateEvent.() -> Unit = { }
	override var onClose: CanvasCloseEvent.() -> Unit = { }
	override var onClicks: Map<Int?, List<CanvasClickEvent.() -> Unit>> = emptyMap()
	override var onUpdateNonClearableSlots: Set<Int> = emptySet()

	override var identity = buildRandomTag(10, tagType = MIXED_CASE)

	// properties

	@CanvasDsl
	fun label(component: ComponentLike) {
		this.label = component.asComponent()
	}

	@CanvasDsl
	fun label(styledString: String) = label(component = styledString.asStyledComponent)

	@CanvasDsl
	fun base(base: CanvasBase) {
		this.base = base
	}

	@CanvasDsl
	fun base(size: Int) = base(CanvasBase.ofSize(size))

	@CanvasDsl
	fun base(inventoryType: InventoryType) = base(Companion.ofType(inventoryType))

	@CanvasDsl
	@CanvasPrototypeAPI
	fun pagination(pagination: PaginationType<*>) {
		this.pagination = pagination
	}

	@CanvasDsl
	fun flags(flags: Iterable<CanvasFlag>) {
		this.flags = flags.toSet()
	}

	@CanvasDsl
	fun flags(vararg flags: CanvasFlag) = flags(flags.toSet())

	@CanvasDsl
	fun openingSound(soundEffect: SoundEffect?) {
		this.openSoundEffect = soundEffect
	}

	// setters

	operator fun set(slot: Int, itemLike: ItemLike?) {
		if (itemLike != null) {
			content += slot to itemLike
		} else
			content -= slot
	}

	fun setDeferred(slot: Int, itemLikeProcess: DeferredComposable<ItemLike>) {
		asyncItems += slot to SparkleApp.coroutineScope.async(block = itemLikeProcess::compose)
	}

	operator fun set(slots: Iterable<Int>, itemLike: ItemLike?) =
		slots.forEach { set(it, itemLike) }

	fun setDeferred(slots: Iterable<Int>, process: DeferredComposable<ItemLike>) =
		slots.forEach { setDeferred(it, process) }

	operator fun set(vararg slots: Int, itemLike: ItemLike?) =
		set(slots.toList(), itemLike)

	fun setDeferred(vararg slots: Int, process: DeferredComposable<ItemLike>) =
		setDeferred(slots.toList(), process)


	// ItemStack support

	operator fun set(slot: Int, itemStack: ItemStack?) =
		set(slot, itemStack?.let { ItemLike.of(it) })

	@JvmName("asyncItemStack")
	fun setDeferred(slot: Int, itemStackProcess: DeferredComposable<ItemStack>): Unit =
		setDeferred(slot, itemLikeProcess = { asSync { ItemLike.of(itemStackProcess.compose(it)) } })

	operator fun set(slots: Iterable<Int>, itemStack: ItemStack?) =
		set(slots, itemStack?.let { ItemLike.of(it) })

	@JvmName("asyncItemStack")
	fun setDeferred(slots: Iterable<Int>, itemStackProcess: DeferredComposable<ItemStack>): Unit =
		slots.forEach { setDeferred(it, itemStackProcess) }

	operator fun set(vararg slots: Int, itemStack: ItemStack?) =
		set(slots.toList(), itemStack?.let { ItemLike.of(it) })

	@JvmName("asyncItemStack")
	fun setDeferred(vararg slots: Int, itemStackProcess: DeferredComposable<ItemStack>): Unit =
		setDeferred(slots.toList(), itemStackProcess)

	// Material support

	operator fun set(slot: Int, material: Material?) =
		set(slot, material?.let { ItemLike.of(it) })

	@JvmName("asyncMaterial5")
	fun setDeferred(slot: Int, materialProcess: DeferredComposable<Material>): Unit =
		setDeferred(slot, itemLikeProcess = { ItemLike.of(materialProcess.compose(system.coroutineScope)) })

	operator fun set(slotIterable: Iterable<Int>, material: Material?): Unit =
		set(slotIterable, material?.let { ItemLike.of(it) })

	@JvmName("asyncMaterial")
	fun setDeferred(slotIterable: Iterable<Int>, materialProcess: DeferredComposable<Material>): Unit =
		slotIterable.forEach { setDeferred(it, materialProcess) }

	operator fun set(vararg slotArray: Int, material: Material?): Unit =
		set(slotArray.toList(), material?.let { ItemLike.of(it) })

	@JvmName("asyncMaterial2")
	fun setDeferred(vararg slotArray: Int, materialProcess: DeferredComposable<Material>): Unit =
		setDeferred(slotIterable = slotArray.toList(), materialProcess)

	// Adaptive support

	operator fun set(slots: Iterable<Int>, adaptiveCanvasCompose: AdaptiveCanvasCompose) =
		adaptiveCanvasCompose.place(this, slots)

	operator fun set(slot: Int, adaptiveCanvasCompose: AdaptiveCanvasCompose) =
		set(listOf(slot), adaptiveCanvasCompose)

	operator fun set(vararg slots: Int, adaptiveCanvasCompose: AdaptiveCanvasCompose) =
		set(slots.toList(), adaptiveCanvasCompose)

	// Inner-placement

	fun setInner(innerSlot: Int, itemLike: ItemLike?) {
		if (innerSlot !in availableInnerSlots) throw IndexOutOfBoundsException("The inner slot $innerSlot is not available in this canvas.")

		set(innerSlots[innerSlot], itemLike)
	}

	fun setInnerDeferred(innerSlot: Int, itemLikeProcess: DeferredComposable<ItemLike>) {
		if (innerSlot !in availableInnerSlots) throw IndexOutOfBoundsException("The inner slot $innerSlot is not available in this canvas.")

		setDeferred(innerSlots[innerSlot], itemLikeProcess)
	}

	fun setInner(innerSlots: Iterable<Int>, itemLike: ItemLike?) =
		innerSlots.forEach { setInner(it, itemLike) }

	fun setInnerDeferred(innerSlots: Iterable<Int>, itemLikeProcess: DeferredComposable<ItemLike>) =
		innerSlots.forEach { setInnerDeferred(it, itemLikeProcess) }

	fun setInner(vararg innerSlots: Int, itemLike: ItemLike?) =
		setInner(innerSlots.toList(), itemLike)

	fun setInnerDeferred(vararg innerSlots: Int, itemLikeProcess: DeferredComposable<ItemLike>) =
		setInnerDeferred(innerSlots.toList(), itemLikeProcess)

	// Inner ItemStack support

	fun setInner(innerSlot: Int, itemStack: ItemStack?) =
		setInner(innerSlot, itemStack?.let { ItemLike.of(it) })

	@JvmName("asyncInnerItemStack")
	fun setInnerDeferred(innerSlot: Int, itemStackProcess: DeferredComposable<ItemStack>): Unit =
		setInnerDeferred(innerSlot, itemLikeProcess = { ItemLike.of(itemStackProcess.compose(it)) })

	fun setInner(innerSlots: Iterable<Int>, itemStack: ItemStack?) =
		setInner(innerSlots, itemStack?.let { ItemLike.of(it) })

	@JvmName("asyncInnerItemStack")
	fun setInnerDeferred(innerSlots: Iterable<Int>, itemStackProcess: DeferredComposable<ItemStack>): Unit =
		innerSlots.forEach { setInnerDeferred(it, itemStackProcess) }

	fun setInner(vararg innerSlots: Int, itemStack: ItemStack?) =
		setInner(innerSlots.toList(), itemStack?.let { ItemLike.of(it) })

	@JvmName("asyncInnerItemStack")
	fun setInnerDeferred(vararg innerSlots: Int, itemStackProcess: DeferredComposable<ItemStack>): Unit =
		setInnerDeferred(innerSlots.toList(), itemStackProcess)

	// Inner Material support

	fun setInner(innerSlot: Int, material: Material?) =
		setInner(innerSlot, material?.let { ItemLike.of(it) })

	@JvmName("asyncInnerMaterial")
	fun setInnerDeferred(innerSlot: Int, materialProcess: DeferredComposable<Material>): Unit =
		setInnerDeferred(innerSlot, itemLikeProcess = { ItemLike.of(materialProcess.compose(it)) })

	fun setInner(innerSlots: Iterable<Int>, material: Material?) =
		setInner(innerSlots, material?.let { ItemLike.of(it) })

	@JvmName("asyncInnerMaterial")
	fun setInnerDeferred(innerSlots: Iterable<Int>, materialProcess: DeferredComposable<Material>): Unit =
		innerSlots.forEach { setInnerDeferred(it, materialProcess) }

	fun setInner(vararg innerSlots: Int, material: Material?) =
		setInner(innerSlots.toList(), material?.let { ItemLike.of(it) })

	@JvmName("asyncInnerMaterial")
	fun setInnerDeferred(vararg innerSlots: Int, materialProcess: DeferredComposable<Material>): Unit =
		setInnerDeferred(innerSlots.toList(), materialProcess)

	// Inner Adaptive support

	fun setInner(innerSlotIterable: Iterable<Int>, adaptiveCanvasCompose: AdaptiveCanvasCompose) =
		adaptiveCanvasCompose.place(this, innerSlotIterable.map { innerSlots[it] })

	fun setInner(innerSlot: Int, adaptiveCanvasCompose: AdaptiveCanvasCompose) =
		setInner(listOf(innerSlot), adaptiveCanvasCompose)

	fun setInner(vararg innerSlots: Int, adaptiveCanvasCompose: AdaptiveCanvasCompose) =
		setInner(innerSlots.toList(), adaptiveCanvasCompose)

	// Interactions

	@CanvasDsl
	fun onCanvasClick(onClick: (CanvasClickEvent) -> Unit) {
		onClicks += null to (onClicks.getOrDefault(null, emptyList()) + onClick)
	}

	@CanvasDsl
	fun onCanvasClickWith(onClick: CanvasClickEvent.() -> Unit) =
		onCanvasClick(onClick)

	@CanvasDsl
	fun onSlotClick(slot: Int, onClick: (CanvasClickEvent) -> Unit) {
		onClicks += slot to (onClicks.getOrDefault(slot, emptyList()) + onClick)
	}

	@CanvasDsl
	fun onSlotClickWith(slot: Int, onClick: CanvasClickEvent.() -> Unit) =
		onSlotClick(slot, onClick)

	@CanvasDsl
	fun onSlotClick(slots: Iterable<Int>, onClick: (CanvasClickEvent) -> Unit) =
		slots.forEach { slot -> onSlotClick(slot, onClick) }

	@CanvasDsl
	fun onSlotClickWith(slots: Iterable<Int>, onClick: CanvasClickEvent.() -> Unit) =
		slots.forEach { slot -> onSlotClick(slot, onClick) }

	@CanvasDsl
	operator fun set(slot: Int, onClick: CanvasClickEvent.() -> Unit) =
		onSlotClick(slot, onClick)

	@CanvasDsl
	operator fun set(slots: Iterable<Int>, onClick: CanvasClickEvent.() -> Unit) =
		slots.forEach { onSlotClick(it, onClick) }

	@CanvasDsl
	operator fun set(vararg slots: Int, onClick: CanvasClickEvent.() -> Unit) =
		onSlotClick(slots.toList(), onClick)

	// State change

	fun onOpen(onOpen: (CanvasOpenEvent) -> Unit) {
		this.onOpen = onOpen
	}

	fun onOpenWith(onOpen: CanvasOpenEvent.() -> Unit) {
		this.onOpen = onOpen
	}

	fun onUpdate(onUpdate: (CanvasUpdateEvent) -> Unit) {
		this.onUpdate = onUpdate
	}

	fun onUpdateWith(onUpdate: CanvasUpdateEvent.() -> Unit) {
		this.onUpdate = onUpdate
	}

	fun onClose(onClose: (CanvasCloseEvent) -> Unit) {
		this.onClose = onClose
	}

	fun onCloseWith(onClose: CanvasCloseEvent.() -> Unit) {
		this.onClose = onClose
	}

	fun onRender(renderer: CanvasRender) {
		this.onRender = renderer
	}

	fun onRenderWith(renderer: CanvasRender) {
		this.onRender = renderer
	}

	// Design

	fun border(itemLike: ItemLike) =
		set(base.borderSlots, itemLike)

	fun border(material: Material) =
		border(ItemLike.of(material))

	fun border(itemStack: ItemStack) =
		border(ItemLike.of(itemStack))

	fun fill(itemLike: ItemLike) =
		set(base.slots, itemLike)

	fun fill(material: Material) =
		border(ItemLike.of(material))

	fun fill(itemStack: ItemStack) =
		border(ItemLike.of(itemStack))

	fun replace(replaceWith: ItemLike?, search: IndexedValue<ItemLike?>.() -> Boolean) =
		base.slots.forEach { slot ->
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

	fun removeFlags(vararg flags: CanvasFlag) {
		this.flags -= flags
	}

	/**
	 * This function adds the [NO_GRAB], [NO_DRAG], [NO_SWAP] and [NO_MOVE] flags
	 * to this [Canvas] using the [annexFlags] function in the background.
	 * This completely disables the possibility for the player, to remove items
	 * from the open canvas or move them around.
	 * @param flags The flags that creates the protective canvas layer.
	 * @author Fruxz
	 * @since 1.0
	 */
	@CanvasDsl
	fun disablePlayerItemGrabbing(vararg flags: CanvasFlag = CanvasFlag.DEFAULT_PROTECTION.toTypedArray()) = annexFlags(*flags)

	/**
	 * This function removes the [NO_GRAB], [NO_DRAG], [NO_SWAP] and [NO_MOVE] flags
	 * from this [Canvas] using the [removeFlags] function in the background.
	 * This re-enables the possibility for the player, to remove items from the open
	 * canvas or move them around. This only takes effect, if the flags were added
	 * or/and if you used the [disablePlayerItemGrabbing] function before.
	 * @param flags The flags that created the protective canvas layer.
	 * @author Fruxz
	 * @since 1.0
	 */
	@CanvasDsl
	fun reEnablePlayerItemGrabbing(vararg flags: CanvasFlag = CanvasFlag.DEFAULT_PROTECTION.toTypedArray()) = removeFlags(*flags)

	// Technical stuff

	/**
	 * This function is used to add some slots, that
	 * are not going to get cleared during the [update]
	 * process. This is usefully for example, if you
	 * set some slots during the [onOpen] event, and
	 * you want to keep them, even if you call the
	 * [update] function.
	 * Doing so will prevent flickering, but you have
	 * to clear the [slots] at your self, if you want
	 * to use this.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun addNonClearableUpdateSlots(vararg slots: Int) {
		onUpdateNonClearableSlots += slots.toSet()
	}

	/**
	 * This function is used to add some slots, that
	 * are not going to get cleared during the [update]
	 * process. This is usefully for example, if you
	 * set some slots during the [onOpen] event, and
	 * you want to keep them, even if you call the
	 * [update] function.
	 * Doing so will prevent flickering, but you have
	 * to clear the [slots] at your self, if you want
	 * to use this.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun addNonClearableUpdateSlots(slots: Iterable<Int>) {
		onUpdateNonClearableSlots += slots.toSet()
	}

	/**
	 * Removing the [slots] of the update clear prevention
	 * @author Fruxz
	 * @since 1.0
	 */
	fun takeNonClearableUpdateSlots(vararg slots: Int) {
		onUpdateNonClearableSlots -= slots.toSet()
	}

	/**
	 * Removing the [slots] of the update clear prevention
	 * @author Fruxz
	 * @since 1.0
	 */
	fun takeNonClearableUpdateSlots(slots: Iterable<Int>) {
		onUpdateNonClearableSlots -= slots.toSet()
	}

	// Internal stuff

	private fun optimize() {
		val contentSize = content.size

		content = content.filterNot { it.value.asItemStack().type.isAir }

		debugLog("Optimized canvas content from $contentSize to ${content.size} @ $identity")
	}

	override fun produce(): Canvas {
		optimize()
		return this
	}

	override fun build(): Canvas {
		optimize()
		return this
	}

}

/**
 * This function constructs a new [MutableCanvas].
 * *If you immediately want a non-mutable [Canvas] as a return,
 * but want an [MutableCanvas] to build it, use the builder
 * parameter!*
 * @param key The identity of the [MutableCanvas] to create.
 * @param size The size of the canvas.
 * @return The created mutable [MutableCanvas].
 * @author Fruxz
 * @since 1.0
 */
fun buildCanvas(base: CanvasBase = CanvasBase.ofLines(3)): MutableCanvas =
	MutableCanvas(base = base)

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
fun buildCanvas(base: CanvasBase = CanvasBase.ofLines(3), builder: MutableCanvas.() -> Unit): Canvas =
	MutableCanvas(base = base).apply(builder).build()

fun buildCanvas(size: Int, builder: MutableCanvas.() -> Unit): Canvas =
	buildCanvas(CanvasBase.ofSize(size), builder)

fun buildCanvas(inventoryType: InventoryType, builder: MutableCanvas.() -> Unit): Canvas =
	buildCanvas(CanvasBase.ofType(inventoryType), builder)
