package de.jet.library.extension.tasky

import de.jet.library.structure.app.App
import de.jet.library.tool.tasky.Tasky
import de.jet.library.tool.tasky.TemporalAdvice

fun task(
	vendor: App,
	temporalAdvice: TemporalAdvice,
	killAtError: Boolean = true,
	process: Tasky.() -> Unit,
) = Tasky.task(vendor, temporalAdvice, killAtError, process)