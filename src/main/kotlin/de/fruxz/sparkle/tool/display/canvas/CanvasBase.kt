package de.fruxz.sparkle.tool.display.canvas

import de.fruxz.ascend.extension.math.floorToInt
import net.kyori.adventure.text.TextComponent
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryHolder
import kotlin.math.roundToInt

data class CanvasBase(
	val size: Int?,
	val type: InventoryType?,
) {

	init { assert(size != null || type != null) { "size or type have to be non-null!" } }

	fun generateInventory(
		owner: InventoryHolder? = null,
		label: TextComponent? = null,
	) = when {
		size != null -> {
			if (label != null) {
				Bukkit.createInventory(owner, size, label)
			} else
				Bukkit.createInventory(owner, size)
		}
		type != null -> {
			if (label != null) {
				Bukkit.createInventory(owner, type, label)
			} else
				Bukkit.createInventory(owner, type)
		}
		else -> throw illegalState()
	}

	// tracking values

	val virtualSize: Int = when {
		size != null -> size
		type != null -> type.defaultSize
		else -> throw illegalState()
	}

	val lines = floorToInt(virtualSize.toDouble() / 9)

	val slots = 0 until virtualSize

	val borderSlots by lazy {
		val sideRows = if (virtualSize >= 9 * 2) {
			(virtualSize.toDouble() / 9.0).let { lines ->
				lines.roundToInt().takeIf { lines >= 3 } ?: 0
			}
		} else
			0

		var sideSlots = setOf<Int>()

		if (sideRows > 0) {
			for ((index, _) in (1..(sideRows - 2)).withIndex()) {
				sideSlots += (setOf(9 + (index * 9), 17 + (index * 9)))
			}
		}

		(0..8).toSet() + (virtualSize - 9 until virtualSize).toSet() + sideSlots
	}

	private fun illegalState() = IllegalStateException("Both parameters of CanvasBase are null, which should not be possible!")

	companion object {

		fun ofSize(size: Int) = CanvasBase(size, null)

		fun ofLines(lines: Int) = CanvasBase(lines * 9, null)

		fun ofType(type: InventoryType) = CanvasBase(null, type)

	}

}
