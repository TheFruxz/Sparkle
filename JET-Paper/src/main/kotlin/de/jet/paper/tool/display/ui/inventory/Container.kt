package de.jet.paper.tool.display.ui.inventory

import de.jet.paper.extension.display.GRAY
import de.jet.paper.extension.display.ui.item
import de.jet.paper.extension.mainLog
import de.jet.paper.extension.paper.createInventory
import de.jet.paper.extension.tasky.sync
import de.jet.paper.tool.display.color.ColorType
import de.jet.paper.tool.display.item.Item
import de.jet.paper.tool.display.ui.UI
import de.jet.paper.tool.effect.sound.SoundMelody
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.logging.Level
import kotlin.math.roundToInt

open class Container(
	open var content: MutableMap<Int, Item> = mutableMapOf(),
	open var label: Component = Component.text("${GRAY}Container"),
	var size: Int = 9 * 3,
	open var theme: ColorType = ColorType.GRAY,
	open var openSound: SoundMelody? = null,
	override var identity: String = "${UUID.randomUUID()}",
) : UI, Cloneable {

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

	fun <T : Container> T.place(slot: Int, item: Item) = apply {
		content[slot] = item
	}

	fun <T : Container> T.place(slot: Int, stack: ItemStack) = place(slot = slot, item = Item(stack))

	fun <T : Container> T.place(slot: Int, material: Material) = place(slot = slot, item = Item(material))

	fun <T : Container> T.place(rangeSlots: IntRange, item: Item) = apply {
		rangeSlots.forEach {
			place(it, item)
		}
	}

	fun <T : Container> T.place(rangeSlots: IntRange, itemStack: ItemStack) = apply {
		rangeSlots.forEach {
			place(it, itemStack)
		}
	}

	fun <T : Container> T.place(rangeSlots: IntRange, material: Material) = apply {
		rangeSlots.forEach {
			place(it, material)
		}
	}

	fun <T : Container> T.place(arraySlots: Array<Int>, item: Item) = apply {
		arraySlots.forEach {
			place(it, item)
		}
	}

	fun <T : Container> T.place(arraySlots: Array<Int>, itemStack: ItemStack) = apply {
		arraySlots.forEach {
			place(it, itemStack)
		}
	}

	fun <T : Container> T.place(arraySlots: Array<Int>, material: Material) = apply {
		arraySlots.forEach {
			place(it, material)
		}
	}

	fun <T : Container> T.place(arraySlots: Collection<Int>, item: Item) = apply {
		arraySlots.forEach {
			place(it, item)
		}
	}

	fun <T : Container> T.place(arraySlots: Collection<Int>, itemStack: ItemStack) = apply {
		arraySlots.forEach {
			place(it, itemStack)
		}
	}

	fun <T : Container> T.place(arraySlots: Collection<Int>, material: Material) = apply {
		arraySlots.forEach {
			place(it, material)
		}
	}

	fun <T : Container> T.place(listSlots: List<Int>, item: Item) = apply {
		listSlots.forEach {
			place(it, item)
		}
	}

	fun <T : Container> T.place(listSlots: List<Int>, material: Material) = apply {
		listSlots.forEach {
			place(it, material)
		}
	}

	fun <T : Container> T.place(map: Map<Int, Item>) = apply {
		map.forEach { (key, value) ->
			place(key, value)
		}
	}

	fun <T : Container> T.placeOver(content: Map<Int, Item>) = apply {
		for ((key, value) in content) {
			this.content[key] = value
		}
	}

	fun <T : Container> T.replace(that: Material, withThat: Material) = apply {
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

	fun <T : Container> T.replace(that: Item, withThat: Item) = apply {
		val out = HashMap<Int, Item>()

		content.forEach { (key, value) ->
			out[key] = if (value.isSame(that)) withThat else value
		}

		content = out
	}

	fun <T : Container> T.replace(that: Material, withThat: Item) = apply {
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

	fun <T : Container> T.replace(that: Item, withThat: Material) = apply {
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

	fun <T : Container> T.background(item: Item) = replace(Material.AIR, item)

	fun <T : Container> T.background(material: Material) = background(Item(material))

	fun <T : Container> T.fill(vararg items: Item) = apply {
		(0 until size).forEach { slot ->
			content[slot] = items.random()
		}
	}

	fun <T : Container> T.fill(vararg materials: Material) = fill(*materials.map { Item(it) }.toTypedArray())

	fun <T : Container> T.border(item: Item, schema: Array<Int>) = apply {
		schema.forEach { position ->
			content[position] = item
		}
	}

	fun <T : Container> T.border(material: Material, schema: Array<Int>) = border(Item(material), schema)

	fun <T : Container> T.border(item: Item) = apply {
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

	fun <T : Container> T.border(material: Material) = border(item = material.item)

	fun <T : Container> T.placeOver(container: Container) = placeOver(container.content)

	fun <T : Container> T.placeOver(vararg items: Pair<Int, Item>) = placeOver(mapOf(*items))

	operator fun <T : Container> T.set(i: Int, value: Item) = place(i, value)

	operator fun <T : Container> T.set(i: Int, value: ItemStack) = place(i, value)

	operator fun <T : Container> T.set(i: Int, value: Material) = place(i, value)

	operator fun <T : Container> T.set(i: IntRange, value: Item) = place(i, value)

	operator fun <T : Container> T.set(i: IntRange, value: ItemStack) = place(i, value)

	operator fun <T : Container> T.set(i: IntRange, value: Material) = place(i, value)

	operator fun <T : Container> T.set(i: Array<Int>, value: Item) = place(i, value)

	operator fun <T : Container> T.set(i: Array<Int>, value: ItemStack) = place(i, value)

	operator fun <T : Container> T.set(i: Array<Int>, value: Material) = place(i, value)

	operator fun <T : Container> T.set(i: Collection<Int>, value: Item) = place(i, value)

	operator fun <T : Container> T.set(i: Collection<Int>, value: ItemStack) = place(i, value)

	operator fun <T : Container> T.set(i: Collection<Int>, value: Material) = place(i, value)

	operator fun <T : Container> T.set(vararg i: Int, value: Item) = set(i.toTypedArray(), value)

	operator fun <T : Container> T.set(vararg i: Int, value: ItemStack) = set(i.toTypedArray(), value)

	operator fun <T : Container> T.set(vararg i: Int, value: Material) = set(i.toTypedArray(), value)

	operator fun <T : Container> T.get(i: Int) = content[i]

	operator fun <T : Container> T.get(c: Collection<Int>) = content.toList().filter { c.contains(it.first) }.toMap().values

	operator fun <T : Container> T.get(a: Array<Int>) = get(a.toList())

	operator fun <T : Container> T.get(i: IntRange) = get(c = i.asIterable().toList())

	operator fun <T : Container> T.get(vararg i: Int) = get(i.toTypedArray())

}