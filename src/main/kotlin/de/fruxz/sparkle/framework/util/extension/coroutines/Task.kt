package de.fruxz.sparkle.framework.util.extension.coroutines

import de.fruxz.ascend.extension.dump
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.util.extension.createKey
import de.fruxz.sparkle.framework.util.extension.system
import de.fruxz.sparkle.framework.util.scheduler.Tasky
import de.fruxz.sparkle.framework.util.scheduler.TemporalAdvice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.await
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import net.kyori.adventure.key.Key
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
	serviceVendor: Key = vendor.createKey("undefined"),
	process: Tasky.() -> Unit,
) = Tasky.task(vendor, temporalAdvice, killAtError, onStart, onStop, onCrash, serviceVendor, process)

/**
 * This function creates a sync task using the [Tasky] system.
 * Internally a [CompletableFuture] is used to create the delayed result.
 * Because the [CompletableFuture.await] function uses the coroutine suspend
 * feature, this function also utilizes the suspend function feature, so the
 * result can be easily managed into your existing Sparkle App.
 * @param delay The delay before the process is executed; default none.
 * @param process The process to be executed.
 * @return The direct result of the process.
 * @author Fruxz
 * @since 1.0
 */
suspend fun <T> asSync(delay: Duration = Duration.ZERO, process: suspend (CoroutineScope) -> T): T {
	val output = CompletableFuture<T>()

	launch(isAsync = false) {
		if (delay.isPositive()) delay(delay)

		try {
			output.complete(process(it))
		} catch (e: Exception) {
			output.completeExceptionally(e)
		}

	}

	return output.await()
}

/**
 * This function creates a sync task using the [Tasky] system.
 * Internally a [CompletableFuture] is used to create the delayed result.
 * Instead of the [asSync] function, this is not a suspending function, so
 * this function can be used anywhere you want!
 * @param delay The delay before the process is executed; default none.
 * @param cycleDuration The delay before the process is executed again; default none.
 * @param process The process to be executed
 * @author Fruxz
 * @since 1.0
 */
fun doSync(delay: Duration = Duration.ZERO, cycleDuration: Duration = Duration.ZERO, process: suspend (CoroutineScope) -> Unit) =
	launch(isAsync = false) { scope ->
		when {
			delay.isPositive() && !cycleDuration.isPositive() -> {
				delay(delay)
				process.invoke(scope)
			}
			delay.isPositive() && cycleDuration.isPositive() -> {
				delay(delay)
				while (scope.isActive) {
					process.invoke(scope)
					delay(cycleDuration)
				}
			}
			else -> process.invoke(scope)
		}
	}

/**
 * This function creates an async context using the KotlinX Coroutines Library.
 * Returns the result as a [Deferred] object, for multiple-line executions.
 * Utilizes the [CoroutineScope.async] function, to create a safe environment.
 * @param delay The delay before the process is executed; default none.
 * @param process The process to be executed.
 * @return The result of the process as a [Deferred] object.
 * @see CoroutineScope.async
 * @see Deferred
 * @see CoroutineScope
 * @author Fruxz
 * @since 1.0
 */
fun <T> asAsync(delay: Duration = Duration.ZERO, process: suspend (CoroutineScope) -> T): Deferred<T> =
	system.coroutineScope.async(context = system.pluginCoroutineDispatcher(true)) {
		if (delay.isPositive()) delay(delay)
		return@async process(system.coroutineScope)
	}

/**
 * This function executes the [process] asynchronously via the [launch] function.
 * @param delay The delay before the process is executed; default none.
 * @param cycleDuration The delay before the process is executed again; default none.
 * @param process The process to be executed.
 * @return The job executing this code *take a look at Kotlin Coroutines*
 * @author Fruxz
 * @since 1.0
 */
fun doAsync(delay: Duration = Duration.ZERO, cycleDuration: Duration = Duration.ZERO, process: suspend (CoroutineScope) -> Unit) = launch(isAsync = true) { scope ->
	if (delay.isPositive()) delay(delay)

	if (cycleDuration.isPositive()) {
		while (scope.isActive) {
			process.invoke(scope)
			delay(cycleDuration)
		}
	} else
		process.invoke(scope)

}

/**
 * Launches a plugin schedule based coroutine.
 */
fun launch(
	vendor: App = system,
	isAsync: Boolean = true,
	process: suspend (CoroutineScope) -> Unit,
) = vendor.coroutineScope.launch(context = system.pluginCoroutineDispatcher(isAsync), block = process)

suspend fun <T, O> T.wait(duration: Duration, code: suspend T.() -> O): O = asAsync(duration) {
	code(this@wait)
}.await()

fun <T, O> T.delayed(duration: Duration, code: suspend T.() -> O) = asAsync { wait(duration, code) }.dump()