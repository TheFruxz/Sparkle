package de.jet.library.extension.timing

import de.jet.library.tool.timing.calendar.Calendar
import java.util.Calendar as JavaUtilCalendar

val JavaUtilCalendar.jetCalendar: Calendar
	get() = Calendar.fromLegacy(this)

fun JavaUtilCalendar.editInJETEnvironment(action: Calendar.() -> Unit) = jetCalendar.apply(action).produce()