package de.jet.jvm.tool.timing.calendar

import de.jet.jvm.annotation.NotTested
import de.jet.jvm.extension.time.prettyPrint
import de.jet.jvm.tool.smart.Producible
import de.jet.jvm.tool.timing.calendar.Calendar.TimeField.*
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime
import java.util.Calendar as JavaUtilCalendar

/**
 * This class is a calender, which can be from & to a [JavaUtilCalendar] transformed.
 * @param origin the java base of the calendar
 * @author Fruxz
 * @since 1.0
 */
@NotTested
class Calendar private constructor(
	private var origin: JavaUtilCalendar
) : Producible<JavaUtilCalendar>, Cloneable {

	override fun produce() = origin

	/**
	 * This function adds some time to the calendar.
	 * It takes the [amount], takes the time unit
	 * [timeField] and adds it to the calendar.
	 * @param timeField the unit of time which should be added
	 * @param amount the amount of time which should be added
	 * @return the calendar itself
	 * @author Fruxz
	 * @since 1.0
	 */
	fun add(timeField: TimeField, amount: Int) = apply {
		origin.add(timeField.field, amount)
	}

	/**
	 * This function adds some time to the calendar.
	 * It takes the time from the [duration] and adds
	 * it to the calendar using the internal units.
	 * @param duration the amount of time which should be added
	 * @return the calendar itself
	 * @author Fruxz
	 * @since 1.0
	 */
	fun add(duration: Duration) = apply {
		add(MILLISECOND, duration.inWholeMilliseconds.toInt())
	}

	/**
	 * This function sets the time of the calendar.
	 * It takes the [amount], takes the time unit
	 * [timeField] and sets it to the calendar.
	 * @param timeField the unit of time which should be set
	 * @param amount the amount of time which should be set
	 * @return the calendar itself
	 * @author Fruxz
	 * @since 1.0
	 */
	fun set(timeField: TimeField, amount: Int) = apply {
		origin.set(timeField.field, amount)
	}

	/**
	 * This function takes some time from the calendar.
	 * It takes the [amount], takes the time unit
	 * [timeField] and takes it from the calendar.
	 * @param timeField the unit of time which should be taken
	 * @param amount the amount of time which should be taken
	 * @return the calendar itself
	 * @author Fruxz
	 * @since 1.0
	 */
	fun take(timeField: TimeField, amount: Int) = apply {
		origin.add(timeField.field, amount * (-1))
	}

	/**
	 * This function takes some time from the calendar.
	 * It takes the time from the [duration] and takes
	 * it from the calendar using the internal units.
	 * @param duration the amount of time which should be taken
	 * @return the calendar itself
	 * @author Fruxz
	 * @since 1.0
	 */
	fun take(duration: Duration) = apply {
		take(MILLISECOND, duration.inWholeMicroseconds.toInt())
	}

	/**
	 * This function returns the time of the calendar.
	 * It takes the [timeField] and returns the time using it.
	 * @param timeField the unit of time which should be returned
	 * @return the time using the unit of time
	 * @author Fruxz
	 * @since 1.0
	 */
	fun get(timeField: TimeField) = origin.get(timeField.field)

	/**
	 * This function returns the time of the calendar in Minecraft-Ticks.
	 * @return the time in Minecraft-Ticks
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getTicks() = get(SECOND).toLong() * 20L

	/**
	 * This function returns, if this calendar is
	 * after the [it] calendar.
	 * @param it the calendar which should be compared
	 * @return if this calendar is after the [it] calendar
	 * @author Fruxz
	 * @since 1.0
	 */
	fun isAfter(it: Calendar) = origin.after(it.origin)

	/**
	 * This function returns, if this calendar is
	 * before the [it] calendar.
	 * @param it the calendar which should be compared
	 * @return if this calendar is before the [it] calendar
	 * @author Fruxz
	 * @since 1.0
	 */
	fun isBefore(it: Calendar) = !isAfter(it)

	/**
	 * This function returns, if this calendar is
	 * after the [latest] calendar. This basically
	 * means, this calendar is the expiration date
	 * and [latest] is the current date, to check,
	 * if the expiration data (this) is expired.
	 * @param latest the calendar which should be compared
	 * @return if this calendar is after the [latest] calendar
	 * @author Fruxz
	 * @since 1.0
	 */
	fun isInputExpired(latest: Calendar) = isAfter(latest)

	/**
	 * This value returns, if this calendar is
	 * after [now] calendar. This basically
	 * means, this calendar is the expiration date
	 * and [now] is the current date, to check,
	 * if the expiration data (this) is expired.
	 * @author Fruxz
	 * @since 1.0
	 */
	val isExpired: Boolean
		get() = isBefore(now())

	/**
	 * This value returns this calendar as a [Date]
	 * object.
	 * @author Fruxz
	 * @since 1.0
	 */
	val javaDate: Date
		get() = origin.time

	/**
	 * This value returns this calendar as a [JavaUtilCalendar]
	 * object.
	 * @author Fruxz
	 * @since 1.0
	 */
	val javaCalendar: JavaUtilCalendar
		get() = produce()

	/**
	 * Gets this calendar, internally converts it, with its contents, to a
	 * [JavaUtilCalendar], edit it with the [action] in the [JavaUtilCalendar]-Environment and
	 * returns the [JavaUtilCalendar] converted back to a [Calendar] with the new
	 * values containing inside the [Calendar].
	 * @param action the edit process, which is executed in the [JavaUtilCalendar]-Environment
	 * @return the [Calendar] with the new values
	 * @author Fruxz
	 * @since 1.0
	 */
	fun editInJavaEnvironment(action: JavaUtilCalendar.() -> Unit) {
		origin = origin.apply(action)
	}

	/**
	 * Gets the duration from this to the [javaCalendar].
	 * @param javaCalendar the calendar which should be compared
	 * @return the duration from this to the [javaCalendar]
	 * @author Fruxz
	 * @since 1.0
	 */
	fun durationTo(javaCalendar: JavaUtilCalendar) = (javaCalendar.timeInMillis-origin.timeInMillis).milliseconds

	/**
	 * Gets the duration from this to the [jetCalendar].
	 * @param jetCalendar the calendar which should be compared
	 * @return the duration from this to the [jetCalendar]
	 * @author Fruxz
	 * @since 1.0
	 */
	fun durationTo(jetCalendar: Calendar) = durationTo(jetCalendar.origin)

	operator fun plus(duration: Duration) = clone().add(MILLISECOND, duration.inWholeMilliseconds.toInt())

	operator fun plusAssign(duration: Duration) {
		add(MILLISECOND, duration.inWholeMilliseconds.toInt())
	}

	operator fun minus(duration: Duration) = clone().take(MILLISECOND, duration.inWholeMilliseconds.toInt())

	operator fun minusAssign(duration: Duration) {
		take(MILLISECOND, duration.inWholeMilliseconds.toInt())
	}

	/**
	 * This function returns a new [Calendar] with the
	 * same values as this calendar.
	 * @return a new [Calendar] with the same values
	 * @author Fruxz
	 * @since 1.0
	 */
	override fun clone(): Calendar {
		return Calendar(origin.clone() as JavaUtilCalendar)
	}

	/**
	 * This function returns the result of the [toString] function
	 * of the [origin] object.
	 * @author Fruxz
	 * @since 1.0
	 */
	override fun toString() = prettyPrint

	override fun equals(other: Any?) = if (other is JavaUtilCalendar) {
		other == origin
	} else
		false

	override fun hashCode(): Int {
		return origin.hashCode()
	}

	companion object {

		/**
		 * This function returns the current date and time.
		 * @return the current date and time
		 * @author Fruxz
		 * @since 1.0
		 */
		fun now() = Calendar(JavaUtilCalendar.getInstance())

		/**
		 * This function returns the current date and time.
		 * @param timeZone the time zone which should be used
		 * @return the current date and time of [timeZone]
		 * @author Fruxz
		 * @since 1.0
		 */
		fun now(timeZone: TimeZone) = Calendar(JavaUtilCalendar.getInstance(timeZone))

		/**
		 * This function returns the current date and time.
		 * @param locale the locale which should be used
		 * @return the current date and time of [locale]
		 * @author Fruxz
		 * @since 1.0
		 */
		fun now(locale: Locale) = Calendar(JavaUtilCalendar.getInstance(locale))

		/**
		 * This function returns the current date and time.
		 * @param timeZone the time zone which should be used
		 * @param locale the locale which should be used
		 * @return the current date and time of [timeZone] and [locale]
		 * @author Fruxz
		 */
		fun now(locale: Locale, timeZone: TimeZone) = Calendar(JavaUtilCalendar.getInstance(timeZone, locale))

		/**
		 * This function returns the current date and time plus the [duration].
		 * @param duration the duration which should be added
		 * @return the current date and time plus the [duration]
		 * @author Fruxz
		 * @since 1.0
		 */
		fun fromNow(duration: Duration) = now().plus(duration)

		/**
		 * This function returns the current date and time plus the [duration].
		 * @param duration the duration which should be added
		 * @return the current date and time plus the [duration]
		 * @author Fruxz
		 * @since 1.0
		 */
		@ExperimentalTime
		fun isAfter(duration: Duration) = now().plus(duration)

		/**
		 * This function returns the current date and time plus the [duration].
		 * @param duration the duration which should be added
		 * @return the current date and time plus the [duration]
		 * @author Fruxz
		 * @since 1.0
		 */
		@ExperimentalTime
		fun durationSince(duration: Duration) = now().minus(duration)

		/**
		 * This function returns a new [Calendar] with the
		 * same values as the [calendar].
		 * @param calendar the calendar which should be cloned
		 * @return a new [Calendar] with the same values
		 * @author Fruxz
		 * @since 1.0
		 */
		fun fromLegacy(calendar: JavaUtilCalendar) = Calendar(calendar)

	}

	/**
	 * @property ERA era as a time unit
	 * @property YEAR year as a time unit
	 * @property MONTH month as a time unit
	 * @property HOUR hour as a time unit
	 * @property MINUTE minute as a time unit
	 * @property SECOND second as a time unit
	 * @property MILLISECOND millisecond as a time unit
	 */
	enum class TimeField {
		ERA, YEAR, MONTH, HOUR, MINUTE, SECOND, MILLISECOND;

		internal val field by lazy {
			when (this) {
				ERA -> JavaUtilCalendar.ERA
				YEAR -> JavaUtilCalendar.YEAR
				MONTH -> JavaUtilCalendar.MONTH
				HOUR -> JavaUtilCalendar.HOUR
				MINUTE -> JavaUtilCalendar.MINUTE
				SECOND -> JavaUtilCalendar.SECOND
				MILLISECOND -> JavaUtilCalendar.MILLISECOND
			}
		}

	}

}