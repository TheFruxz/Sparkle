package de.fruxz.sparkle.framework.visual.canvas

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryHolder

data class CanvasBase(
	val size: Int?,
	val type: InventoryType?,
	val lineSize: Int,
) {

	init { assert(size != null || type != null) { "size or type have to be non-null!" } }

	fun generateInventory(
		owner: InventoryHolder? = null,
		label: Component? = null,
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

	val slots = 0 until virtualSize

	private fun illegalState() = IllegalStateException("Both parameters of CanvasBase are null, which should not be possible!")

	companion object {

		fun ofSize(size: Int, lineSize: Int = 9) = CanvasBase(size, null, lineSize)

		fun ofLines(lines: Int, lineSize: Int = 9) = CanvasBase(lines * lineSize, null, lineSize)

		fun ofType(type: InventoryType, lineSize: Int = 9) = CanvasBase(null, type, lineSize)

	}

}
