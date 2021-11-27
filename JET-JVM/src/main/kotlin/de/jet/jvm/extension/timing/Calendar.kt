package de.jet.jvm.extension.timing

import de.jet.jvm.tool.timing.calendar.Calendar
import java.util.Calendar as JavaUtilCalendar

/**
 * Converts a [JavaUtilCalendar] to a [Calendar] from JET, but keeps it contents.
 * @author Fruxz
 * @since 1.0
 */
val JavaUtilCalendar.jetCalendar: Calendar
	get() = Calendar.fromLegacy(this)

/**
 * Gets the [JavaUtilCalendar], internally converts it, with its contents, to a
 * JET-[Calendar], edit it with the [action] in the JET-Calender-Environment and
 * returns the [Calendar] converted back to a [JavaUtilCalendar] with the new
 * values containing inside the [JavaUtilCalendar].
 * @param action the edit process, which is executed in the JET-[Calendar]-Environment
 * @return the [JavaUtilCalendar] with the new values
 * @author Fruxz
 * @since 1.0
 */
fun JavaUtilCalendar.editInJETEnvironment(action: Calendar.() -> Unit) = jetCalendar.apply(action).produce()