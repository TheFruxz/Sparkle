package de.jet.paper.tool.display.ui.panel

import de.jet.jvm.extension.container.and
import de.jet.jvm.extension.container.orEmptyMutableList
import de.jet.jvm.extension.tryOrNull
import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.jvm.tool.smart.identification.Identity
import de.jet.paper.app.JetCache
import de.jet.paper.extension.display.BOLD
import de.jet.paper.extension.display.YELLOW
import de.jet.paper.extension.display.ui.item
import de.jet.paper.extension.display.ui.panelIdentificationKey
import de.jet.paper.extension.paper.createInventory
import de.jet.paper.extension.paper.createKey
import de.jet.paper.extension.paper.legacyString
import de.jet.paper.extension.system
import de.jet.paper.extension.tasky.sync
import de.jet.paper.runtime.event.PanelClickEvent
import de.jet.paper.runtime.event.PanelCloseEvent
import de.jet.paper.runtime.event.PanelOpenEvent
import de.jet.paper.structure.app.App
import de.jet.paper.tool.display.color.ColorType
import de.jet.paper.tool.display.item.Item
import de.jet.paper.tool.display.ui.inventory.Container
import de.jet.paper.tool.effect.sound.SoundMelody
import de.jet.paper.tool.smart.Logging
import de.jet.paper.tool.smart.VendorsIdentifiable
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

data class PanelReceiveData(
	var panel: Panel,
	val receiver: Player,
	val receiveParameters: Map<String, Any>,
) {

	fun editPanel(process: Panel.() -> Unit) {
		panel = panel.apply(process)
	}

	fun updateView() = panel.display(receiver, receiveParameters)

}

data class Panel(
	override var content: MutableMap<Int, Item> = mutableMapOf(),
	override var label: Component = Component.text("$YELLOW${BOLD}Panel"),
	val lines: Int = 3,
	override var theme: ColorType = ColorType.GRAY,
	override var openSound: SoundMelody? = null,
	override var identity: String = "${UUID.randomUUID()}",
	override var vendor: Identifiable<App> = system,
	var onReceiveEvent: PanelReceiveData.() -> Unit = { },
	var onOpenEvent: PanelOpenEvent.() -> Unit = { },
	var onCloseEvent: PanelCloseEvent.() -> Unit = { },
	var icon: Item = theme.wool.item.apply {
		lore = """
			
			This panel has no icon, override
			this example by replacing the
			icon variable at your panel.
			   
		""".trimIndent()
	},
	var overridingBorderProtection: Boolean = true,
	val generateBorder: Boolean = true,
) : Cloneable, Logging, Container<Panel>(label = label, size = lines * 9, theme = theme, openSound = openSound), VendorsIdentifiable<Panel> {

	/**
	 * This value represents the used key, to identify the border
	 * items, used in the base of the panel. This key is mostly
	 * used, to cancel a click, onto the border of the panel.
	 * @author Fruxz
	 * @since 1.0
	 */
	val borderKey = system.createKey("panelBorder")

	init {
		if (generateBorder && content.isEmpty()) { // do not write a border, if already content is inside or not enabled
			content = content.apply {

				border(theme.stainedGlassPane.item.blankLabel().apply {
					if (overridingBorderProtection)
						dataPut(borderKey, 1)
				})

			}
		}
	}

	override val vendorIdentity = vendor.identityObject

	override val thisIdentity = "PLACEHOLDER"

	override val sectionLabel = "Panel/$identity"

	/**
	 * The flags, that modify the user-experience of the panel.
	 * @see PanelFlag
	 * @author Fruxz
	 * @since 1.0
	 */
	var panelFlags: Set<PanelFlag>
		get() = JetCache.registeredPanelFlags[identity] ?: emptySet()
		set(value) {
			JetCache.registeredPanelFlags[identity] = value
		}

	/**
	 * The identities of the inner slots, that are used to identify
	 * the slots, without the border surrounding it.
	 * @author Fruxz
	 * @since 1.0
	 */
	private val computedInnerSlots: List<Int> by lazy {
		mutableListOf<Int>().apply {
			for (x in 1..(lines - 2)) {
				for (x2 in ((1 + (x * 9))..(7 + (x * 9))))
					add(x2)
			}
		}
	}

	/**
	 * The available inner slots, that can be used to place inner.
	 * It's like the size of the inventory, but without the border.
	 * @author Fruxz
	 * @since 1.0
	 */
	val innerSlots by lazy { 0..computedInnerSlots.lastIndex }

	/**
	 * This function replaces the onReceiveEvent, that is used to
	 * handle the first-look, of the player, onto the panel.
	 * This function can modify the receiving data, to change the
	 * panel, that is displayed to the player.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun onReceive(onReceive: (PanelReceiveData) -> Unit) {
		onReceiveEvent = onReceive
	}

	/**
	 * This function replaces the onReceiveEvent, that is used to
	 * handle the first-look, of the player, onto the panel.
	 * This function can modify the receiving data, to change the
	 * panel, that is displayed to the player.
	 * This function uses the this-perspective, to handle the
	 * panel-receive-event.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun onReceiveWith(onReceive: PanelReceiveData.() -> Unit) =
		onReceive(onReceive)

	/**
	 * This function adds a new click handler, that is used to
	 * handle the click-event, of the player, onto the panel.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun onClick(onClick: (PanelClickEvent) -> Unit) {
		JetCache.panelInteractions[identityObject] = JetCache.panelInteractions[identityObject].orEmptyMutableList() and onClick
	}

	/**
	 * This function adds a new click handler, that is used to
	 * handle the click-event, of the player, onto the panel.
	 * This function uses the this-perspective, to handle the
	 * panel-click-event.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun onClickWith(onClick: PanelClickEvent.() -> Unit) =
		onClick(onClick)

	/**
	 * This function adds a new [onClick], which only looks at the
	 * slot [slot]. If [slot] is clicked, [action] will be executed.
	 * @author Fruxz
	 * @since 1.0
	 */
	operator fun set(slot: Int, action: (PanelClickEvent) -> Unit) =
		onClick { event -> if (event.clickedSlot == slot) action(event) }

	/**
	 * This function replaces the [onOpenEvent], that is used to
	 * handle the open-event, of the player, onto the panel.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun onOpen(onOpen: (PanelOpenEvent) -> Unit) {
		onOpenEvent = onOpen
	}

	/**
	 * This function replaces the [onOpenEvent], that is used to
	 * handle the open-event, of the player, onto the panel.
	 * This function uses the this-perspective, to handle the
	 * panel-open-event.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun onOpenWith(onOpen: PanelOpenEvent.() -> Unit) =
		onOpen(onOpen)

	/**
	 * This function replaces the [onCloseEvent], that is used to
	 * handle the close-event, of the player, onto the panel.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun onClose(onClose: (PanelCloseEvent) -> Unit) {
		onCloseEvent = onClose
	}

	/**
	 * This function replaces the [onCloseEvent], that is used to
	 * handle the close-event, of the player, onto the panel.
	 * This function uses the this-perspective, to handle the
	 * panel-close-event.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun onCloseWith(onClose: PanelCloseEvent.() -> Unit) =
		onClose(onClose)
	
	fun placeInner(slot: Int, action: (PanelClickEvent) -> Unit) {
		this[computedInnerSlots[slot]] = action
	}

	fun placeInner(slot: Int, item: Item) {
		content[computedInnerSlots[slot]] = item
	}

	fun placeInner(slot: Int, stack: ItemStack) = placeInner(slot = slot, item = Item(stack))

	fun placeInner(slot: Int, material: Material) = placeInner(slot = slot, item = Item(material))

	fun placeInner(rangeSlots: IntRange, item: Item) {
		rangeSlots.forEach {
			placeInner(it, item)
		}
	}

	fun placeInner(rangeSlots: IntRange, itemStack: ItemStack) {
		rangeSlots.forEach {
			placeInner(it, itemStack)
		}
	}

	fun placeInner(rangeSlots: IntRange, material: Material) {
		rangeSlots.forEach {
			placeInner(it, material)
		}
	}

	fun placeInner(arraySlots: Array<Int>, item: Item) {
		arraySlots.forEach {
			placeInner(it, item)
		}
	}

	fun placeInner(arraySlots: Array<Int>, itemStack: ItemStack) {
		arraySlots.forEach {
			placeInner(it, itemStack)
		}
	}

	fun placeInner(arraySlots: Array<Int>, material: Material) {
		arraySlots.forEach {
			placeInner(it, material)
		}
	}

	fun placeInner(arraySlots: Collection<Int>, item: Item) {
		arraySlots.forEach {
			placeInner(it, item)
		}
	}

	fun placeInner(arraySlots: Collection<Int>, itemStack: ItemStack) {
		arraySlots.forEach {
			placeInner(it, itemStack)
		}
	}

	fun placeInner(arraySlots: Collection<Int>, material: Material) {
		arraySlots.forEach {
			placeInner(it, material)
		}
	}

	fun placeInner(listSlots: List<Int>, item: Item) {
		listSlots.forEach {
			placeInner(it, item)
		}
	}

	fun placeInner(listSlots: List<Int>, material: Material) {
		listSlots.forEach {
			placeInner(it, material)
		}
	}

	fun placeInner(map: Map<Int, Item>) {
		map.forEach { (key, value) ->
			placeInner(key, value)
		}
	}

	/**
	 * This computational value produces the inventory of this
	 * panel, by creating a new inventory and placing every item,
	 * configured in this panel, into it.
	 * @author Fruxz
	 * @since 1.0
	 */
	override val rawInventory: Inventory
		get() {
			val inventory = createInventory(null, size, label)

			content.forEach { (key, value) ->
				if (key < inventory.size) {
					inventory.setItem(key, value.produce())
				} else
					sectionLog.warning("Failed to produce item: $value to slot $key because it is higher that the size-content max ${inventory.size}!")
			}

			return inventory
		}

	/**
	 * This function creates a copy of this panel, by using
	 * the [copy] function of this [Panel] data class.
	 * It is recommended, to directly use the [copy] function
	 * instead, but for overriding reasons, this exists.
	 * @author Fruxz
	 * @since 1.0
	 */
	override fun clone() = copy()

	/**
	 * This function adds the current panel to the [JetCache.completedPanels],
	 * so that the identification of a panel can be easily done.
	 * The identification of [getPanel] only uses the completed panels as a
	 * reference, so this function is required, to be identified as a real
	 * panel.
	 * This function is automatically called when the panel is created.
	 * @see getPanel
	 * @see JetCache.completedPanels
	 * @author Fruxz
	 * @since 1.0
	 */
	fun complete() {
		JetCache.completedPanels.add(this)
	}

	override fun display(humanEntity: HumanEntity) =
		display(humanEntity, emptyMap())

	override fun display(receiver: Player) =
		display(humanEntity = receiver)


	override fun display(humanEntity: HumanEntity, specificParameters: Map<String, Any>) { with(copy()) {
		val previousState = this@Panel.content.toMap()

		complete()

		this@with.content = this@with.content.apply {
			set(4, this@with.icon.apply {

				label = this@with.label.legacyString

				dataPut(panelIdentificationKey, this@with.identity, true)

				if (overridingBorderProtection) {
					dataPut(borderKey, 1)
				}

				// TODO check if the above is needed, because it may causes problems with the indicator

			})
		}

		if (humanEntity is Player) {

			val editedPanel = try {

				PanelReceiveData(this@with, humanEntity, specificParameters)
					.apply(onReceiveEvent)
					.panel

			} catch (exception: Exception) {

				exception.printStackTrace()

				this@with.apply {
					fill(Material.RED_STAINED_GLASS_PANE)
				}

			}

			sync { humanEntity.openInventory(editedPanel.rawInventory) }

		} else
			super.display(humanEntity, specificParameters)

		this@Panel.content = previousState.toMutableMap()

	} }

	override fun display(receiver: Player, specificParameters: Map<String, Any>) =
		display(humanEntity = receiver, specificParameters)

	/**
	 * This function returns, if the [inventory] is (via the identity) a
	 * panel and also if the [inventory]-panel has the same panel identity,
	 * as this Panels [identity].
	 * @param inventory that gets checked
	 * @author Fruxz
	 * @since 1.0
	 */
	fun isPanel(inventory: Inventory) = inventory.panelIdentity?.identity == this.identity

	/**
	 * This function returns, if the [panel] is (via the identity)
	 * the same panel, as this [Panel]. True, if the identity is the same.
	 * @param panel that gets checked
	 * @author Fruxz
	 * @since 1.0
	 */
	fun isPanel(panel: Panel) = panel.identity == this.identity

	companion object {

		/**
		 * Returns the identity of the panel, that the [InventoryView.getTopInventory]
		 * inventory represents, or null, if there is no panel identity linked.
		 * @author Fruxz
		 * @since 1.0
		 */
		val InventoryView.panelIdentity: Identifiable<Panel>?
			get() = topInventory.panelIdentity

		/**
		 * Returns the identity of the panel, that this [Inventory]
		 * represents, or null, if there is no panel identity linked.
		 * @author Fruxz
		 * @since 1.0
		 */
		val Inventory.panelIdentity: Identity<Panel>?
			get() = getItem(4)?.item?.identityObject?.change()

		/**
		 * Returns the panel, if the provided inventory is registered as a panel.
		 * @return the panel, or null if it is not a completed panel
		 * @author Fruxz
		 * @since 1.0
		 */
		fun <T : Inventory> T.getPanel() = tryOrNull { panelIdentity?.let { panelIdentity ->
			JetCache.completedPanels.lastOrNull { it.identity == "$panelIdentity" }
		} }

	}

}