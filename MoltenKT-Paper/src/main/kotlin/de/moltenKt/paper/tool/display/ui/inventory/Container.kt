package de.moltenKt.paper.tool.display.ui.inventory

import de.moltenKt.jvm.extension.dump
import de.moltenKt.paper.extension.display.GRAY
import de.moltenKt.paper.extension.display.ui.item
import de.moltenKt.paper.extension.mainLog
import de.moltenKt.paper.extension.paper.createInventory
import de.moltenKt.paper.extension.tasky.sync
import de.moltenKt.paper.tool.display.color.ColorType
import de.moltenKt.paper.tool.display.item.Item
import de.moltenKt.paper.tool.display.ui.UI
import de.moltenKt.paper.tool.effect.sound.SoundMelody
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.Material.AIR
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.logging.Level
import kotlin.math.roundToInt

open class Container<T : Container<T>>(
	open var content: MutableMap<Int, Item> = mutableMapOf(),
	open var label: Component = Component.text("${GRAY}Container"),
	var size: Int = 9 * 3,
	open var theme: ColorType = ColorType.GRAY,
	open var openSound: SoundMelody? = null,
	override var identity: String = "${UUID.randomUUID()}",
) : UI<T>, Cloneable {

	open val rawInventory: Inventory
		get() {
			val inventory = createInventory(null, size, label)

			content.forEach { (key, value) ->
				if (key < inventory.size) {
					inventory.setItem(key, value.produce())
				} else
					mainLog(Level.WARNING,"Failed to produce item: $value to slot $key because it is higher that the size-content max ${inventory.size}!")
			}

			return inventory
		}

	val borderSlots: Array<Int>
		get() {

			val fullBorders = size >= 9 * 2
			val sideRows = if (fullBorders) {
				(size.toDouble() / 9.0).let {
					if (it >= 3) {
						it.roundToInt()
					} else
						0
				}
			} else
				0
			val sideSlots = mutableListOf<Int>()

			if (sideRows > 0) {
				for ((index, _) in (1..(sideRows - (if (fullBorders) 2 else 0))).withIndex()) {
					sideSlots.addAll(setOf(9 + (index * 9), 17 + (index * 9)))
				}
			}

			return emptyArray<Int>() + (0..8).toList() + (size - 9 until size).toList() + sideSlots

		}

	/**
	 * This computational value is the last usable slot
	 * of this [Container].
	 * It is calculated by the size of the [Container].
	 * @author Fruxz
	 * @since 1.0
	 */
	val lastSlot: Int
		get() = size - 1

	/**
	 * This computational value is every usable slot
	 * of this [Container].
	 * It is calculated by the size of the [Container].
	 * @author Fruxz
	 * @since 1.0
	 */
	val slots: IntRange
		get() = firstSlot .. lastSlot

	/**
	 * This value is the last usable slot
	 * (Hint: it's always 0)
	 * @author Fruxz
	 * @since 1.0
	 */
	val firstSlot = 0

	override fun display(receiver: Player) = display(humanEntity = receiver)

	override fun display(humanEntity: HumanEntity) = sync {
		humanEntity.openInventory(rawInventory)
	}.dump()

	override fun display(receiver: Player, specificParameters: Map<String, Any>) =
		display(receiver)

	override fun display(humanEntity: HumanEntity, specificParameters: Map<String, Any>) =
		display(humanEntity)

	fun place(slot: Int, item: Item) {
		content[slot] = item
	}

	fun place(slot: Int, stack: ItemStack) =
		place(slot = slot, item = Item(stack))

	fun place(slot: Int, material: Material) =
		place(slot = slot, item = Item(material))

	fun place(rangeSlots: IntRange, item: Item) {
		rangeSlots.forEach { slot ->
			place(slot = slot, item = item)
		}
	}

	fun place(rangeSlots: IntRange, itemStack: ItemStack) {
		rangeSlots.forEach {
			place(it, itemStack)
		}
	}

	fun place(rangeSlots: IntRange, material: Material) {
		rangeSlots.forEach {
			place(it, material)
		}
	}

	fun place(arraySlots: Array<Int>, item: Item) {
		arraySlots.forEach {
			place(it, item)
		}
	}

	fun place(arraySlots: Array<Int>, itemStack: ItemStack) {
		arraySlots.forEach {
			place(it, itemStack)
		}
	}

	fun place(arraySlots: Array<Int>, material: Material) {
		arraySlots.forEach {
			place(it, material)
		}
	}

	fun place(arraySlots: Collection<Int>, item: Item) {
		arraySlots.forEach {
			place(it, item)
		}
	}

	fun place(arraySlots: Collection<Int>, itemStack: ItemStack) {
		arraySlots.forEach {
			place(it, itemStack)
		}
	}

	fun place(arraySlots: Collection<Int>, material: Material) {
		arraySlots.forEach {
			place(it, material)
		}
	}

	fun place(listSlots: List<Int>, item: Item) {
		listSlots.forEach {
			place(it, item)
		}
	}

	fun place(listSlots: List<Int>, material: Material) {
		listSlots.forEach {
			place(it, material)
		}
	}

	fun place(slotToItemMap: Map<Int, Item>) {
		slotToItemMap.forEach { (key, value) ->
			place(key, value)
		}
	}

	fun placeOver(slotToItemMap: Map<Int, Item>) {
		slotToItemMap.forEach { (key, value) ->
			content[key] = value
		}
	}

	fun replace(oldMaterial: Material, newMaterial: Material) {
		val out = HashMap<Int, Item>()

		content.forEach { (key, value) ->
			if (value.material != oldMaterial) {

				val clone = value.copy()

				clone.material = newMaterial

				out[key] = value

			} else
				out[key] = value
		}

		content = out
	}

	fun replace(oldItem: Item, newItem: Item) {
		val out = HashMap<Int, Item>()

		content.forEach { (key, value) ->
			out[key] = if (value.isSame(oldItem)) newItem else value
		}

		content = out
	}

	fun replace(oldMaterial: Material, newItem: Item) {
		val out = HashMap<Int, Item>()

		content.forEach { (key, value) ->
			out[key] = if (value.isSame(
					other = Item(oldMaterial),
					ignoreLabel = true,
					ignoreSize = true,
					ignoreDamage = true,
					ignoreLore = true,
					ignoreModifications = true,
					ignoreMaterial = false
				)
			) newItem else value
		}

		content = out
	}

	fun replace(oldItem: Item, newMaterial: Material) {
		val out = HashMap<Int, Item>()

		content.forEach { (key, value) ->
			out[key] = if (oldItem.isSame(
					other = Item(newMaterial),
					ignoreLabel = true,
					ignoreSize = true,
					ignoreDamage = true,
					ignoreLore = true,
					ignoreModifications = true,
					ignoreMaterial = false
				)
			) Item(newMaterial) else value
		}

		content = out
	}

	fun background(item: Item, replaceAir: Boolean = true) {
		slots.forEach {
			val content = content[it]?.material

			if (content == null || (replaceAir && content == AIR)) {
				place(it, item)
			}

		}
	}

	fun background(material: Material, replaceAir: Boolean = true) = background(Item(material), replaceAir)

	fun background(itemStack: ItemStack, replaceAir: Boolean = true) = background(itemStack.item, replaceAir)

	fun fill(vararg items: Item) {
		(0 until size).forEach { slot ->
			content[slot] = items.random()
		}
	}

	fun fill(vararg materials: Material) = fill(*materials.map { Item(it) }.toTypedArray())

	fun border(item: Item, schemaArray: Array<Int>) {
		schemaArray.forEach { position ->
			content[position] = item
		}
	}

	fun border(material: Material, schemaArray: Array<Int>) = border(item = Item(material), schemaArray = schemaArray)

	fun border(item: Item) {

		border(item, borderSlots)

	}

	fun border(material: Material) = border(item = material.item)

	fun placeOver(container: Container<*>) = placeOver(container.content)

	fun placeOver(vararg items: Pair<Int, Item>) = placeOver(mapOf(*items))

	operator fun set(slot: Int, item: Item) = place(slot, item)

	operator fun set(slot: Int, itemStack: ItemStack) = place(slot, itemStack)

	operator fun set(slot: Int, material: Material) = place(slot, material)

	operator fun set(slotRange: IntRange, item: Item) = place(slotRange, item)

	operator fun set(slotRange: IntRange, itemStack: ItemStack) = place(slotRange, itemStack)

	operator fun set(slotRange: IntRange, material: Material) = place(slotRange, material)

	operator fun set(slotsArray: Array<Int>, item: Item) = place(slotsArray, item)

	operator fun set(slotsArray: Array<Int>, itemStack: ItemStack) = place(slotsArray, itemStack)

	operator fun set(slotsArray: Array<Int>, material: Material) = place(slotsArray, material)

	operator fun set(slotsCollection: Collection<Int>, item: Item) = place(slotsCollection, item)

	operator fun set(slotsCollection: Collection<Int>, itemStack: ItemStack) = place(slotsCollection, itemStack)

	operator fun set(slotsCollection: Collection<Int>, material: Material) = place(slotsCollection, material)

	operator fun set(vararg multipleSlots: Int, item: Item) = set(multipleSlots.toTypedArray(), item)

	operator fun set(vararg multipleSlots: Int, itemStack: ItemStack) = set(multipleSlots.toTypedArray(), itemStack)

	operator fun set(vararg multipleSlots: Int, material: Material) = this.set(slotsArray = multipleSlots.toTypedArray(), material = material)

	operator fun get(slot: Int) = content[slot]

	operator fun get(collection: Collection<Int>) = content.toList().filter { collection.contains(it.first) }.toMap().values

	operator fun get(array: Array<Int>) = this.get(collection = array.toList())

	operator fun get(intRange: IntRange) = get(collection = intRange.asIterable().toList())

	operator fun get(vararg slots: Int) = get(slots.toTypedArray())

}