package de.moltenKt.paper.tool.display.ui.canvas

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

		fun ofLines(lines: Int) = CanvasSize(lines * 9)

		fun ofSize(size: Int) = CanvasSize(size)

		val SMALL = ofLines(3)

		val MEDIUM = ofLines(5)

		val HUGE = ofLines(6)

	}

}
