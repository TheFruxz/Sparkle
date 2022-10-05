package de.fruxz.sparkle.framework.visual.canvas

import de.fruxz.ascend.extension.math.floorToInt

fun mathDemo(slot: Int) = slot + (floorToInt(slot.toDouble() / 9)) // TODO <- possible design of the vertical scroll bar slot systematic

fun main() {
	val scrollState = 0
	val lines = 2
	val map = buildMap {
		repeat(100) {
			this[it] = it
		}
	}

	for (slotRequest in (8*scrollState)..(((8*lines)-1)+(8*scrollState))) {
		println(map[slotRequest])
	}

}