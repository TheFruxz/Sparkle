package de.moltenKt.paper.tool.display.canvas

import de.moltenKt.core.extension.math.floorToInt
import kotlin.math.roundToInt

class CanvasSize private constructor(
	val size: Int
) {

	val lines = floorToInt(size.toDouble() / 9)

	val slots = 0 until size

	val borderSlots by lazy {
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

		val sideSlots = mutableSetOf<Int>()

		if (sideRows > 0) {
			for ((index, _) in (1..(sideRows - (if (fullBorders) 2 else 0))).withIndex()) {
				sideSlots.addAll(setOf(9 + (index * 9), 17 + (index * 9)))
			}
		}

		(0..8).toSet() + (size - 9 until size).toSet() + sideSlots
	}

	companion object {

		/**
		 * This function creates a canvas size with the given size in rows,
		 * this means that lines 2 is 18 slots.
		 * @param lines the amount of 9-slot lines.
		 * @author Fruxz
		 * @since 1.0
		 */
		fun ofLines(lines: Int) = CanvasSize(lines * 9)

		/**
		 * This function creates a canvas size with the given size in slots,
		 * this means that size 9 is 1 row.
		 * @param size the amount of slots.
		 * @author Fruxz
		 * @since 1.0
		 */
		fun ofSize(size: Int) = CanvasSize(size)

		/**
		 * The small canvas size contains 3 lines of 9 slots.
		 * @author Fruxz
		 * @since 1.0
		 */
		val SMALL = ofLines(3)

		/**
		 * The medium size contains 5 lines of 9 slots.
		 * @author Fruxz
		 * @since 1.0
		 */
		val MEDIUM = ofLines(5)

		/**
		 * The large size contains 6 lines of 9 slots.
		 * @author Fruxz
		 * @since 1.0
		 */
		val HUGE = ofLines(6)

	}

}
