package de.moltenKt.paper.tool.display.canvas.design

import de.moltenKt.paper.tool.display.canvas.MutableCanvas
import de.moltenKt.paper.tool.display.color.ColorType
import de.moltenKt.paper.tool.display.color.DyeableMaterial
import de.moltenKt.paper.tool.display.item.ItemLike
import kotlin.random.Random

/**
 * This interface represents an extendable canvas feature, that
 * automatically adapts to its environment and placement location.
 * View templates at [AdaptiveCanvasCompose.Companion]!
 * @see AdaptiveCanvasCompose.Companion
 * @author Fruxz
 * @since 1.0
 */
interface AdaptiveCanvasCompose {

	fun place(canvas: MutableCanvas, slots: Iterable<Int>)

	companion object {

		/**
		 * This function returns an [AdaptiveCanvasCompose] that is
		 * generating a rainbow-colored bunch of items.
		 * @param materialGroup defines, which items should be used, as the color representatives.
		 * @author Fruxz
		 * @since 1.0
		 */
		fun rainbow(materialGroup: DyeableMaterial) = object : AdaptiveCanvasCompose {

			override fun place(canvas: MutableCanvas, slots: Iterable<Int>) {
				slots.withIndex().forEach { (index, canvasSlot) ->
					canvas[canvasSlot] = materialGroup.withColor(ColorType.rainbow[index % ColorType.rainbow.size])
				}
			}

		}

		/**
		 * This function returns an [AdaptiveCanvasCompose] that is
		 * generating a bunch of items with randomized colors.
		 * @param materialGroup defines, which items should be used, as the color representatives.
		 * @author Fruxz
		 * @since 1.0
		 */
		fun random(materialGroup: DyeableMaterial, random: () -> Random = { Random }) = object : AdaptiveCanvasCompose {

			override fun place(canvas: MutableCanvas, slots: Iterable<Int>) {
				slots.forEach { canvasSlot ->
					canvas[canvasSlot] = materialGroup.withColor(ColorType.values().random(random.invoke()))
				}
			}

		}

		/**
		 * This function returns an [AdaptiveCanvasCompose] that is
		 * generating a bunch of items, but their item-stack-size is
		 * the current place in the order of the slots list.
		 * The first one is 1, so does not start with 0! -> (1..2..3..4..5..6)
		 * @author Fruxz
		 * @since 1.0
		 */
		fun amountCount(itemLike: ItemLike) = object : AdaptiveCanvasCompose {

			override fun place(canvas: MutableCanvas, slots: Iterable<Int>) {
				slots.withIndex().forEach { (index, value) ->
					canvas[value] = itemLike.asItemStack().apply {
						amount = index + 1
					}
				}
			}

		}

	}

}