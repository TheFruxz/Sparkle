package de.fruxz.sparkle.framework.visual.canvas

import de.fruxz.ascend.extension.container.distinctSetBy
import de.fruxz.ascend.extension.container.forEachNotNull
import de.fruxz.ascend.extension.data.RandomTagType.MIXED_CASE
import de.fruxz.ascend.extension.data.buildRandomTag
import de.fruxz.ascend.extension.objects.takeIfInstance
import de.fruxz.ascend.tool.smart.identification.Identifiable
import de.fruxz.sparkle.framework.event.canvas.CanvasClickEvent
import de.fruxz.sparkle.framework.event.canvas.CanvasCloseEvent
import de.fruxz.sparkle.framework.event.canvas.CanvasOpenEvent
import de.fruxz.sparkle.framework.event.canvas.CanvasRenderEvent
import de.fruxz.sparkle.framework.event.canvas.CanvasUpdateEvent
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.extension.visual.ui.get
import de.fruxz.sparkle.framework.extension.visual.ui.set
import de.fruxz.sparkle.framework.extension.effect.playSoundEffect
import de.fruxz.sparkle.framework.extension.system
import de.fruxz.sparkle.framework.extension.coroutines.asAsync
import de.fruxz.sparkle.framework.extension.coroutines.asSync
import de.fruxz.sparkle.framework.visual.canvas.Canvas.CanvasRender
import de.fruxz.sparkle.framework.visual.canvas.CanvasFlag.*
import de.fruxz.sparkle.framework.visual.item.ItemLike
import de.fruxz.sparkle.framework.effect.sound.SoundEffect
import de.fruxz.stacked.extension.subKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import kotlin.time.Duration.Companion.seconds

/**
 * This class helps to easily create ui's for players.
 * @param identityKey The key of the canvas, that is used to bind the actions to the canvas.
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
	open val onFinishedDeferred: List<Deferred<ItemStack>>.() -> Unit = { }
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
		owner: InventoryHolder? = null
	) = system.coroutineScope.launch {
		if (NO_OPEN in flags) cancel()

		val inventoryContent = async { asSync { (0 until base.virtualSize).map { slot -> content[slot]?.asItemStack() }.toTypedArray() } }
		val scrollableContent = async { pagination.contentRendering((data[PaginationType.CANVAS_SCROLL_STATE]?.takeIfInstance<Int>() ?: 0), ((base.virtualSize + 1) / 9), content) }

		receivers.toList().forEachNotNull { receiver ->
			var localInstance = base.generateInventory(
				owner = owner,
				label = label,
			).apply {
				this.contents = when {
					base.virtualSize % 9 != 0 -> runBlocking { inventoryContent.await() }
					else -> runBlocking { (0 until base.virtualSize).map { slot -> scrollableContent.await()[slot]?.asItemStack() }.toTypedArray() }
				}
			}

			if (receiver is Player) {

				CanvasRenderEvent(receiver, this@Canvas, localInstance).let { event ->
					event.callEvent()
					event.apply { onRender.render(this) }
					localInstance = event.renderResult
				}

				CanvasOpenEvent(receiver, this@Canvas, localInstance, data).let { event ->
					if (!triggerOpenEvent || event.callEvent()) {
						localInstance = event.inventory

						asSync {
							receiver.openInventory(localInstance)
							CanvasSessionManager.putSession(receiver, this@Canvas, event.data)
						}

						asyncItems.map { (key, value) ->
							asAsync {
								val result = value.await().asItemStack()

								localInstance.setItem(key, result)

								result
							}
						}.let { result ->
							system.coroutineScope.launch {
								while (true) {
									if (result.none { it.isActive }) {
										onFinishedDeferred.invoke(result)
										cancel()
										break
									}
									delay(.1.seconds)
								}
							}
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
	) = system.coroutineScope.launch {
		if (NO_UPDATE in flags) cancel()

		val inventoryContent = async { asSync { (0 until base.virtualSize).map { slot -> content[slot]?.asItemStack() }.toTypedArray() } } // todo this is alsp effected by the pagination system

		receivers.toList().forEachNotNull { receiver ->
			var topInventory = receiver.openInventory.topInventory

			// INSERT START

			if (receiver is Player && topInventory.viewers.isNotEmpty() && receiver in viewers) {
				// now the topInventory is safe to use

				runBlocking { inventoryContent.await() }.forEachIndexed { index, itemStack ->
					if (topInventory[index] != itemStack &&
						index !in asyncItems.keys &&
						!(itemStack == null && index in onUpdateNonClearableSlots)
					) topInventory[index] = itemStack
				}

				CanvasRenderEvent(receiver, this@Canvas, topInventory).let { event ->
					event.callEvent()
					event.apply { onRender.render(this) }
					topInventory = event.renderResult
				}

				CanvasUpdateEvent(receiver, this@Canvas, topInventory, data).let { event ->
					if (!triggerUpdateEvent || event.callEvent()) {
						topInventory = event.inventory

						asSync {

							if (topInventory.viewers.isNotEmpty() && receiver in viewers) { // just a check, to keep it bulletproof
								debugLog("Updating ${receiver.name}'s inventory from $identity canvas")

								receiver.openInventory(topInventory)

								CanvasSessionManager.putSession(receiver, this@Canvas, data)
							} else
								debugLog("Updating ${receiver.name}'s inventory blocked, during last check")
						}

						asyncItems.map { (key, value) ->
							asAsync {
								val result = value.await().asItemStack()

								if (result != topInventory[key]) topInventory.setItem(key, result)

								result
							}
						}.let { result ->
							system.coroutineScope.launch {
								while (true) {
									if (result.none { it.isActive }) {
										onFinishedDeferred.invoke(result)
										cancel()
										break
									}
									delay(.1.seconds)
								}
							}
						}

						event.apply(onUpdate)
						if (triggerOnOpenToo) event.apply(onOpen)

						if (triggerSound) openSoundEffect?.let { receiver.playSoundEffect(it) }

					} else {
						return@forEachNotNull
					}
				}

			}

			// INSERT STOP

		}

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

	fun interface CanvasRender {
		suspend fun render(event: CanvasRenderEvent)
	}

	fun interface DeferredComposable<O> {
		suspend fun compose(scope: CoroutineScope): O
	}

	@DslMarker
	@MustBeDocumented
	annotation class CanvasDsl

	internal object CanvasButtons {
		const val PREV_BUTTON_ID = "go.to.prev"
		const val NEXT_BUTTON_ID = "go.to.next"
	}

}