package de.fruxz.sparkle.framework.visual.canvas

import de.fruxz.ascend.extension.container.distinctSetBy
import de.fruxz.ascend.extension.container.forEachNotNull
import de.fruxz.ascend.extension.data.RandomTagType.MIXED_CASE
import de.fruxz.ascend.extension.data.buildRandomTag
import de.fruxz.ascend.extension.objects.takeIfInstance
import de.fruxz.ascend.tool.smart.identification.Identifiable
import de.fruxz.sparkle.framework.effect.sound.SoundEffect
import de.fruxz.sparkle.framework.event.canvas.CanvasClickEvent
import de.fruxz.sparkle.framework.event.canvas.CanvasCloseEvent
import de.fruxz.sparkle.framework.event.canvas.CanvasOpenEvent
import de.fruxz.sparkle.framework.event.canvas.CanvasRenderEvent
import de.fruxz.sparkle.framework.event.canvas.CanvasUpdateEvent
import de.fruxz.sparkle.framework.event.canvas.CanvasUpdateEvent.UpdateReason
import de.fruxz.sparkle.framework.event.canvas.CanvasUpdateEvent.UpdateReason.PLUGIN
import de.fruxz.sparkle.framework.extension.coroutines.asAsync
import de.fruxz.sparkle.framework.extension.coroutines.asSyncDeferred
import de.fruxz.sparkle.framework.extension.coroutines.doAsync
import de.fruxz.sparkle.framework.extension.coroutines.doSync
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.extension.effect.playSoundEffect
import de.fruxz.sparkle.framework.extension.sparkle
import de.fruxz.sparkle.framework.extension.visual.ui.get
import de.fruxz.sparkle.framework.extension.visual.ui.set
import de.fruxz.sparkle.framework.extension.visual.ui.slots
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.visual.canvas.Canvas.CanvasRender
import de.fruxz.sparkle.framework.visual.canvas.CanvasFlag.NO_OPEN
import de.fruxz.sparkle.framework.visual.canvas.CanvasFlag.NO_UPDATE
import de.fruxz.sparkle.framework.visual.canvas.PaginationType.Companion.PaginationBase.PAGED
import de.fruxz.sparkle.framework.visual.canvas.PaginationType.Companion.PaginationBase.SCROLL
import de.fruxz.sparkle.framework.visual.canvas.session.CanvasSessionManager
import de.fruxz.sparkle.framework.visual.item.ItemLike
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import kotlin.coroutines.CoroutineContext
import kotlin.math.max

/**
 * This class helps to easily create ui's for players.
 * This should be unique for each canvas, but if you want to share a canvas habits & actions, use the same.
 * Using shared keys can also improve memory-usage, if you do it cleverly. Why? because each registered action,
 * session and update is identified by the key! ***USING THE SAME KEY OR GENERATE A SINGLE CANVAS MULTIPLE
 * TIMES IS QUITE DANGEROUS AND CAN AFFECT BEHAVIOR AND CANVAS UPDATES!***
 * @param label The label, which the viewer will see on top of the inventory.
 * @param base The size of the canvas.
 * @param content The content, which is placed inside the canvas
 * @param flags The individual habits of the canvas.
 * @param openSoundEffect The sound effect, which is played, when the canvas is opened.
 * ***NOTE: If you want to edit a canvas, use the [MutableCanvas] class, maybe the [toMutable] or [buildCanvas] function helps!***
 * @author Fruxz
 * @since 1.0
 */
open class Canvas(
	open val label: Component = Component.empty(),
	open val base: CanvasBase = CanvasBase.ofLines(3),
	open val pagination: PaginationType<*> = PaginationType.none(),
	open val content: Map<Int, ItemLike> = emptyMap(),
	open val flags: Set<CanvasFlag> = emptySet(),
	open val openSoundEffect: SoundEffect? = null,
	open val asyncItems: Map<Int, Deferred<ItemLike>> = emptyMap(),
) : Identifiable<Canvas> {

	open val onRender: CanvasRender = CanvasRender {  }
	open val onOpen: CanvasOpenEvent.() -> Unit = { }
	open val onClose: CanvasCloseEvent.() -> Unit = { }
	open val onUpdate: CanvasUpdateEvent.() -> Unit = { }
	open val onClicks: Map<Int?, List<CanvasClickEvent.() -> Unit>> = emptyMap()
	open val onFinishedDeferred: suspend Set<Deferred<ItemStack>>.() -> Unit = { }
	open val onUpdateNonClearableSlots: Set<Int> = emptySet()

	override val identity = buildRandomTag(10, tagType = MIXED_CASE)

	val innerSlots: List<Int>
		get() = buildList {
			for (x in 1..(base.lines - 2)) {
				for (x2 in ((1 + (x * 9))..(7 + (x * 9)))) {
					add(x2)
				}
			}
		}

	val availableInnerSlots
		get() = innerSlots.indices

	val center: Int
		get() = base.virtualSize / 2 - 1

	val virtualSlots: IntRange
		get() = 0..(if (pagination.base == null) base.virtualSize-1 else max(base.virtualSize-1, pagination.computeRealSlot(asyncItems.maxOf { it.key })))

	operator fun get(slot: Int): ItemLike? {
		return content[slot]
	}

	operator fun get(slots: Iterable<Int>): List<ItemLike?> =
		slots.map(this::get)

	operator fun get(vararg slots: Int) =
		get(slots.toList())

	/**
	 * This computational value returns the players, that are currently
	 * registered in the system, that are currently viewing this canvas.
	 * @author Fruxz
	 * @since 1.0
	 */
	val viewers: Set<Player>
		get() = CanvasSessionManager.getSessions(identityObject).map { it.key }.distinctSetBy { it.uniqueId }

	fun display(
		vararg receivers: HumanEntity?,
		data: Map<Key, Any> = emptyMap(),
		triggerOpenEvent: Boolean = true,
		triggerSound: Boolean = true,
	) = sparkle.coroutineScope.launch {
		if (NO_OPEN in flags) cancel()

		receivers.toList().forEachNotNull { receiver ->

			if (receiver is Player) {

				var (renderResult, _) = render(receiver, data = data) ?: return@forEachNotNull

				CanvasOpenEvent(receiver, this@Canvas, renderResult, data).let { event ->
					if (!triggerOpenEvent || event.callEvent()) {
						renderResult = event.inventory

						doSync {
							receiver.openInventory(renderResult)
							CanvasSessionManager.putSession(receiver, this@Canvas, event.data)
						}

						event.apply(onOpen)

						if (triggerSound) openSoundEffect?.let { receiver.playSoundEffect(it) }

					} else {
						return@forEachNotNull
					}
				}

			}

		}

	}

	/**
	 * Tip: Do not call this function during an execution-part of the canvas itself, like inside the onOpen-execution.
	 * This would lead, that even the update calls this method again and again.
	 * Executing this per-player can also be dangerous, because multiple players executing this part of the code updating
	 * (by default) even the canvas of the other [receivers], which would be unnecessary and power consuming!
	 * @param receivers The receivers of this update (if they have the canvas open); By default [viewers]
	 * @param data The additional data to be used in messaging the different accessors
	 * @param triggerUpdateEvent If the [CanvasUpdateEvent] should be triggered
	 * @param triggerSound If the [openSoundEffect] should be played to the [receivers]
	 * @param triggerOnOpenToo The [onUpdate] is called, when the rendering is done and sending.
	 * If we look on [display]: instead of [onUpdate] is the [onOpen] here called. To get the
	 * same behavior of [onOpen] also in updates, not only first-openings, this should be enabled.
	 * This (as normal) executes the [onUpdate] and directly after it the [onOpen].
	 * @return The job, executing the update
	 * @author Fruxz
	 * @since 1.0
	 */
	fun update(
		vararg receivers: HumanEntity? = viewers.toTypedArray(),
		data: Map<Key, Any> = emptyMap(),
		triggerUpdateEvent: Boolean = true,
		triggerSound: Boolean = true,
		triggerOnOpenToo: Boolean = true,
		updateReason: UpdateReason = PLUGIN,
	) = sparkle.coroutineScope.launch {
		if (NO_UPDATE in flags) cancel()

		receivers.toList().forEachNotNull { receiver ->
			var topInventory = receiver.openInventory.topInventory

			if (receiver is Player && topInventory.viewers.isNotEmpty() && receiver in viewers) {

				doAsync { // todo maybe doAsync lets the update struggle with pagination? because async inv generation is invalid???
					render(receiver, data = data) {
						this.inventory.contents.forEachIndexed { index, itemStack ->
							if (topInventory[index] != itemStack && index !in asyncItems.keys && !(itemStack == null && index in onUpdateNonClearableSlots)) {
								topInventory[index] = itemStack
							}
						}
					}
				}.join()

				CanvasUpdateEvent(receiver, this@Canvas, topInventory, data, updateReason).let { event ->
					if (!triggerUpdateEvent || event.callEvent()) {
						topInventory = event.inventory

						doSync {

							if (topInventory.viewers.isNotEmpty() && receiver in viewers) { // just a check, to keep it bulletproof
								debugLog("Updating ${receiver.name}'s inventory from $identity canvas")

								receiver.openInventory(topInventory) // TODO check, if this is really needed, because the inventory is already open
								// TODO for above: maybe only do it, if the inventory is not the same as the topInventory aka. is not open

								CanvasSessionManager.putSession(receiver, this@Canvas, data)
							} else
								debugLog("Updating ${receiver.name}'s inventory blocked, during last check")
						}

						event.apply(onUpdate)
						if (triggerOnOpenToo) event.apply(onOpen)

						if (triggerSound) openSoundEffect?.let { receiver.playSoundEffect(it) }

					} else {
						return@forEachNotNull
					}
				}

			}

		}

	}

	suspend fun render(
		target: Player? = null, // todo check if target is indeed set in the implementations of these function, even if it is possible to be null
		vendor: App = sparkle,
		syncContext: CoroutineContext = vendor.syncDispatcher,
		asyncContext: CoroutineContext = vendor.asyncDispatcher,
		data: Map<Key, Any> = emptyMap(),
		onCompletion: suspend CanvasRenderResult.() -> Unit = {  }, // on whole completion, every deferred is also set!
	): CanvasRenderResult? {

		var state = base.generateInventory(target, label)
		val scrollState = (data[PaginationType.CANVAS_SCROLL_STATE]?.takeIfInstance<Int>() ?: 0)
		val staticContent = asSyncDeferred(
			vendor = vendor,
			context = syncContext,
		) {
			when {
				base.virtualSize % 9 != 0 -> (0 until base.virtualSize).map { slot ->
					content[slot]?.asItemStack()
				}
				else -> ((0 until base.virtualSize) to pagination.contentRendering(scrollState, this)).let { (slots, paginated) ->
					slots.map { paginated[it]?.asItemStack() }
				}
			}
		}

		// Phase 1 - place static content

		state.contents = staticContent.await().toTypedArray()

		// Phase 2 - call render event

		CanvasRenderEvent(target, this, state).let { event ->
			if (!event.callEvent()) return null

			event.apply { onRender.render(this) }
			state = event.renderResult

		}

		// Phase 3 - place deferred content
		var deferredItemQueue: Set<Deferred<ItemStack>> = emptySet()

		asyncItems.forEach { (placedSlot, value) ->
			when (pagination.base) { // \/ now producing relative position to current point-of-view
				null -> pagination.computeRealSlot(placedSlot).takeIf { it in state.slots } // <- expecting non-pagination, but still use computeRealSlot for 3rd party software
				SCROLL -> pagination.computeRealSlot(placedSlot - (scrollState * 8)).takeIf { it in state.slots } // <- items placed for scroll panels
				PAGED -> (placedSlot - (scrollState * (base.virtualSize - 9))).takeIf { it in state.slots.toList().dropLast(9) }
			}?.let { slot ->

				deferredItemQueue += asAsync(vendor = vendor, context = asyncContext) {
					value.await().asItemStack().also {
						state.setItem(slot, it)
					}
				}.apply {
					invokeOnCompletion {
						doAsync(vendor = vendor, context = asyncContext) {
							val queue = deferredItemQueue

							if (queue.all { it.isCompleted }) {
								onFinishedDeferred.invoke(queue)
								onCompletion.invoke(CanvasRenderResult(state, queue))
							}

						}
					}
				}

			}
		}

		// Phase 4 - return result

		return CanvasRenderResult(
			inventory = state,
			deferredItems = deferredItemQueue,
		)

	}

	data class CanvasRenderResult(
		val inventory: Inventory,
		val deferredItems: Set<Deferred<ItemStack>>,
	) {

		/**
		 * This computational value returns, if every single
		 * deferred item-production got completed.
		 * This utilizes [Deferred.isCompleted]!
		 * @author Fruxz
		 * @since 1.0
		 */
		val isCompleted: Boolean
			get() = deferredItems.all { it.isCompleted }

		/**
		 * This computational value returns, if any of the
		 * deferred item-productions got cancelled on any
		 * time. This utilizes [Deferred.isCancelled]!
		 * @author Fruxz
		 * @since 1.0
		 */
		val isCancelled: Boolean
			get() = deferredItems.any { it.isCancelled }

	}

	fun toMutable(
		label: Component = this.label,
		canvasBase: CanvasBase = this.base,
		pagination: PaginationType<*> = this.pagination,
		content: Map<Int, ItemLike> = this.content,
		panelFlags: Set<CanvasFlag> = this.flags,
		openSoundEffect: SoundEffect? = this.openSoundEffect,
		onRender: CanvasRender = this.onRender,
		onOpen: CanvasOpenEvent.() -> Unit = this.onOpen,
		onUpdate: CanvasUpdateEvent.() -> Unit = this.onUpdate,
		onClose: CanvasCloseEvent.() -> Unit = this.onClose,
		onClicks: Map<Int?, List<CanvasClickEvent.() -> Unit>> = this.onClicks,
		onUpdateNonClearableSlots: Set<Int> = emptySet(),
		asyncItems: Map<Int, Deferred<ItemLike>> = this.asyncItems,
		identity: String = this.identity
	): MutableCanvas = MutableCanvas(label, canvasBase, pagination, content, panelFlags, openSoundEffect, asyncItems).apply {
		this.identity = identity
		this.onRender = onRender
		this.onOpen = onOpen
		this.onUpdate = onUpdate
		this.onClose = onClose
		this.onClicks = onClicks
		this.onUpdateNonClearableSlots = onUpdateNonClearableSlots
	}

	/**
	 * Utilizes the [display] function on the [canvas] to display
	 * the canvas to [this] type of [Player].
	 * @author Fruxz
	 * @since 1.0
	 */
	fun Player.showCanvas(
		canvas: Canvas,
		data: Map<Key, Any> = emptyMap(),
		triggerOpenEvent: Boolean = true,
		triggerSound: Boolean = true,
	) = canvas.display(receivers = arrayOf(this), data, triggerOpenEvent, triggerSound)

	/**
	 * Builds a [Canvas] using the [buildCanvas] function and
	 * displays it via the [display] function.
	 * ***Please do not use this function, if you don't know what you are doing!
	 * Actions and processes bound to stuff, like item-actions, are still stored
	 * again and again (if randomized identities are used), so that every creation
	 * caches its own item-actions, which can lead to a big memory leak! Take care!***
	 * @author Fruxz
	 * @since 1.0
	 */
	fun Player.showCanvas(
		base: CanvasBase = CanvasBase.ofLines(3),
		data: Map<Key, Any> = emptyMap(),
		triggerOpenEvent: Boolean = true,
		triggerSound: Boolean = true,
		canvasBuilder: MutableCanvas.() -> Unit,
	) = buildCanvas(base, canvasBuilder).display(receivers = arrayOf(this), data, triggerOpenEvent, triggerSound)

	fun interface CanvasRender {
		suspend fun render(event: CanvasRenderEvent)
	}

	@DslMarker
	@MustBeDocumented
	annotation class CanvasDsl

	@MustBeDocumented
	@RequiresOptIn(message = "This api is still in development and may not work like expected and may change in the future")
	annotation class ExperimentalCanvasApi

}