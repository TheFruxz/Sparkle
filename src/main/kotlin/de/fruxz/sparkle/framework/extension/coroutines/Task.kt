package de.fruxz.sparkle.framework.extension.coroutines

import dev.fruxz.ascend.extension.dump
import dev.fruxz.ascend.extension.switchResult
import de.fruxz.sparkle.framework.extension.debugLog
import de.fruxz.sparkle.framework.extension.sparkle
import de.fruxz.sparkle.framework.infrastructure.app.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.await
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

/**
 * This function executes the code defined in [process] synchronously.
 * Internally a [CompletableFuture] is used to create the delayed result.
 * Because the [CompletableFuture.await] function uses the coroutine suspend
 * feature, this function also utilizes the suspend function feature, so the
 * result can be easily managed into your existing Sparkle App.
 * NOTE! Because the asSync (not the [doSync] and [asSyncDeferred]) function, awaits for its result,
 * this can take up some time and freeze your code. So use this function only,
 * if you really need to wait for the result, and you can not use [asSyncDeferred].
 * @param delay The delay before the process is executed; default none.
 * @param process The process to be executed.
 * @return The direct result of the process.
 * @author Fruxz
 * @since 1.0
 */
suspend fun <T> asSync(
	delay: Duration = Duration.ZERO,
	vendor: App = sparkle,
	context: CoroutineContext = vendor.syncDispatcher,
	process: suspend (CoroutineScope) -> T
): T {
	val output = CompletableFuture<T>()

	launch(isAsync = false, vendor = vendor, context = context) {
		if (delay.isPositive()) delay(delay)

		try {
			output.complete(process(it))
		} catch (e: Exception) {
			output.completeExceptionally(e)
			e.debugLog("asSync process failed future with exception:")
			e.printStackTrace()
		}

	}

	return output.await()
}

fun <T> asSyncDeferred(
	delay: Duration = Duration.ZERO,
	vendor: App = sparkle,
	context: CoroutineContext = vendor.syncDispatcher,
	process: suspend (CoroutineScope) -> T,
): Deferred<T> = vendor.coroutineScope.async(context = context) {
	if (delay.isPositive()) delay(delay)
	return@async asSync(context = context) {
		return@asSync process(this)
	}
}

/**
 * This function executes the code defined in [process] synchronously.
 * Internally a [CompletableFuture] is used to create the delayed result.
 * Instead of the [asSync] function, this is not a suspending function, so
 * this function can be used anywhere you want!
 * @param delay The delay before the process is executed; default none.
 * @param interval The delay before the process is executed again; default none.
 * @param process The process to be executed
 * @author Fruxz
 * @since 1.0
 */
fun doSync(
	delay: Duration = Duration.ZERO,
	interval: Duration = Duration.ZERO,
	vendor: App = sparkle,
	context: CoroutineContext = vendor.syncDispatcher,
	process: suspend (CoroutineScope) -> Unit
) = launch(isAsync = false, vendor = vendor, context = context) { scope ->
		if (delay.isPositive()) delay(delay)

		try {
			when {
				interval.isPositive() -> {
					while (scope.isActive) {
						process.invoke(scope)
						delay(interval)
					}
				}
				else -> {
					process.invoke(scope)
				}
			}
		} catch (e: Exception) {
			e.debugLog("doSync process failed with exception:")
			e.printStackTrace()
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
	vendor: App = sparkle,
	context: CoroutineContext = vendor.asyncDispatcher,
	process: suspend (CoroutineScope) -> T
): Deferred<T> = vendor.coroutineScope.async(context = context) {
		if (delay.isPositive()) delay(delay)

		try {
			return@async process(sparkle.coroutineScope)
		} catch (e: Exception) {
			e.debugLog("asAsync process failed with (invisble) exception:")
			e.printStackTrace()
			throw e
		}

	}

/**
 * This function executes the [process] asynchronously via the [launch] function.
 * @param delay The delay before the process is executed; default none.
 * @param interval The delay before the process is executed again; default none.
 * @param process The process to be executed.
 * @return The job executing this code *take a look at Kotlin Coroutines*
 * @author Fruxz
 * @since 1.0
 */
fun doAsync(
	delay: Duration = Duration.ZERO,
	interval: Duration = Duration.ZERO,
	vendor: App = sparkle,
	context: CoroutineContext = vendor.asyncDispatcher,
	process: suspend (CoroutineScope) -> Unit
) = launch(isAsync = true, context = context, vendor = vendor) { scope ->
	if (delay.isPositive()) delay(delay)

	try {
		if (interval.isPositive()) {
			while (scope.isActive) {
				process.invoke(scope)
				delay(interval)
			}
		} else
			process.invoke(scope)
	} catch (e: Exception) {
		e.debugLog("doAsync process failed with exception:")
		e.printStackTrace()
	}

}

/**
 * This function launches a new coroutine, using the provided
 * [context] and preference of [isAsync] execution.
 * @author Fruxz
 * @since 1.0
 */
fun launch(
	vendor: App = sparkle,
	isAsync: Boolean = true,
	context: CoroutineContext = isAsync.switchResult(vendor.asyncDispatcher, vendor.syncDispatcher),
	process: suspend (CoroutineScope) -> Unit,
) = vendor.coroutineScope.launch(context = context, block = {
	try {
		process.invoke(this)
	} catch (e: Exception) {
		e.debugLog("Last level launch-exception detected (may already been printed publicly):")
		e.printStackTrace()
	}
})

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