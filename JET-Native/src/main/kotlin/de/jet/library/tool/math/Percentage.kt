package de.jet.library.tool.math

import kotlin.math.roundToInt

data class Percentage(
    val decimal: Double,
) {

    constructor(percentage: Int) : this(percentage.toDouble() / 100)

    constructor(percentage: Long) : this(percentage.toDouble() / 100)

    constructor(percentage: Short) : this(percentage.toDouble() / 100)

    constructor(percentage: Byte) : this(percentage.toDouble() / 100)

    val percentage: Double
        get() = (decimal * 100)

    fun displayString(filled: CharSequence, empty: CharSequence, displaySize: Int): String {
        val filledLength = (decimal * displaySize).roundToInt()
        val emptyLength = displaySize - filledLength
        return filled.toString().repeat(filledLength) + empty.toString().repeat(emptyLength)
    }

    fun displayPercentageString(filled: CharSequence, empty: CharSequence, displaySize: Int, percentPrefix: String = " ", percentSuffix: String = "%") =
        "${displayString(filled, empty, displaySize)}$percentPrefix$percentage$percentSuffix"

    fun percentageString(): String {
        return "$percentage%"
    }

    override fun toString(): String {
        return "$percentage%"
    }

}
