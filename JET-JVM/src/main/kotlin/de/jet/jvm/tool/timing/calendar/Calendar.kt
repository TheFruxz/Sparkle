package de.jet.jvm.tool.timing.calendar

import de.jet.jvm.tool.smart.Producible
import de.jet.jvm.tool.timing.calendar.Calendar.FormatStyle.FULL
import de.jet.jvm.tool.timing.calendar.Calendar.FormatStyle.MEDIUM
import de.jet.jvm.tool.timing.calendar.timeUnit.TimeUnit
import de.jet.jvm.tool.timing.calendar.timeUnit.TimeUnit.Companion.MILLISECOND
import de.jet.jvm.tool.timing.calendar.timeUnit.TimeUnit.Companion.SECOND
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZonedDateTime
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime
import java.util.Calendar as JavaUtilCalendar

/**
 * This class is a calendar, which can be from & to a [JavaUtilCalendar] transformed.
 * @param timeInMillis the milliseconds stored in the calendar
 * @param timeZoneId the id of the used timezone
 * @author Fruxz
 * @since 1.0
 */
@Serializable
class Calendar constructor(
	private var timeInMillis: Long,
	private var timeZoneId: String,
) : Producible<JavaUtilCalendar>, Cloneable, Comparable<Calendar> {

	constructor(
		timeInMillis: Long,
		timeZone: TimeZone,
	) : this(timeInMillis, timeZone.id)

	constructor(
		origin: JavaUtilCalendar
	) : this(origin.timeInMillis, origin.timeZone.id)

	constructor(
		instant: Instant,
		timeZone: TimeZone,
	) : this(origin = GregorianCalendar.from(ZonedDateTime.from(instant.atZone(timeZone.toZoneId()))))

	private var origin: JavaUtilCalendar
		set(value) {
			timeInMillis = value.timeInMillis
			timeZoneId = value.timeZone.id
		}
		get() = JavaUtilCalendar.getInstance(TimeZone.getTimeZone(timeZoneId)).apply {
			this.timeInMillis = this@Calendar.timeInMillis
		}

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
	fun add(timeField: TimeUnit, amount: Int) = apply {
		origin = origin.apply {
			add(timeField.javaField, amount)
		}
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
	fun set(timeField: TimeUnit, amount: Int) = apply {
		origin = origin.apply {
			set(timeField.javaField, amount)
		}
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
	fun take(timeField: TimeUnit, amount: Int) = apply {
		origin = origin.apply {
			add(timeField.javaField, amount * (-1))
		}
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
	fun get(timeField: TimeUnit) = origin.get(timeField.javaField)

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
	 * This value returns the timezone of this
	 * calendar, using the [javaCalendar].
	 * @author Fruxz
	 * @since 1.0
	 */
	val timeZone: TimeZone
		get() = javaCalendar.timeZone

	/**
	 * This value returns this calendar as a [JavaUtilCalendar]
	 * object.
	 * @author Fruxz
	 * @since 1.0
	 */
	val javaCalendar: JavaUtilCalendar
		get() = produce()

	/**
	 * This value returns this calendar as a [Instant].
	 * @author Fruxz
	 * @since 1.0
	 */
	val javaInstant: Instant
		get() = Instant.ofEpochMilli(origin.timeInMillis)

	/**
	 * This value returns this calendar time, represented as
	 * the time in milliseconds.
	 * This value uses the [JavaUtilCalendar.getTimeInMillis] function.
	 * @author Fruxz
	 * @since 1.0
	 */
	val timeInMilliseconds: Long
		get() = origin.timeInMillis

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

	/**
	 * Gets the duration from this to the current time.
	 * @return the duration from this to the current time
	 * @author Fruxz
	 * @since 1.0
	 */
	fun durationToNow() = durationTo(now())

	/**
	 * Gets the duration from the [javaCalendar] to this.
	 * @param javaCalendar the calendar which should be compared
	 * @return the duration from the [javaCalendar] to this
	 * @author Fruxz
	 * @since 1.0
	 */
	fun durationFrom(javaCalendar: JavaUtilCalendar) = (origin.timeInMillis-javaCalendar.timeInMillis).milliseconds

	/**
	 * Gets the duration from the [jetCalendar] to this.
	 * @param jetCalendar the calendar which should be compared
	 * @return the duration from the [jetCalendar] to this
	 * @author Fruxz
	 * @since 1.0
	 */
	fun durationFrom(jetCalendar: Calendar) = durationFrom(jetCalendar.origin)

	/**
	 * Gets the duration from the current time to this.
	 * @return the duration from the current time to this
	 * @author Fruxz
	 * @since 1.0
	 */
	fun durationFromNow() = durationFrom(now())

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
	override fun toString() = getFormatted()

	/**
	 * Returns the current [Calendar]-Time as a localized String,
	 * using the [locale], a [dateStyle] and a [timeStyle].
	 * This function gives the ability, to easily and quickly
	 * create a usable String, which can be displayed to average
	 * customers or in the front-end.
	 * @param locale in which language & language-format it should be generated
	 * @param dateStyle the style & length of the date
	 * @param timeStyle the style & length of the time
	 * @return A locale-based formatted [String] of the [javaDate]
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getFormatted(locale: Locale = Locale.getDefault(), dateStyle: FormatStyle = FULL, timeStyle: FormatStyle = MEDIUM) =
		SimpleDateFormat.getDateTimeInstance(dateStyle.ordinal, timeStyle.ordinal, locale).format(javaDate)

	/**
	 * This function returns, if the [JavaUtilCalendar.getTimeInMillis] of this == [other].
	 * @param other the calendar which should be compared
	 * @author Fruxz
	 * @since 1.0
	 */
	override fun equals(other: Any?) = if (other is JavaUtilCalendar) {
		other.timeInMillis == origin.timeInMillis
	} else
		false

	override fun hashCode(): Int {
		return origin.hashCode()
	}

	/**
	 * This function compares the [origin] object with the [other] object,
	 * with their [JavaUtilCalendar.getTimeInMillis] values.
	 * @param other is the calendar which should be compared
	 * @author Fruxz
	 * @since 1.0
	 */
	override operator fun compareTo(other: Calendar): Int {
		if (origin.timeInMillis < other.origin.timeInMillis)
			return -1
		if (origin.timeInMillis > other.origin.timeInMillis)
			return 1
		return 0
	}

	companion object {

		/**
		 * This function returns the current date and time.
		 * @return the current date and time
		 * @author Fruxz
		 * @since 1.0
		 */
		fun now(instance: JavaUtilCalendar = JavaUtilCalendar.getInstance()) = Calendar(instance)

		/**
		 * This function returns the current date and time.
		 * @param timeZone the time zone which should be used
		 * @return the current date and time of [timeZone]
		 * @author Fruxz
		 * @since 1.0
		 */
		fun now(timeZone: TimeZone) = now(instance = JavaUtilCalendar.getInstance(timeZone))

		/**
		 * This function returns the current date and time.
		 * @param locale the locale which should be used
		 * @return the current date and time of [locale]
		 * @author Fruxz
		 * @since 1.0
		 */
		fun now(locale: Locale) = now(instance = JavaUtilCalendar.getInstance(locale))

		/**
		 * This function returns the current date and time.
		 * @param timeZone the time zone which should be used
		 * @param locale the locale which should be used
		 * @return the current date and time of [timeZone] and [locale]
		 * @author Fruxz
		 */
		fun now(locale: Locale, timeZone: TimeZone) = now(instance = JavaUtilCalendar.getInstance(timeZone, locale))

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
		fun durationSince(duration: Duration) = now().minus(duration)

		/**
		 * This function returns a new [Calendar] with the
		 * same values as the [calendar].
		 * @param calendar the calendar which should be cloned
		 * @return a new [Calendar] with the same values
		 * @author Fruxz
		 * @since 1.0
		 */
		fun fromLegacy(calendar: JavaUtilCalendar) = now(instance = calendar)

	}

	enum class FormatStyle {
		FULL, HUGE, MEDIUM, SHORT;
	}

}