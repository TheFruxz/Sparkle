package de.jet.jvm.extension.time

import de.jet.jvm.tool.timing.calendar.Calendar
import de.jet.jvm.tool.timing.calendar.Calendar.TimeField.*
import java.util.Calendar as UtilCalendar

/**
 * This value returns the value of the [Calendar.get] function
 * with the TimeUnit [ERA].
 * @return the value of the [Calendar.get] function with the TimeUnit [ERA].
 * @author Fruxz
 * @since 1.0
 */
val Calendar.era: Int
	get() = get(ERA)

/**
 * This value returns the value of the [Calendar.get] function
 * with the TimeUnit [YEAR].
 * @return the value of the [Calendar.get] function with the TimeUnit [YEAR].
 * @author Fruxz
 * @since 1.0
 */
val Calendar.year: Int
	get() = get(YEAR)

/**
 * This value returns the value of the [Calendar.year] value, but
 * attaches 0 at the beginning if the value is lower than the usual
 * used displayed time format.
 * ***Format: ####***
 * @author Fruxz
 * @since 1.0
 */
val Calendar.styledYear: String
	get() = "$year".padStart(4, '0')

/**
 * This value returns the value of the [Calendar.get] function
 * with the TimeUnit [MONTH] plus 1.
 * @return the value of the [Calendar.get] function with the TimeUnit [MONTH] plus 1.
 * @author Fruxz
 * @since 1.0
 */
val Calendar.month: Int
	get() = get(MONTH)+1

/**
 * This value returns the value of the [Calendar.month] value, but
 * attaches 0 at the beginning if the value is lower than the usual
 * used displayed time format.
 * ***Format: ##***
 * @author Fruxz
 * @since 1.0
 */
val Calendar.styledMonth: String
	get() = "$month".padStart(2, '0')

/**
 * This value returns the value of the [UtilCalendar.get] function
 * with the TimeUnit [UtilCalendar.DAY_OF_MONTH].
 * @return the value of the [UtilCalendar.get] function with the TimeUnit [UtilCalendar.DAY_OF_MONTH].
 * @author Fruxz
 * @since 1.0
 */
val Calendar.day: Int
	get() = javaCalendar.get(UtilCalendar.DAY_OF_MONTH)

/**
 * This value returns the value of the [Calendar.day] value, but
 * attaches 0 at the beginning if the value is lower than the usual
 * used displayed time format.
 * ***Format: ##***
 * @author Fruxz
 * @since 1.0
 */
val Calendar.styledDay: String
	get() = "$day".padStart(2, '0')

/**
 * This value returns the value of the [Calendar.get] function
 * with the TimeUnit [HOUR].
 * @return the value of the [Calendar.get] function with the TimeUnit [HOUR].
 * @author Fruxz
 */
val Calendar.hour: Int
	get() = get(HOUR)

/**
 * This value returns the value of the [Calendar.hour] value, but
 * attaches 0 at the beginning if the value is lower than the usual
 * used displayed time format.
 * ***Format: ##***
 * @author Fruxz
 * @since 1.0
 */
val Calendar.styledHour: String
	get() = "$hour".padStart(2, '0')

/**
 * This value returns the value of the [Calendar.get] function
 * with the TimeUnit [MINUTE].
 * @return the value of the [Calendar.get] function with the TimeUnit [MINUTE].
 * @author Fruxz
 * @since 1.0
 */
val Calendar.minute: Int
	get() = get(MINUTE)

/**
 * This value returns the value of the [Calendar.minute] value, but
 * attaches 0 at the beginning if the value is lower than the usual
 * used displayed time format.
 * ***Format: ##***
 * @author Fruxz
 * @since 1.0
 */
val Calendar.styledMinute: String
	get() = "$minute".padStart(2, '0')

/**
 * This value returns the value of the [Calendar.get] function
 * with the TimeUnit [SECOND].
 * @return the value of the [Calendar.get] function with the TimeUnit [SECOND].
 * @author Fruxz
 * @since 1.0
 */
val Calendar.second: Int
	get() = get(SECOND)

/**
 * This value returns the value of the [Calendar.second] value, but
 * attaches 0 at the beginning if the value is lower than the usual
 * used displayed time format.
 * ***Format: ##***
 * @author Fruxz
 * @since 1.0
 */
val Calendar.styledSecond: String
	get() = "$second".padStart(2, '0')

/**
 * This value returns the value of the [Calendar.get] function
 * with the TimeUnit [MILLISECOND].
 * @return the value of the [Calendar.get] function with the TimeUnit [MILLISECOND].
 * @author Fruxz
 * @since 1.0
 */
val Calendar.millisecond: Int
	get() = get(MILLISECOND)

/**
 * This value returns the value of the [Calendar.millisecond] value, but
 * attaches 0 at the beginning if the value is lower than the usual
 * used displayed time format.
 * ***Format: ####***
 * @author Fruxz
 * @since 1.0
 */
val Calendar.styledMillisecond: String
	get() = "$millisecond".padStart(4, '0')

val Calendar.prettyPrint: String
	get() = "y: $styledYear; m: $styledMonth; d: $styledDay; h: $styledHour; m: $styledMinute; s: $styledSecond; ms: $styledMillisecond"