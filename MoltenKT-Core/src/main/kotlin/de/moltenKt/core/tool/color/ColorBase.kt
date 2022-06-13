package de.moltenKt.core.tool.color

import de.moltenKt.core.tool.color.Color.ShiftType
import de.moltenKt.core.tool.color.Color.ShiftType.RELATIVE_TO_SPECTRUM
import de.moltenKt.core.tool.color.Color.ShiftType.RELATIVE_TO_TRANSITION
import de.moltenKt.core.tool.smart.identification.Identifiable
import java.awt.Color

interface ColorBase<T : ColorBase<T>> : Identifiable<T> {

	val red: Int
	val green: Int
	val blue: Int

	val rgb: Int

	val awtColor: Color

	val hexString: String

	val rgbString: String

	override val identity: String
		get() = hexString

	fun validate() {
		if (red !in 0..255) error("red must be in range of 0..255")
		if (green !in 0..255) error("green must be in range of 0..255")
		if (blue !in 0..255) error("blue must be in range of 0..255")
	}

	fun shiftTo(color: ColorBase<*>, opacity: Double, shiftType: ShiftType = RELATIVE_TO_SPECTRUM): T

	fun shiftTo(red: Int, green: Int, blue: Int, opacity: Double, shiftType: ShiftType = RELATIVE_TO_SPECTRUM): T

	fun splitShiftTo(destination: ColorBase<*>, parts: Int): List<T>

	fun brighter(strength: Double = .2, shiftType: ShiftType = RELATIVE_TO_TRANSITION): T

	fun darker(strength: Double = .2, shiftType: ShiftType = RELATIVE_TO_TRANSITION): T

	fun recreate(red: Int, green: Int, blue: Int): T

}