package de.moltenKt.paper.tool.display.ui.canvas

import de.moltenKt.core.extension.math.floorToInt

class CanvasSize private constructor(
	val size: Int
) {

	val lines = floorToInt(size.toDouble() / 9)

	companion object {

		fun ofLines(lines: Int) = CanvasSize(lines * 9)

		fun ofSize(size: Int) = CanvasSize(size)

		val SMALL = ofLines(3)

		val MEDIUM = ofLines(5)

		val HUGE = ofLines(6)

	}

}
