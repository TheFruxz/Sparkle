package de.jet.library.tool.math

import kotlin.math.roundToInt

/**
 * This data class represents a percentage value,
 * which can be used to calculate a value from a given base value.
 * @param decimal the percentage value in decimal notation *(0.2 = 20%)*
 * @author Fruxz
 * @since 1.0
 */
data class Percentage(
    val decimal: Double,
) {

    /**
     * Constructs a new [Percentage] object using the percentage directly.
     * @param percentage the percentage value (20 = 20%)
     * @return the new [Percentage] object
     * @author Fruxz
     * @since 1.0
     */
    constructor(percentage: Int) : this(percentage.toDouble() / 100)

    /**
     * Constructs a new [Percentage] object using the percentage directly.
     * @param percentage the percentage value (20 = 20%)
     * @return the new [Percentage] object
     * @author Fruxz
     * @since 1.0
     */
    constructor(percentage: Long) : this(percentage.toDouble() / 100)

    /**
     * Constructs a new [Percentage] object using the percentage directly.
     * @param percentage the percentage value (20 = 20%)
     * @return the new [Percentage] object
     * @author Fruxz
     * @since 1.0
     */
    constructor(percentage: Short) : this(percentage.toDouble() / 100)

    /**
     * Constructs a new [Percentage] object using the percentage directly.
     * @param percentage the percentage value (20 = 20%)
     * @return the new [Percentage] object
     * @author Fruxz
     * @since 1.0
     */
    constructor(percentage: Byte) : this(percentage.toDouble() / 100)

    /**
     * Computes the percentage value directly *(e.g.: 20%; 25.5%; ...)*
     * @author Fruxz
     * @since 1.0
     */
    val percentage: Double
        get() = (decimal * 100)

    /**
     * Computes a beautiful displayable percentage bar (e.g. 60% -> ||||||::::)
     * It takes [displaySize] as the size of the bar, [filled] as the filled part
     * and [empty] as the empty part. It appends the [filled] and [empty] to the
     * bar and returns it.
     * @param filled the symbols to use for the filled part
     * @param empty the symbols to use for the empty part
     * @param displaySize the size of the bar
     * @return the percentage bar
     * @author Fruxz
     * @since 1.0
     */
    fun displayString(filled: CharSequence, empty: CharSequence, displaySize: Int): String {
        val filledLength = (decimal * displaySize).roundToInt()
        val emptyLength = displaySize - filledLength
        return filled.toString().repeat(filledLength) + empty.toString().repeat(emptyLength)
    }

    /**
     * Computes a beautiful displayable percentage bar (e.g. 60% -> ||||||::::) from
     * [displayString] and appends a [percentPrefix], percentage and a [percentSuffix].
     * @param filled the symbols to use for the filled part
     * @param empty the symbols to use for the empty part
     * @param displaySize the size of the bar
     * @param percentPrefix the prefix to append before the percentage
     * @param percentSuffix the suffix to append after the percentage
     * @return the percentage bar with the percentage amount
     * @author Fruxz
     * @since 1.0
     */
    fun displayPercentageString(filled: CharSequence, empty: CharSequence, displaySize: Int, percentPrefix: String = " ", percentSuffix: String = "%") =
        "${displayString(filled, empty, displaySize)}$percentPrefix$percentage$percentSuffix"

    /**
     * This function returns the [percentage] as a string,
     * with an % symbol at the end.
     * @return "[percentage]%"
     * @author Fruxz
     * @since 1.0
     */
    fun percentageString(): String {
        return "$percentage%"
    }

    /**
     * This function returns the [percentageString] as a string.
     * @return the percentage as a string
     * @author Fruxz
     * @since 1.0
     */
    override fun toString() = percentageString()

}
