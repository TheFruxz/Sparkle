package de.fruxz.sparkle.framework.extension.coroutines

import de.fruxz.ascend.extension.dump
import de.fruxz.sparkle.framework.extension.createKey
import de.fruxz.sparkle.framework.extension.system
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.scheduler.Tasky
import de.fruxz.sparkle.framework.scheduler.TemporalAdvice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.await
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import net.kyori.adventure.key.Key
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

/**
 * Legacy tasky task.
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
 * This function executes the code defined in [process] synchronously.
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
suspend fun <T> asSync(
	delay: Duration = Duration.ZERO,
	vendor: App = system,
	context: CoroutineContext = system.pluginCoroutineDispatcher(false),
	process: suspend (CoroutineScope) -> T
): T {
	val output = CompletableFuture<T>()

	launch(isAsync = false, vendor = vendor, context = context) {
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
 * This function executes the code defined in [process] synchronously.
 * Internally a [CompletableFuture] is used to create the delayed result.
 * Instead of the [asSync] function, this is not a suspending function, so
 * this function can be used anywhere you want!
 * @param delay The delay before the process is executed; default none.
 * @param cycleDuration The delay before the process is executed again; default none.
 * @param process The process to be executed
 * @author Fruxz
 * @since 1.0
 */
fun doSync(
	delay: Duration = Duration.ZERO,
	cycleDuration: Duration = Duration.ZERO,
	vendor: App = system,
	context: CoroutineContext = system.pluginCoroutineDispatcher(false),
	process: suspend (CoroutineScope) -> Unit
) =
	launch(isAsync = false, vendor = vendor, context = context) { scope ->
		if (delay.isPositive()) delay(delay)

		when {
			cycleDuration.isPositive() -> {
				while (scope.isActive) {
					process.invoke(scope)
					delay(cycleDuration)
				}
			}
			else -> {
				process.invoke(scope)
			}
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
fun <T> asAsync(
	delay: Duration = Duration.ZERO,
	context: CoroutineContext = system.pluginCoroutineDispatcher(true),
	process: suspend (CoroutineScope) -> T
): Deferred<T> =
	system.coroutineScope.async(context = context) {
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
fun doAsync(
	delay: Duration = Duration.ZERO,
	cycleDuration: Duration = Duration.ZERO,
	vendor: App = system,
	context: CoroutineContext = system.pluginCoroutineDispatcher(true),
	process: suspend (CoroutineScope) -> Unit
) = launch(isAsync = true, context = context, vendor = vendor) { scope ->
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
 * This function launches a new coroutine, using the provided
 * [context] and preference of [isAsync] execution.
 * @author Fruxz
 * @since 1.0
 */
fun launch(
	vendor: App = system,
	isAsync: Boolean = true,
	context: CoroutineContext = system.pluginCoroutineDispatcher(isAsync),
	process: suspend (CoroutineScope) -> Unit,
) = vendor.coroutineScope.launch(context = context, block = process)

/**
 * This function executes the code provided in [code] asynchronously,
 * with a delay of [duration]. The code waits for its execution to be
 * finished before continuing.
 * @author Fruxz
 * @since 1.0
 */
suspend inline fun <O> wait(duration: Duration, crossinline code: suspend () -> O): O = asAsync(duration) { code() }.await()

/**
 * This function creates a new async process using the [doAsync]
 * function with the [code] process and a delay of [duration].
 * @author Fruxz
 * @since 1.0
 */
fun delayed(duration: Duration, code: suspend (CoroutineScope) -> Unit): Unit = doAsync(delay = duration, process = code).dump()