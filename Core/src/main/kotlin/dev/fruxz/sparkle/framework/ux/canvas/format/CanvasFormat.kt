package dev.fruxz.sparkle.framework.ux.canvas.format

import dev.fruxz.sparkle.framework.ux.inventory.container.buildInventory
import net.kyori.adventure.text.ComponentLike
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

sealed interface CanvasFormat {

    fun generateInventory(
        label: ComponentLike,
        owner: InventoryHolder? = null,
    ): Inventory

    val virtualSize: Int

    val slots: IntRange
        get() = 0 until virtualSize

    companion object {

        fun ofSize(size: Int, lineSize: Int = 9) = Size(size, lineSize)

        fun ofLines(lines: Int, lineSize: Int = 9) = Size(lines * lineSize, lineSize)

        fun ofType(type: InventoryType, lineSize: Int = 9) = Type(type, lineSize)

    }

    data class Size(
        val size: Int,
        val lineSize: Int = 9,
    ) : CanvasFormat {

        init {
            assert(size % lineSize == 0) { "size has to be a multiple of lineSize!" }
        }

        override fun generateInventory(
            label: ComponentLike,
            owner: InventoryHolder?,
        ) = buildInventory(size, label, owner)

        override val virtualSize = size

    }

    data class Type(
        val type: InventoryType,
        val lineSize: Int = 9,
    ) : CanvasFormat {

        override fun generateInventory(
            label: ComponentLike,
            owner: InventoryHolder?,
        ) = buildInventory(type, label, owner)

        override val virtualSize = type.defaultSize

    }

}