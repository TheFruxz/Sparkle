package de.jet.minecraft.tool.display.item

import de.jet.library.extension.asString
import de.jet.library.extension.forceCast
import de.jet.library.extension.paper.createBlockData
import de.jet.library.extension.paper.itemFactory
import de.jet.library.tool.smart.Identifiable
import de.jet.library.tool.smart.Producible
import de.jet.minecraft.app.JetCache
import de.jet.minecraft.extension.debugLog
import de.jet.minecraft.extension.display.WHITE
import de.jet.minecraft.extension.display.ui.changeColor
import de.jet.minecraft.extension.paper.legacyString
import de.jet.minecraft.extension.system
import de.jet.minecraft.runtime.event.interact.PlayerInteractAtItemEvent
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.tool.display.color.ColorType
import de.jet.minecraft.tool.display.item.PostProperty.*
import de.jet.minecraft.tool.display.item.action.ItemClickAction
import de.jet.minecraft.tool.display.item.action.ItemInteractAction
import de.jet.minecraft.tool.display.item.quirk.Quirk
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent.ShowItem
import net.kyori.adventure.text.event.HoverEventSource
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Material.*
import org.bukkit.NamespacedKey
import org.bukkit.block.data.BlockData
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import java.util.*
import java.util.function.UnaryOperator

data class Item(
	var material: Material = STONE,
	var label: String = "",
	var size: Int = 1,
	var lore: String = "",
	var damage: Int = 0,
	var modifications: MutableSet<Modification> = mutableSetOf(),
	var flags: MutableSet<ItemFlag> = mutableSetOf(),
	var quirk: Quirk = Quirk.empty,
	var postProperties: MutableSet<PostProperty> = mutableSetOf(),
	override var identity: String = "${UUID.randomUUID()}",
	private var data: MutableMap<String, Any> = mutableMapOf(),
	var itemMetaBase: ItemMeta? = null,
) : Identifiable<Item>, Producible<ItemStack>, HoverEventSource<ShowItem> {

	constructor(source: Material) : this(material = source)

	constructor(itemStack: ItemStack) : this() {
		material = itemStack.type
		label = try { itemStack.itemMeta.displayName()?.legacyString ?: "" } catch (e: NullPointerException) { "" }
		size = itemStack.amount
		lore = itemStack.lore()?.joinToString("\n") { it.legacyString } ?: ""
		damage = if (itemStack.itemMeta is Damageable) {
			(itemStack.itemMeta as Damageable).damage
		} else 0
		modifications = enchantmentsToModifications(itemStack.enchantments).toMutableSet()
		flags = itemStack.itemFlags
		quirk = Quirk.empty // using itemMetaBase instead of quirks
		data = readItemDataStorage(itemStack).toMutableMap()
		itemMetaBase = itemStack.itemMeta
		this.identity =
			("${itemStack.itemMeta.persistentDataContainer.get(identityNamespace, PersistentDataType.STRING)}").let {
				if (it.isNotBlank()) {
					return@let it
				} else
					"${UUID.randomUUID()}"
			}

		// base skull quirk

		if (itemStack.type == PLAYER_HEAD || itemStack.type == PLAYER_WALL_HEAD) {
			val meta = itemStack.itemMeta as SkullMeta
			if (meta.owningPlayer != null) {
				quirk = Quirk.skull {
					owningPlayer = meta.owningPlayer
				}
			} else
				quirk = Quirk.empty
		} else
			quirk = Quirk.empty
	}

	val identityNamespace = NamespacedKey(system, "itemIdentity")

	var clickAction: ItemClickAction?
		get() = JetCache.registeredItemClickActions[this.identity]
		set(value) {
			if (value != null) {
				JetCache.registeredItemClickActions[this.identity] = value
			} else
				JetCache.registeredItemClickActions.remove(this.identity)
		}

	var interactAction: ItemInteractAction?
		get() = JetCache.registeredItemInteractActions[this.identity]
		set(value) {
			if (value != null) {
				JetCache.registeredItemInteractActions[this.identity] = value
			} else
				JetCache.registeredItemInteractActions.remove(this.identity)
		}

	val displayObject: Component
		get() = Component.text()
			.append(Component.text("[", NamedTextColor.WHITE))
			.append(
				(if (label.isNotBlank()) {
					Component.text(label, NamedTextColor.WHITE)
				} else
					Component.translatable(material.translationKey(), NamedTextColor.WHITE))
					.hoverEvent(this)
			)
			.append(Component.text("]", NamedTextColor.WHITE))
			.build()

	fun produceItemMeta() = if (!material.isAir) {
		(itemMetaBase ?: itemFactory.getItemMeta(material)).apply {

			label.let {
				if (it.isNotEmpty()) {
					@Suppress("RemoveSingleExpressionStringTemplate")
					displayName(Component.text("$it"))
				}
			}

			if (this@Item.lore.isNotBlank() && this@Item.lore.isNotEmpty())
				lore(this@Item.lore.lines().map {
					Component.text("$WHITE$it").asComponent()
				}.toList())

			if (flags.isNotEmpty())
				addItemFlags(*flags.toTypedArray())

		}
	} else null

	override fun produce(): ItemStack {
		@Suppress("DEPRECATION") var itemStack = ItemStack(material, size, damage.toShort())
		val itemMeta = produceItemMeta()

		if (itemMeta != null) {

			modificationsToEnchantments(modifications).forEach { (key, value) ->
				itemMeta.addEnchant(key, value, true)
			}

			if (postProperties.contains(NO_ENCHANTMENTS)) itemMeta.addItemFlags(HIDE_ENCHANTS)

			itemMeta.addItemFlags(*flags.toTypedArray())

			if (!postProperties.contains(NO_IDENTITY))
				itemMeta.persistentDataContainer.set(identityNamespace, PersistentDataType.STRING, this.identity)

			if (!postProperties.contains(NO_DATA) && data.isNotEmpty())
				itemMeta.persistentDataContainer.apply { itemStoreApplier() }

			if (postProperties.contains(BLANK_LABEL)) itemMeta.displayName(Component.text(" "))

			itemStack.itemMeta = itemMeta

		}

		itemStack = itemStack.apply(quirk.itemStackProcessing)

		if (postProperties.contains(BLANK_DATA)) itemStack.addItemFlags(*ItemFlag.values())

		return itemStack
	}

	fun spawn(location: Location) = location.world.dropItem(location, produce())

	// data processing

	val dataStorage: Map<String, Any>
		get() = data

	val dataKeys: Set<String>
		get() = data.keys

	val dataValues: Collection<Any>
		get() = data.values

	fun dataCompatiblityCheck(any: Any?, allowTransforming: Boolean = true): Boolean {
		return if (any != null) {
			when (any) {
				is Byte, is Short, is Int, is Long, is Float, is Double -> true
				is String -> true
				is ByteArray, is IntArray, is LongArray -> true
				is IntRange -> allowTransforming
				else -> false.debugLog("checker is flown; ${any.javaClass.name}")
			}
		} else
			false
	}

	fun dataContentType(any: Any): PersistentDataType<out Any, out Any> = when (any) {
		is Byte -> PersistentDataType.BYTE
		is Short -> PersistentDataType.SHORT
		is Int -> PersistentDataType.INTEGER
		is Long -> PersistentDataType.LONG
		is Float -> PersistentDataType.FLOAT
		is Double -> PersistentDataType.DOUBLE
		is String -> PersistentDataType.STRING
		is ByteArray -> PersistentDataType.BYTE_ARRAY
		is IntArray -> PersistentDataType.INTEGER_ARRAY
		is LongArray -> PersistentDataType.LONG_ARRAY
		else -> throw IllegalArgumentException("The data '$any' is not compatible with the data-cache")
	}

	fun dataGet(path: NamespacedKey) = dataGet(path.asString)

	fun dataGet(location: String) = data[location]

	fun dataPut(path: NamespacedKey, data: Any?, allowTransforming: Boolean = true) = apply {
		val transformedData = data.let {
			if (allowTransforming) {
				if (it is IntRange)
					return@let it.toSet().toIntArray()
			}

			return@let it
		}

		if (dataCompatiblityCheck(transformedData, allowTransforming)) {

			this.data[path.toString()] = data!!

			debugLog("saved data '$data' under '$path' in item '${this.identity}'!")

		} else
			throw IllegalArgumentException("The data '$transformedData' is not compatible with the data-cache")

	}

	fun itemDataStoragePut(
		vendor: App,
		path: String,
		data: Any?,
		allowTransforming: Boolean = true
	) =
		dataPut(NamespacedKey(vendor, path), data, allowTransforming)

	fun dataContains(path: NamespacedKey) = dataGet(path) != null

	fun dataContains(location: String) = dataGet(location) != null

	val itemStoreApplier: PersistentDataContainer.() -> Unit = {

		debugLog("producing persistentDataContainer for item '${this@Item.identity}' started... ")

		data.forEach { (key, value) ->

			fun <T : Any> run() {
				(dataContentType(value) to value).let { valueData ->
					set(
						NamespacedKey.fromString(key)!!,
						valueData.first.forceCast<PersistentDataType<T, T>>(),
						valueData.second.forceCast()
					)
					debugLog("|> produced $value into items-data container '${this@Item.identity}'")
				}
			}

			run<Any>()

		}

		debugLog("producing persistentDataContainer for item '${this@Item.identity}' succeed!")

	}

	// smart-modify functions

	fun annexModifications(vararg modifications: Modification) =
		apply { this.modifications.addAll(modifications) }

	fun annexModifications(modifications: Collection<Modification>) =
		apply { this.modifications.addAll(modifications) }

	fun annexModifications(vararg modifications: Pair<Enchantment, Int>) =
		apply { this.modifications.addAll(modifications.map { Modification(it.first, it.second) }) }

	fun putModifications(vararg modifications: Modification) =
		apply { this.modifications = modifications.toMutableSet() }

	fun putModifications(modifications: Collection<Modification>) =
		apply { this.modifications = modifications.toMutableSet() }

	fun putModifications(vararg modifications: Pair<Enchantment, Int>) =
		apply { this.modifications = modifications.map { Modification(it.first, it.second) }.toMutableSet() }

	fun hasModifications(vararg modifications: Modification) =
		this.modifications.containsAll(modifications.toSet())

	fun hasModifications(modifications: Collection<Modification>) =
		this.modifications.containsAll(modifications)

	fun hasModifications(vararg modifications: Pair<Enchantment, Int>) =
		this.modifications.containsAll(modifications.map { Modification(it.first, it.second) })

	fun dropModifications(vararg modifications: Modification) =
		apply { this.modifications.removeAll(modifications) }

	fun dropModifications(modifications: Collection<Modification>) =
		apply { this.modifications.removeAll(modifications) }



	fun annexFlags(flags: Collection<ItemFlag>) =
		apply { this.flags.addAll(flags) }

	fun annexFlags(vararg flags: ItemFlag) =
		apply { this.flags.addAll(flags) }

	fun hasFlags(flags: Collection<ItemFlag>) =
		this.flags.containsAll(flags)

	fun hasFlags(vararg flags: ItemFlag) =
		this.flags.containsAll(flags.toSet())

	fun putFlags(flags: Collection<ItemFlag>) =
		apply { this.flags = flags.toMutableSet() }

	fun putFlags(vararg flags: ItemFlag) =
		apply { this.flags = flags.toMutableSet() }

	fun dropFlags(flags: Collection<ItemFlag>) =
		apply { this.flags.removeAll(flags) }

	fun dropFlags(vararg flags: ItemFlag) =
		apply { this.flags.removeAll(flags) }



	fun annexPostProperties(postProperties: Collection<PostProperty>) =
		apply { this.postProperties.addAll(postProperties) }

	fun annexPostProperties(vararg postProperties: PostProperty) =
		apply { this.postProperties.addAll(postProperties) }

	fun hasPostProperties(postProperties: Collection<PostProperty>) =
		apply { this.postProperties.containsAll(postProperties) }

	fun hasPostProperties(vararg postProperties: PostProperty) =
		apply { this.postProperties.containsAll(postProperties.toSet()) }

	fun putPostProperties(postProperties: Collection<PostProperty>) =
		apply { this.postProperties = postProperties.toMutableSet() }

	fun putPostProperties(vararg postProperties: PostProperty) =
		apply { this.postProperties = postProperties.toMutableSet() }

	fun dropPostProperties(postProperties: Collection<PostProperty>) =
		apply { this.postProperties.removeAll(postProperties) }

	fun dropPostProperties(vararg postProperties: PostProperty) =
		apply { this.postProperties.removeAll(postProperties) }



	fun putClickAction(clickAction: ItemClickAction) =
		apply { this.clickAction = clickAction }

	fun putClickAction(async: Boolean = true, stop: Boolean = true, action: InventoryClickEvent.() -> Unit) =
		putClickAction(ItemClickAction(action, async, stop))

	fun hasClickAction() = clickAction != null

	fun dropClickAction() {
		clickAction = null
	}



	fun putInteractAction(interactAction: ItemInteractAction) =
		apply { this.interactAction = interactAction }

	fun putInteractAction(async: Boolean = true, stop: Boolean = true, action: PlayerInteractAtItemEvent.() -> Unit) =
		putInteractAction(ItemInteractAction(action, async, stop))

	fun hasInteractAction() = interactAction != null

	fun dropInteractAction() {
		interactAction = null
	}

	// stupid-modify functions

	fun putMaterial(material: Material) =
		apply { this.material = material }

	fun putLabel(label: String) =
		apply { this.label = label }

	fun putSize(size: Int) =
		apply { this.size = size }

	fun putLore(lore: String) =
		apply { this.lore = lore }

	fun putDamage(damage: Int) =
		apply { this.damage = damage }

	fun putQuirk(quirk: Quirk) =
		apply { this.quirk = quirk }

	fun putIdentity(identity: String) =
		apply { this.identity = identity }

	fun putBase(itemMetaBase: ItemMeta?) =
		apply { this.itemMetaBase = itemMetaBase }

	// additional-modify functions

	fun changeColor(newColorType: ColorType) =
		apply { putMaterial(material.changeColor(newColorType)) }

	fun hideItemData() =
		apply { postProperties.add(NO_DATA) }

	fun showItemData() =
		apply { postProperties.remove(NO_DATA) }

	fun blankLabel() =
		apply { postProperties.add(BLANK_LABEL) }

	fun filledLabel() =
		apply { postProperties.remove(BLANK_LABEL) }

	fun emptyLabel() =
		apply { label = "" }

	// computation

	val computedBlockData: BlockData
		get() = createBlockData(material)

	// compare

	operator fun compareTo(other: Item) = size - other.size

	// base functions

	override fun asHoverEvent(op: UnaryOperator<ShowItem>) = produce().asHoverEvent(op)

	fun isSame(
		other: Item,
		ignoreIdentity: Boolean = false,
		ignoreMaterial: Boolean = false,
		ignoreLabel: Boolean = false,
		ignoreSize: Boolean = false,
		ignoreDamage: Boolean = false,
		ignoreLore: Boolean = false,
		ignoreModifications: Boolean = false,
		ignoreFlags: Boolean = false,

		): Boolean {
		var isOtherItem = false

		if (!ignoreIdentity) {
			if (this.identity != other.identity) {
				isOtherItem = true
			}
		}

		if (!ignoreMaterial) {
			if (this.material != other.material) {
				isOtherItem = true
			}
		}

		if (!ignoreLabel) {
			if (this.label != other.label) {
				isOtherItem = true
			}
		}

		if (!ignoreSize) {
			if (this.size != other.size) {
				isOtherItem = true
			}
		}

		if (!ignoreDamage) {
			if (this.damage != other.damage) {
				isOtherItem = true
			}
		}

		if (!ignoreLore) {
			if (this.lore != other.lore) {
				isOtherItem = true
			}
		}

		if (!ignoreModifications) {
			if (this.modifications != other.modifications) {
				isOtherItem = true
			}
		}

		if (!ignoreFlags) {
			if (this.flags != other.flags) {
				isOtherItem = true
			}
		}

		return !isOtherItem
	}

	companion object {

		private fun enchantmentsToModifications(map: Map<Enchantment, Int>) = map.map {
			Modification(it.key, it.value)
		}

		private fun modificationsToEnchantments(modifications: Collection<Modification>) = modifications.associate {
			it.enchantment to it.level
		}

		private fun readItemDataStorage(itemStack: ItemStack): Map<String, Any> = mutableMapOf<String, Any>().apply {
			val persistentDataContainer = itemStack.itemMeta.persistentDataContainer
			val persistentDataTypes = setOf(
				PersistentDataType.BYTE,
				PersistentDataType.SHORT,
				PersistentDataType.INTEGER,
				PersistentDataType.LONG,
				PersistentDataType.FLOAT,
				PersistentDataType.DOUBLE,
				PersistentDataType.STRING,
				PersistentDataType.BYTE_ARRAY,
				PersistentDataType.INTEGER_ARRAY,
				PersistentDataType.LONG_ARRAY,
				//PersistentDataType.TAG_CONTAINER_ARRAY, not supported
				//PersistentDataType.TAG_CONTAINER, not supported
			)

			persistentDataContainer.keys.forEach {
				persistentDataTypes.forEach { type ->
					try {
						persistentDataContainer[it, type]?.let { it1 -> put(it.asString, it1) }
					} catch (e: NullPointerException) {
						throw NoSuchElementException("Element '$it' is not contained!")
					} catch (ignore: IllegalArgumentException) {
					}
				}
			}
		}

	}

}