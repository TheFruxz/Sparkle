package dev.fruxz.sparkle.framework.ux.panel

import org.bukkit.event.inventory.InventoryType

sealed interface PanelFormat {

    companion object {

        fun ofSize(size: InventorySize) = SizePanelFormat(size)

        fun ofLines(lines: InventoryLines) = LinesPanelFormat(lines)

        fun ofType(type: InventoryType) = TypePanelFormat(type)

    }

    val slots: IntRange
        get() = when (this) {
            is SizePanelFormat -> 0 until size.size
            is LinesPanelFormat -> 0 until lines.lines * 9
            is TypePanelFormat -> 0 until type.defaultSize
        }

    @JvmInline
    value class InventoryLines(val lines: Int)

    @JvmInline
    value class InventorySize(val size: Int)

    @JvmInline
    value class TypePanelFormat(val type: InventoryType) : PanelFormat

    @JvmInline
    value class SizePanelFormat(val size: InventorySize) : PanelFormat

    @JvmInline
    value class LinesPanelFormat(val lines: InventoryLines) : PanelFormat

}