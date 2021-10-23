package de.jet.minecraft.tool.display.ui.inventory

import de.jet.library.extension.paper.createInventory
import de.jet.minecraft.extension.display.GRAY
import de.jet.minecraft.extension.display.ui.item
import de.jet.minecraft.extension.tasky.sync
import de.jet.minecraft.tool.display.color.ColorType
import de.jet.minecraft.tool.display.item.Item
import de.jet.minecraft.tool.display.ui.UI
import de.jet.minecraft.tool.effect.sound.SoundMelody
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.math.roundToInt

open class Container(
	var content: MutableMap<Int, Item> = mutableMapOf(),
	open var label: Component = Component.text("${GRAY}Container"),
	var size: Int = 9*3,
	open var theme: ColorType = ColorType.GRAY,
	open var openSound: SoundMelody? = null,
	override var identity: String = "${UUID.randomUUID()}"
) : UI, Cloneable {

	fun replace(that: Material, withThat: Material) {
		val out = HashMap<Int, Item>()

		content.forEach { (key, value) ->
			if (value.material != that) {

				val clone = value.copy()

				clone.material = withThat

				out[key] = value

			} else
				out[key] = value
		}

		content = out
	}

	fun replace(that: Item, withThat: Item) {
		val out = HashMap<Int, Item>()

		content.forEach { (key, value) ->
			out[key] = if (value.isSame(that)) withThat else value
		}

		content = out
	}

	fun replace(that: Material, withThat: Item) {
		val out = HashMap<Int, Item>()

		content.forEach { (key, value) ->
			out[key] = if (value.isSame(
					other = Item(that),
					ignoreLabel = true,
					ignoreSize = true,
					ignoreDamage = true,
					ignoreLore = true,
					ignoreModifications = true,
					ignoreMaterial = false
				)
			) withThat else value
		}

		content = out
	}

	fun replace(that: Item, withThat: Material) {
		val out = HashMap<Int, Item>()

		content.forEach { (key, value) ->
			out[key] = if (that.isSame(
					other = Item(withThat),
					ignoreLabel = true,
					ignoreSize = true,
					ignoreDamage = true,
					ignoreLore = true,
					ignoreModifications = true,
					ignoreMaterial = false
				)
			) Item(withThat) else value
		}

		content = out
	}

	fun background(item: Item) = replace(Material.AIR, item)

	fun background(material: Material) = background(Item(material))

	fun fill(vararg items: Item) {
		(0 until size).forEach { slot ->
			content[slot] = items.random()
		}
	}

	fun fill(vararg materials: Material) = fill(*materials.map { Item(it) }.toTypedArray())

	fun border(item: Item, schema: Array<Int>) {
		schema.forEach { position ->
			content[position] = item
		}
	}

	fun border(material: Material, schema: Array<Int>) = border(Item(material), schema)

	fun border(item: Item) {
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

		border(item, emptyArray<Int>() + (0..8).toList() + (size - 9 until size).toList() + sideSlots)

	}

	fun border(material: Material) = border(item = material.item)

	val rawInventory: Inventory
		get() {
			val inventory = createInventory(null, size, label)

			content.forEach { (key, value) ->
				if (key < inventory.size) {
					inventory.setItem(key, value.produce())
				} else
					System.err.println("Failed to produce item: $value to slot $key because it is higher that the size-content max ${inventory.size}!")
			}

			return inventory
		}

	fun place(slot: Int, item: Item) {
		content[slot] = item
	}

	fun place(slot: Int, stack: ItemStack) = place(slot = slot, item = Item(stack))

	fun place(slot: Int, material: Material) = place(slot = slot, item = Item(material))

	fun place(rangeSlots: IntRange, item: Item) = rangeSlots.forEach {
		place(it, item)
	}

	fun place(rangeSlots: IntRange, itemStack: ItemStack) = rangeSlots.forEach {
		place(it, itemStack)
	}

	fun place(rangeSlots: IntRange, material: Material) = rangeSlots.forEach {
		place(it, material)
	}

	fun place(arraySlots: Array<Int>, item: Item) = arraySlots.forEach {
		place(it, item)
	}

	fun place(arraySlots: Array<Int>, itemStack: ItemStack) = arraySlots.forEach {
		place(it, itemStack)
	}

	fun place(arraySlots: Array<Int>, material: Material) = arraySlots.forEach {
		place(it, material)
	}

	fun place(arraySlots: Collection<Int>, item: Item) = arraySlots.forEach {
		place(it, item)
	}

	fun place(arraySlots: Collection<Int>, itemStack: ItemStack) = arraySlots.forEach {
		place(it, itemStack)
	}

	fun place(arraySlots: Collection<Int>, material: Material) = arraySlots.forEach {
		place(it, material)
	}

	fun place(listSlots: List<Int>, item: Item) = listSlots.forEach {
		place(it, item)
	}

	fun place(listSlots: List<Int>, material: Material) = listSlots.forEach {
		place(it, material)
	}

	fun place(map: Map<Int, Item>) = map.forEach { (key, value) ->
		place(key, value)
	}

	fun placeover(content: Map<Int, Item>) {

		for ((key, value) in content) {
			this.content[key] = value
		}

	}

	fun placeover(container: Container) = placeover(container.content)

	fun placeover(vararg items: Pair<Int, Item>) = placeover(mapOf(*items))

	operator fun set(i: Int, value: Item) = place(i, value)

	operator fun set(i: Int, value: ItemStack) = place(i, value)

	operator fun set(i: Int, value: Material) = place(i, value)

	operator fun set(i: IntRange, value: Item) = place(i, value)

	operator fun set(i: IntRange, value: ItemStack) = place(i, value)

	operator fun set(i: IntRange, value: Material) = place(i, value)

	operator fun set(i: Array<Int>, value: Item) = place(i, value)

	operator fun set(i: Array<Int>, value: ItemStack) = place(i, value)

	operator fun set(i: Array<Int>, value: Material) = place(i, value)

	operator fun set(i: Collection<Int>, value: Item) = place(i, value)

	operator fun set(i: Collection<Int>, value: ItemStack) = place(i, value)

	operator fun set(i: Collection<Int>, value: Material) = place(i, value)

	operator fun set(vararg i: Int, value: Item) = set(i.toTypedArray(), value)

	operator fun set(vararg i: Int, value: ItemStack) = set(i.toTypedArray(), value)

	operator fun set(vararg i: Int, value: Material) = set(i.toTypedArray(), value)

	operator fun get(i: Int) = content[i]

	operator fun get(c: Collection<Int>) = content.toList().filter { c.contains(it.first) }.toMap().values

	operator fun get(a: Array<Int>) = get(a.toList())

	operator fun get(i: IntRange) = get(c = i.asIterable().toList())

	operator fun get(vararg i: Int) = get(i.toTypedArray())

	override fun display(receiver: Player) = display(humanEntity = receiver)

	override fun display(humanEntity: HumanEntity) {
		sync {
			humanEntity.openInventory(rawInventory)
		}
	}

	override fun display(receiver: Player, specificParameters: Map<String, Any>) =
		display(receiver)

	override fun display(humanEntity: HumanEntity, specificParameters: Map<String, Any>) =
		display(humanEntity)

}