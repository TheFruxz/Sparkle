package de.jet.paper.extension.tasky

import de.jet.jvm.tool.smart.identification.Identity
import de.jet.paper.extension.system
import de.jet.paper.structure.app.App
import de.jet.paper.structure.service.Service
import de.jet.paper.tool.timing.tasky.Tasky
import de.jet.paper.tool.timing.tasky.TemporalAdvice

fun task(
	temporalAdvice: TemporalAdvice,
	killAtError: Boolean = true,
	vendor: App = system,
	onStart: Tasky.() -> Unit = {},
	onStop: Tasky.() -> Unit = {},
	onCrash: Tasky.() -> Unit = {},
	serviceVendor: Identity<Service> = Identity("undefined"),
	process: Tasky.() -> Unit,
) = Tasky.task(vendor, temporalAdvice, killAtError, onStart, onStop, onCrash, serviceVendor, process)

fun sync(
	temporalAdvice: TemporalAdvice = TemporalAdvice.instant(async = false),
	killAtError: Boolean = true,
	vendor: App = system,
	onStart: Tasky.() -> Unit = {},
	onStop: Tasky.() -> Unit = {},
	onCrash: Tasky.() -> Unit = {},
	serviceVendor: Identity<Service> = Identity("undefined"),
	process: Tasky.() -> Unit,
) = Tasky.task(vendor, temporalAdvice, killAtError, onStart, onStop, onCrash, serviceVendor, process)

fun async(
	temporalAdvice: TemporalAdvice = TemporalAdvice.instant(async = true),
	killAtError: Boolean = true,
	vendor: App = system,
	onStart: Tasky.() -> Unit = {},
	onStop: Tasky.() -> Unit = {},
	onCrash: Tasky.() -> Unit = {},
	serviceVendor: Identity<Service> = Identity("undefined"),
	process: Tasky.() -> Unit,
) = Tasky.task(vendor, temporalAdvice, killAtError, onStart, onStop, onCrash, serviceVendor, process)

fun <T> T.wait(ticks: Long, code: T.() -> Unit): T  {
	sync(temporalAdvice = TemporalAdvice.delayed(ticks)) { code(this@wait) }
	return this
}