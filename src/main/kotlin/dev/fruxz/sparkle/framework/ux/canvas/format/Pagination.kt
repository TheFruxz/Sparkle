package dev.fruxz.sparkle.framework.ux.canvas.format

import dev.fruxz.ascend.extension.math.ceilToInt
import dev.fruxz.ascend.extension.math.decimalAsPercent
import dev.fruxz.ascend.extension.math.limitTo
import dev.fruxz.sparkle.framework.ux.canvas.Canvas
import dev.fruxz.sparkle.framework.ux.inventory.container.set
import dev.fruxz.sparkle.framework.ux.inventory.container.slots
import dev.fruxz.sparkle.framework.ux.inventory.item.itemStack
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import kotlin.math.max

interface Pagination<C> {

    val inventoryWidth: Int

    fun computeDisplaySlots(slots: IntRange): List<Int>

    fun preRenderInventory(canvas: Canvas, base: Inventory, offset: Int): InventoryRenderResult

    /**
     * @param content first(slots where to place) second(the content of the content-slots from the build of canvas)
     */
    data class InventoryRenderResult(
        val inventory: Inventory,
        val content: Map<Int, Int>,
    )

    companion object {

        fun scroll(inventoryWidth: Int) = object : Pagination<Unit> {
            override val inventoryWidth: Int = inventoryWidth

            override fun computeDisplaySlots(slots: IntRange): List<Int> =
                slots.filterNot { (it + 1) % inventoryWidth == 0 }

            override fun preRenderInventory(canvas: Canvas, base: Inventory, offset: Int): InventoryRenderResult {
                val inventoryLines = base.size / inventoryWidth

                val contentWidth = inventoryWidth - 1
                val contentSlots = computeDisplaySlots(base.slots)
                val scrollBarSlots = base.slots - contentSlots.toSet()
                val firstSlot = scrollBarSlots.first()
                val lastSlot = scrollBarSlots.last()
                val sliderSlots = scrollBarSlots - firstSlot - lastSlot

                val maxExpectedSlot = max(canvas.content.maxOfOrNull { it.key } ?: 0, canvas.lazyItems.maxOfOrNull { it.key } ?: 0)
                val maxExpectedLines = ceilToInt((maxExpectedSlot + 1.0) / contentWidth) // -1 because of the scroll bar, which is not a place for content
                val maxExpectedContentSlots = maxExpectedLines * contentWidth
                val requestedSlots = maxExpectedContentSlots + 1

                val viewUsage = (contentSlots.size.toDouble() / requestedSlots).limitTo(0.0..1.0) // 'max space' / 'requested space' limit to 0..1 = percentage of usage per view
                val scrollProgress = (offset.toDouble() / maxExpectedLines)

                base[scrollBarSlots] = Material.RED_STAINED_GLASS_PANE.itemStack

                println("scrollProgress: $scrollProgress")
                println("Percentage in view: ${viewUsage.decimalAsPercent.percentageString()}")

                return InventoryRenderResult(
                    base,
                    contentSlots.associateWith { it + (contentWidth * offset) },
                )
            }

        }

    }

}