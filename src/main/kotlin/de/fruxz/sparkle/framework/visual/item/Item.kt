package de.fruxz.sparkle.framework.visual.item

import de.fruxz.ascend.extension.container.takeOrEmpty
import de.fruxz.ascend.extension.data.buildRandomTag
import de.fruxz.ascend.extension.forceCast
import de.fruxz.ascend.extension.objects.takeIfInstance
import de.fruxz.ascend.tool.smart.composition.Producible
import de.fruxz.sparkle.framework.event.interact.PlayerInteractAtItemEvent
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.extension.persistentData
import de.fruxz.sparkle.framework.extension.sparkle
import de.fruxz.sparkle.framework.extension.subNamespacedKey
import de.fruxz.sparkle.framework.extension.visual.ui.changeColor
import de.fruxz.sparkle.framework.extension.visual.ui.itemMetaOrNull
import de.fruxz.sparkle.framework.identification.KeyedIdentifiable
import de.fruxz.sparkle.framework.visual.color.ColorType
import de.fruxz.sparkle.framework.visual.item.PostProperty.*
import de.fruxz.sparkle.framework.visual.item.action.ItemAction
import de.fruxz.sparkle.framework.visual.item.action.ItemActionTag
import de.fruxz.sparkle.framework.visual.item.action.ItemClickAction
import de.fruxz.sparkle.framework.visual.item.action.ItemDropAction
import de.fruxz.sparkle.framework.visual.item.action.ItemInteractAction
import de.fruxz.sparkle.framework.visual.item.quirk.Quirk
import de.fruxz.sparkle.server.SparkleApp
import de.fruxz.stacked.extension.KEY_REGEX
import de.fruxz.stacked.extension.KeyingStrategy.CONTINUE
import de.fruxz.stacked.extension.asComponent
import de.fruxz.stacked.extension.asStyledComponent
import de.fruxz.stacked.extension.asStyledString
import de.fruxz.stacked.extension.isNotBlank
import de.fruxz.stacked.extension.isNotEmpty
import de.fruxz.stacked.extension.lines
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent.ShowItem
import net.kyori.adventure.text.event.HoverEventSource
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration.ITALIC
import net.kyori.adventure.text.format.TextDecoration.State.FALSE
import org.bukkit.Bukkit
import org.bukkit.Bukkit.createBlockData
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Material.*
import org.bukkit.NamespacedKey
import org.bukkit.block.data.BlockData
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.persistence.PersistentDataType
import java.util.*
import java.util.function.UnaryOperator
import kotlin.time.Duration.Companion.seconds

@Suppress("unused", "MemberVisibilityCanBePrivate")
data class Item(
	var material: Material = STONE,
	var label: Component = Component.empty(),
	var size: Int = 1,
	var lore: List<Component> = emptyList(),
	var damage: Int = 0,
	var modifications: Set<Modification> = setOf(),
	var flags: HashSet<ItemFlag> = hashSetOf(),
	var quirk: Quirk = Quirk.empty,
	var postProperties: HashSet<PostProperty> = hashSetOf(),
	var itemIdentity: String = "${UUID.randomUUID()}",
	var persistentData: Map<Key, Any> = mapOf(),
	var itemMetaBase: ItemMeta? = null,
	var itemActionTags: Set<ItemActionTag> = emptySet(),
	var productionPlugins: Set<(ItemStack) -> Unit> = setOf(),
) : ItemLike, KeyedIdentifiable<Item>, Producible<ItemStack>, HoverEventSource<ShowItem> {

	constructor(source: Material) : this(material = source)

	constructor(itemStack: ItemStack) : this(
		material = itemStack.type,
		label = itemStack.itemMeta?.displayName() ?: Component.empty(),
		size = itemStack.amount,
		lore = itemStack.lore().takeOrEmpty(),
		damage = itemStack.itemMeta?.takeIfInstance<Damageable>()?.damage ?: 0,
		modifications = enchantmentsToModifications(itemStack.enchantments),
		flags = itemStack.itemFlags.toHashSet(),
		quirk = when (itemStack.type) {
			PLAYER_HEAD, PLAYER_WALL_HEAD -> Quirk.skull { owningPlayer = itemStack.itemMetaOrNull?.takeIfInstance<SkullMeta>()?.owningPlayer }
			else -> Quirk.empty
		},
		persistentData = itemStack.itemMetaOrNull?.persistentData ?: emptyMap(),
		itemMetaBase = itemStack.itemMeta,
		itemActionTags = itemStack.takeIf { it.hasItemMeta() }?.itemMeta?.persistentDataContainer?.getOrDefault(
			actionsNamespace, PersistentDataType.STRING, "")?.split("|")?.map { ItemActionTag(it) }?.toSet() ?: emptySet(),
		itemIdentity = (itemStack.itemMeta?.persistentDataContainer?.get(identityNamespace, PersistentDataType.STRING) ?: "").let {
			when {
				it.isNotBlank() -> return@let it
				else -> "${UUID.randomUUID()}"
			}
		},
	)

	override val identityKey: Key
		get() = Key.key(SparkleApp.Infrastructure.SYSTEM_IDENTITY + "_items", itemIdentity.lowercase(Locale.ENGLISH).filter { KEY_REGEX.matches("$it") })

	val displayObject: Component
		get() = Component.text()
			.append(Component.text("[", NamedTextColor.WHITE))
			.append(
				(if (label.asStyledString.isNotBlank()) {
					label.color(NamedTextColor.WHITE)
				} else
					Component.translatable(material.translationKey(), NamedTextColor.WHITE))
					.hoverEvent(this)
			)
			.append(Component.text("]", NamedTextColor.WHITE))
			.build()

	fun produceItemMeta() = if (!material.isAir) {
		(itemMetaBase ?: Bukkit.getItemFactory().getItemMeta(material)).apply {

			label.let {
				if (it.isNotEmpty) {
					displayName(Component.text().decoration(ITALIC, FALSE).append(it).build())
				}
			}

			if (this@Item.lore.isNotEmpty() && this@Item.lore.any { it.isNotBlank } )
				lore(this@Item.lore.map { Component.text().decoration(ITALIC, FALSE).append(it).build() })

			if (flags.isNotEmpty())
				addItemFlags(*flags.toTypedArray())

		}
	} else null

	fun produceJson() = JsonItemStack.toJson(produce())

	override fun asItem(): Item = copy()

	override fun asItemStack(): ItemStack = produce()

	override fun produce(): ItemStack = runBlocking {
		val itemMeta = produceItemMeta()

		withContext(SparkleApp.coroutineScope.coroutineContext) {
			@Suppress("DEPRECATION") var itemStack = ItemStack(material, size, damage.toShort())
			var productionData = mapOf<Pair<NamespacedKey, PersistentDataType<*, *>>, Any>()

			if (itemMeta != null) {

				modificationsToEnchantments(modifications).forEach { (key, value) ->
					itemMeta.addEnchant(key, value, true)
				}

				if (postProperties.contains(NO_ENCHANTMENTS)) itemMeta.addItemFlags(HIDE_ENCHANTS)

				itemMeta.addItemFlags(*flags.toTypedArray())

				if (!postProperties.contains(NO_IDENTITY))
					productionData += (identityNamespace to PersistentDataType.STRING) to this@Item.identity

				if (postProperties.contains(BLANK_LABEL)) itemMeta.displayName(Component.text(" "))

				if (itemActionTags.isNotEmpty()) productionData += (actionsNamespace to PersistentDataType.STRING) to (itemActionTags.joinToString("|") { it.identity })

				fun <I, O : Any> place(
					namespacedKey: NamespacedKey,
					persistentDataType: PersistentDataType<I, O>,
					value: Any
				) {
					runBlocking {
						try {
							itemMeta.persistentDataContainer.set(namespacedKey, persistentDataType, value.forceCast())
						} catch (e: ConcurrentModificationException) {
							debugLog("Saving data '$value' under '$namespacedKey' in item '$identity' failed, (PRODUCING) retrying in 0.1 seconds...")
							delay(.1.seconds)
							place(namespacedKey, persistentDataType, value)
						}
					}
				}

				productionData.forEach { (key, value) ->
					place(key.first, key.second, value)
				}

				if (!postProperties.contains(NO_DATA) && persistentData.isNotEmpty())
					itemMeta.persistentData = persistentData

				itemStack.itemMeta = itemMeta

			}

			itemStack = itemStack.apply(quirk.itemStackProcessing)

			if (postProperties.contains(BLANK_DATA)) itemStack.addItemFlags(*ItemFlag.values())

			productionPlugins.forEach {
				itemStack = itemStack.apply(it)
			}

			itemStack
		}
	}

	@ItemDsl
	fun spawn(location: Location) = location.world.dropItem(location, produce())

	// persistentData

	fun setPersistent(key: Key, value: Any) =
		apply { persistentData += key to value }

	fun setPersistent(name: String, value: Any) =
		setPersistent(Key.key(name), value)

	fun <T> getPersistent(key: Key): T? =
		persistentData[key]?.forceCast()

	fun <T> getPersistent(name: String): T? =
		getPersistent(Key.key(name))

	// smart-modify functions

	@ItemDsl
	fun annexModifications(vararg modifications: Modification) =
		apply { this.modifications += modifications }

	@ItemDsl
	fun annexModifications(modifications: Collection<Modification>) =
		apply { this.modifications += modifications }

	@ItemDsl
	fun annexModifications(vararg modifications: Pair<Enchantment, Int>) =
		apply { this.modifications += modifications.map { Modification(it.first, it.second) } }

	@ItemDsl
	fun putModifications(vararg modifications: Modification) =
		apply { this.modifications = modifications.toSet() }

	@ItemDsl
	fun putModifications(modifications: Collection<Modification>) =
		apply { this.modifications = modifications.toSet() }

	@ItemDsl
	fun putModifications(vararg modifications: Pair<Enchantment, Int>) =
		apply { this.modifications = modifications.map { Modification(it.first, it.second) }.toSet() }

	@ItemDsl
	fun hasModifications(vararg modifications: Modification) =
		this.modifications.containsAll(modifications.toSet())

	@ItemDsl
	fun hasModifications(modifications: Collection<Modification>) =
		this.modifications.containsAll(modifications)

	@ItemDsl
	fun hasModifications(vararg modifications: Pair<Enchantment, Int>) =
		this.modifications.containsAll(modifications.map { Modification(it.first, it.second) })

	@ItemDsl
	fun dropModifications(vararg modifications: Modification) =
		apply { this.modifications -= modifications.toSet() }

	@ItemDsl
	fun dropModifications(modifications: Collection<Modification>) =
		apply { this.modifications -= modifications.toSet() }

	@ItemDsl
	fun annexFlags(flags: Collection<ItemFlag>) =
		apply { this.flags.addAll(flags) }

	@ItemDsl
	fun annexFlags(vararg flags: ItemFlag) =
		apply { this.flags.addAll(flags) }

	@ItemDsl
	fun hasFlags(flags: Collection<ItemFlag>) =
		this.flags.containsAll(flags)

	@ItemDsl
	fun hasFlags(vararg flags: ItemFlag) =
		this.flags.containsAll(flags.toSet())

	@ItemDsl
	fun putFlags(flags: Collection<ItemFlag>) =
		apply { this.flags = flags.toHashSet() }

	@ItemDsl
	fun putFlags(vararg flags: ItemFlag) =
		apply { this.flags = flags.toHashSet() }

	@ItemDsl
	fun dropFlags(flags: Collection<ItemFlag>) =
		apply { this.flags.removeAll(flags.toSet()) }

	@ItemDsl
	fun dropFlags(vararg flags: ItemFlag) =
		apply { this.flags.removeAll(flags.toSet()) }

	@ItemDsl
	fun annexPostProperties(postProperties: Collection<PostProperty>) =
		apply { this.postProperties.addAll(postProperties) }

	@ItemDsl
	fun annexPostProperties(vararg postProperties: PostProperty) =
		apply { this.postProperties.addAll(postProperties) }

	@ItemDsl
	fun hasPostProperties(postProperties: Collection<PostProperty>) =
		apply { this.postProperties.containsAll(postProperties) }

	@ItemDsl
	fun hasPostProperties(vararg postProperties: PostProperty) =
		apply { this.postProperties.containsAll(postProperties.toSet()) }

	@ItemDsl
	fun putPostProperties(postProperties: Collection<PostProperty>) =
		apply { this.postProperties = postProperties.toHashSet() }

	@ItemDsl
	fun putPostProperties(vararg postProperties: PostProperty) =
		apply { this.postProperties = postProperties.toHashSet() }

	@ItemDsl
	fun dropPostProperties(postProperties: Collection<PostProperty>) =
		apply { this.postProperties.removeAll(postProperties.toSet()) }

	@ItemDsl
	fun dropPostProperties(vararg postProperties: PostProperty) =
		apply { this.postProperties.removeAll(postProperties.toSet()) }

	@ItemDsl
	fun onItemClick(identity: String = "click_${buildRandomTag()}_${this.identity}", process: (InventoryClickEvent) -> Unit) =
		attachItemActions(ItemClickAction(identity, executionProcess = process).also { it.register() })

	@ItemDsl
	fun onItemClickWith(identity: String = "click_${buildRandomTag()}_${this.identity}", process: InventoryClickEvent.() -> Unit) =
		onItemClick(identity, process)

	@ItemDsl
	fun onItemInteract(identity: String = "interact_${buildRandomTag()}_${this.identity}", process: (PlayerInteractAtItemEvent) -> Unit) =
		attachItemActions(ItemInteractAction(identity, executionProcess = process).also { it.register() })

	@ItemDsl
	fun onItemInteractWith(identity: String = "interact_${buildRandomTag()}_${this.identity}", process: PlayerInteractAtItemEvent.() -> Unit) =
		onItemInteract(identity, process)

	@ItemDsl
	fun onItemDrop(identity: String = "click_${buildRandomTag()}_${this.identity}", process: (PlayerDropItemEvent) -> Unit) =
		attachItemActions(ItemDropAction(identity, executionProcess = process).also { it.register() })

	@ItemDsl
	fun onDropWith(identity: String = "click_${buildRandomTag()}_${this.identity}", process: PlayerDropItemEvent.() -> Unit) =
		onItemDrop(identity, process)

	@ItemDsl
	fun attachItemActions(vararg itemActionTags: ItemActionTag) = apply {
		this.itemActionTags += itemActionTags
	}

	@ItemDsl
	fun attachItemActions(vararg itemActions: ItemAction<*>) =
		attachItemActions(itemActionTags = itemActions.map { it.registrationTag }.toTypedArray())

	// dsl-modify functions

	@ItemDsl
	fun material(material: Material) =
		apply { this.material = material }

	@ItemDsl
	fun label(styledString: String) =
		apply { this.label = styledString.asStyledComponent }

	@ItemDsl
	fun label(label: Component) =
		apply { this.label = label }

	@ItemDsl
	fun size(size: Int) =
		apply { this.size = size }

	@ItemDsl
	fun lore(lore: String, styled: Boolean = false) =
		apply { this.lore = listOf(if (styled) lore.asStyledComponent else lore.asComponent) }

	@ItemDsl
	fun lore(lore: List<String>, styled: Boolean = false) =
		apply { this.lore = lore.map { if (styled) it.asStyledComponent else it.asComponent } }

	@ItemDsl
	fun lore(lore: Component) =
		apply { this.lore = lore.lines }

	@ItemDsl
	@JvmName("putLoreComponents")
	fun lore(lore: List<Component>) =
		apply { this.lore = lore }

	@ItemDsl
	fun damage(damage: Int) =
		apply { this.damage = damage }

	@ItemDsl
	fun quirk(quirk: Quirk) =
		apply { this.quirk = quirk }

	@ItemDsl
	fun identity(itemIdentity: String) =
		apply { this.itemIdentity = itemIdentity }

	@ItemDsl
	fun metaBase(itemMetaBase: ItemMeta?) =
		apply { this.itemMetaBase = itemMetaBase }

	// additional-modify functions

	fun changeColor(newColorType: ColorType) =
		apply { material(material.changeColor(newColorType)) }

	fun hideItemData() =
		apply { postProperties.add(NO_DATA) }

	fun showItemData() =
		apply { postProperties.remove(NO_DATA) }

	fun blankLabel() =
		apply { postProperties.add(BLANK_LABEL) }

	fun filledLabel() =
		apply { postProperties.remove(BLANK_LABEL) }

	fun emptyLabel() =
		apply { label = Component.empty() }

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
		ignoreActionTags: Boolean = false,

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

		if (!ignoreActionTags) {
			if (this.itemActionTags != other.itemActionTags) {
				isOtherItem = true
			}
		}

		return !isOtherItem
	}

	companion object {

		val identityNamespace = sparkle.subNamespacedKey("item.identity", CONTINUE)

		val actionsNamespace = sparkle.subNamespacedKey("item.actions", CONTINUE)

		@JvmStatic
		fun produceByJson(json: String) = JsonItemStack.fromJson(json)?.let { Item(it) }

		private fun enchantmentsToModifications(map: Map<Enchantment, Int>) = map.map {
			Modification(it.key, it.value)
		}.toSet()

		private fun modificationsToEnchantments(modifications: Collection<Modification>) = modifications.associate {
			it.enchantment to it.level
		}

	}

	@DslMarker
	@MustBeDocumented
	annotation class ItemDsl

}