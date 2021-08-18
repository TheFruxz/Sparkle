package de.jet.library.extension.tasky

import de.jet.library.extension.system
import de.jet.library.structure.app.App
import de.jet.library.tool.timing.tasky.Tasky
import de.jet.library.tool.timing.tasky.TemporalAdvice

fun task(
	temporalAdvice: TemporalAdvice,
	killAtError: Boolean = true,
	vendor: App = system,
	process: Tasky.() -> Unit,
) = Tasky.task(vendor, temporalAdvice, killAtError, process)

fun sync(
	temporalAdvice: TemporalAdvice = TemporalAdvice.instant(async = false),
	killAtError: Boolean = true,
	vendor: App = system,
	process: Tasky.() -> Unit,
) = Tasky.task(vendor, temporalAdvice, killAtError, process)

fun async(
	temporalAdvice: TemporalAdvice = TemporalAdvice.instant(async = true),
	killAtError: Boolean = true,
	vendor: App = system,
	process: Tasky.() -> Unit,
) = Tasky.task(vendor, temporalAdvice, killAtError, process)

fun <T> T.wait(ticks: Long, code: T.() -> Unit): T  {
	sync(temporalAdvice = TemporalAdvice.delayed(ticks)) { code(this@wait) }
	return this
}