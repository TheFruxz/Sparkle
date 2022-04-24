package de.moltenKt.paper.extension.tasky

import de.moltenKt.core.tool.smart.identification.Identity
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.service.Service
import de.moltenKt.paper.tool.timing.tasky.Tasky
import de.moltenKt.paper.tool.timing.tasky.TemporalAdvice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture

/**
 * Exceptions are caught!
 */
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

/**
 * Exceptions are caught!
 */
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

/**
 * This function creates a sync task using the [Tasky] system.
 * Internally a [CompletableFuture] is used to create the delayed result.
 * Because the [CompletableFuture.await] function uses the coroutine suspend
 * feature, this function also utilizes the suspend function feature, so the
 * result can be easily managed into your existing MoltenKT App.
 * @param process The process to be executed.
 * @return The direct result of the process.
 * @author Fruxz
 * @since 1.0
 */
suspend fun <T> asSync(process: Tasky.() -> T): T {
	val output = CompletableFuture<T>()

	sync {
		output.complete(process())
	}

	return output.await()
}

/**
 * This function creates an async context using the KotlinX Coroutines Library.
 * Returns the result as a [Deferred] object, for multiple-line executions.
 * Utilizes the [CoroutineScope.async] function, to create a safe environment.
 * @param process The process to be executed.
 * @return The result of the process as a [Deferred] object.
 * @see CoroutineScope.async
 * @see Deferred
 * @see CoroutineScope
 * @author Fruxz
 * @since 1.0
 */
fun <T> asAsync(process: suspend CoroutineScope.() -> T): Deferred<T> = system.coroutineScope.async(block = process)

/**
 * Exceptions are caught!
 */
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

fun coroutine(
	vendor: App = system,
	process: suspend CoroutineScope.() -> Unit,
) = vendor.coroutineScope.launch(block = process)

fun <T> T.waitTask(ticks: Long, code: T.() -> Unit): T  {
	sync(temporalAdvice = TemporalAdvice.delayed(ticks)) { code(this@waitTask) }
	return this
}