package de.jet.library.extension.timing

import de.jet.library.tool.timing.calendar.Calendar
import java.util.Calendar as JavaUtilCalendar

val JavaUtilCalendar.kotlinCalendar: Calendar
	get() = Calendar.fromLegacy(this)

fun JavaUtilCalendar.editInKotlinEnvironment(action: Calendar.() -> Unit) = kotlinCalendar.apply(action).produce()