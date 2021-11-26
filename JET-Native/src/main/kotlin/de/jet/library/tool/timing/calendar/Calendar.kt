package de.jet.library.tool.timing.calendar

import de.jet.library.annotation.NotPerfect
import de.jet.library.annotation.NotTested
import de.jet.library.tool.smart.Producible
import de.jet.library.tool.timing.calendar.Calendar.TimeField.MILLISECOND
import de.jet.library.tool.timing.calendar.Calendar.TimeField.SECOND
import java.util.*
import kotlin.time.Duration
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

	fun add(timeField: TimeField, amount: Int) = apply {
		origin.add(timeField.field, amount)
	}

	fun add(duration: Duration) = apply {
		add(MILLISECOND, duration.inWholeMilliseconds.toInt())
	}

	fun set(timeField: TimeField, amount: Int) = apply {
		origin.add(timeField.field, amount)
	}

	fun take(timeField: TimeField, amount: Int) = apply {
		origin.add(timeField.field, amount * (-1))
	}

	fun take(duration: Duration) = apply {
		take(MILLISECOND, duration.inWholeMicroseconds.toInt())
	}

	fun get(timeField: TimeField) = origin.get(timeField.field)

	fun getTicks() = get(SECOND).toLong() * 20L

	fun isAfter(it: Calendar) = origin.after(it.origin)

	fun isBefore(it: Calendar) = !isAfter(it)

	fun isInputExpired(latest: Calendar) = isAfter(latest)

	val isExpired: Boolean
		get() = isBefore(now())

	val javaDate: Date
		get() = origin.time

	val javaCalendar: JavaUtilCalendar
		get() = produce()

	fun editInJavaEnvironment(action: JavaUtilCalendar.() -> Unit) {
		origin = origin.apply(action)
	}

	fun durationTo(javaCalendar: JavaUtilCalendar) = Duration.milliseconds(javaCalendar.timeInMillis-origin.timeInMillis)

	fun durationTo(jetCalendar: Calendar) = durationTo(jetCalendar.origin)

	operator fun plus(duration: Duration) = clone().apply {
		add(MILLISECOND, duration.inWholeMilliseconds.toInt())
	}

	operator fun plusAssign(duration: Duration) {
		add(MILLISECOND, duration.inWholeMilliseconds.toInt())
	}

	operator fun minus(duration: Duration) = clone().apply {
		take(MILLISECOND, duration.inWholeMilliseconds.toInt())
	}

	operator fun minusAssign(duration: Duration) {
		take(MILLISECOND, duration.inWholeMilliseconds.toInt())
	}

	override fun clone() = Calendar(origin)

	override fun toString() = origin.toString()

	override fun equals(other: Any?) = if (other is JavaUtilCalendar) {
		other == origin
	} else
		false

	override fun hashCode(): Int {
		return origin.hashCode()
	}

	companion object {

		fun now() = Calendar(JavaUtilCalendar.getInstance())

		fun now(timeZone: TimeZone) = Calendar(JavaUtilCalendar.getInstance(timeZone))

		fun now(locale: Locale) = Calendar(JavaUtilCalendar.getInstance(locale))

		fun now(locale: Locale, timeZone: TimeZone) = Calendar(JavaUtilCalendar.getInstance(timeZone, locale))

		fun fromNow(duration: Duration) = now().plus(duration)

		@ExperimentalTime
		fun isAfter(duration: Duration) = now().plus(duration)

		@ExperimentalTime
		fun durationSince(duration: Duration) = now().minus(duration)

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