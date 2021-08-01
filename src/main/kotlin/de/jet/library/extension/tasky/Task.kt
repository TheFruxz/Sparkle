package de.jet.library.extension.tasky

import de.jet.library.JET
import de.jet.library.structure.app.App
import de.jet.library.tool.tasky.Tasky
import de.jet.library.tool.tasky.TemporalAdvice
import de.jet.library.tool.tasky.TemporalAdvice.Companion

fun task(
	vendor: App,
	temporalAdvice: TemporalAdvice,
	killAtError: Boolean = true,
	process: Tasky.() -> Unit,
) = Tasky.task(vendor, temporalAdvice, killAtError, process)

fun sync(
	vendor: App = JET.appInstance,
	temporalAdvice: TemporalAdvice = TemporalAdvice.instant(async = false),
	killAtError: Boolean = true,
	process: Tasky.() -> Unit,
) = Tasky.task(vendor, temporalAdvice, killAtError, process)

fun async(
	vendor: App = JET.appInstance,
	temporalAdvice: TemporalAdvice = TemporalAdvice.instant(async = true),
	killAtError: Boolean = true,
	process: Tasky.() -> Unit,
) = Tasky.task(vendor, temporalAdvice, killAtError, process)