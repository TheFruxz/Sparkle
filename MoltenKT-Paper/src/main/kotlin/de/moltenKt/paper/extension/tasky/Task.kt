package de.moltenKt.paper.extension.tasky

import de.moltenKt.core.extension.dump
import de.moltenKt.core.tool.smart.identification.Identity
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.service.Service
import de.moltenKt.paper.tool.timing.tasky.Tasky
import de.moltenKt.paper.tool.timing.tasky.TemporalAdvice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture
import kotlin.time.Duration

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
suspend fun <T> asSync(process: () -> T): T {
	val output = CompletableFuture<T>()

	task(TemporalAdvice.instant(async = false)) {
		output.complete(process())
	}

	return output.await()
}

/**
 * This function creates a sync task using the [Tasky] system.
 * Internally a [CompletableFuture] is used to create the delayed result.
 * Instead of the [asSync] function, this is not a suspending function, so
 * this function can be used anywhere you want!
 * @author Fruxz
 * @since 1.0
 */
fun <T> doSync(process: () -> T): T {
	val output = CompletableFuture<T>()

	task(TemporalAdvice.instant(async = false)) {
		output.complete(process())
	}

	return output.get()
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
fun <T> asAsync(process: suspend (CoroutineScope) -> T): Deferred<T> = system.coroutineScope.async(block = process)

fun <T> doAsync(process: () -> T): T {
	val output = CompletableFuture<T>()

	task(TemporalAdvice.instant(async = false)) {
		process()
	}

	return output.get()
}

fun launch(
	vendor: App = system,
	process: suspend (CoroutineScope) -> Unit,
) = vendor.coroutineScope.launch(block = process)

suspend fun <T, O> T.wait(duration: Duration, code: suspend T.() -> O): O = asAsync {
	delay(duration)
	code(this@wait)
}.await()

fun <T, O> T.delayed(duration: Duration, code: suspend T.() -> O) = asAsync { wait(duration, code) }.dump()