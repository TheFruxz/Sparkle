package de.jet.jvm.tool.timing.calendar

import de.jet.jvm.annotation.NotPerfect
import de.jet.jvm.annotation.NotTested
import de.jet.jvm.annotation.NotWorking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@NotWorking
@NotTested
@NotPerfect
@Serializable
@SerialName("JetCalendar")
data class JetCalendar(
	private val milliseconds: ULong,
) {

	val inWholeMilliseconds: ULong
		get() = milliseconds

	val inWholeSeconds: ULong
		get() = milliseconds / 1000u

	val inWholeMinutes: ULong
		get() = milliseconds / 60000u

	val inWholeHours: ULong
		get() = milliseconds / 3600000u

	val inWholeDays: ULong
		get() = milliseconds / 86400000u

	val inWholeWeeks: ULong
		get() = milliseconds / 604800000u

	val inWholeMonths: ULong
		get() = milliseconds / 2678400000u

	val inWholeYears: ULong
		get() = milliseconds / 31536000000u

	val inWholeCenturies: ULong
		get() = milliseconds / 3153600000000u

	val inWholeMillennia: ULong
		get() = milliseconds / 31536000000000u

	val timeYears: ULong
		get() = inWholeYears
	val timeMonths: ULong
		get() = inWholeMonths % 12u
	val timeDays: ULong
		get() = inWholeDays % 30u
	val timeHours: ULong
		get() = inWholeHours % 24u
	val timeMinutes: ULong
		get() = inWholeMinutes % 60u
	val timeSeconds: ULong
		get() = inWholeSeconds % 60u
	val timeMilliseconds: ULong
		get() = inWholeMilliseconds % 1000u

	fun printFormat(): String {
		return "$timeYears years, $timeMonths months, $timeDays days, $timeHours hours, $timeMinutes minutes, $timeSeconds seconds, $timeMilliseconds milliseconds"
	}

	companion object {

		fun fromCalendar(calendar: Calendar) =
			JetCalendar(calendar.javaCalendar.timeInMillis.toULong()+62126000000000u)

		fun fromCalendarFromYearZero(calendar: Calendar) =
			JetCalendar(calendar.javaCalendar.timeInMillis.toULong()+62126000000000u)

	}

}