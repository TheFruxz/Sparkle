package de.jet.jvm.tool.timing.calendar

data class CalendarRange(override val start: Calendar, override val endInclusive: Calendar) : Iterable<Calendar>, ClosedRange<Calendar>, Comparable<Calendar> {

	/**
	 * This function returns, if [value] is (time wise) in the range between [start] and [endInclusive].
	 * @param value
	 * @author Fruxz
	 * @since 1.0
	 */
	override operator fun contains(value: Calendar): Boolean {
		return value in start..endInclusive
	}

	/**
	 * This function builds an iterator, containing only [start] & [endInclusive] as its values.
	 * @author Fruxz
	 * @since 1.0
	 */
	override fun iterator() = listOf(start, endInclusive).iterator()

	/**
	 * This function returns, if the [other]-time is before the [start] of the range, or if the [other]-time is after the [endInclusive] of the range. (or inside, then returns 'equals')
	 * @param other is the calendar to return
	 * @author Fruxz
	 * @since 1.0
	 */
	override fun compareTo(other: Calendar): Int {
		if (other.isBefore(start)) return -1
		if (other.isAfter(endInclusive)) return 1
		return 0
	}

}