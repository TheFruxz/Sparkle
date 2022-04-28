package de.moltenKt.core.extension.javaUtil

import de.moltenKt.core.extension.tryOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.util.concurrent.CompletableFuture
import kotlin.time.Duration

/**
 * Returns the value of the [CompletableFuture] or if it throws
 * exceptions, it returns null instead.
 * @return the value or null
 * @author Fruxz
 * @since 1.0
 */
fun <T> CompletableFuture<T>.getOrNull() = tryOrNull { get() }

/**
 * Returns the value of the [CompletableFuture] or if it throws
 * exceptions, it returns the [default] instead.
 * @return the value or [default]
 * @author Fruxz
 * @since 1.0
 */
fun <T> CompletableFuture<T>.getOrDefault(default: T) = getOrNull() ?: default

/**
 * This suspend function returns the result [T] of this [CompletableFuture],
 * when it is completed. (Uses Kotlin-Coroutines system)
 * @param T the type of the result
 * @return the result
 * @author Fruxz
 * @since 1.0
 * @see CompletableFuture.join
 */
suspend fun <T> CompletableFuture<T>.await() =
	withContext(Dispatchers.Default) { this@await.join() }

/**
 * This suspend function returns the result [T] of this [CompletableFuture],
 * when it is completed. (Uses Kotlin-Coroutines system).
 * After it is completed, the [block] block is [apply]'d on the result.
 * @param T the type of the result
 * @param block the block to apply on the result
 * @return the result
 * @author Fruxz
 * @since 1.0
 */
suspend fun <T> CompletableFuture<T>.await(block: (T) -> Unit) =
	await().apply(block)

/**
 * This suspend function returns the result [T] of this [CompletableFuture],
 * when it is completed. (Uses Kotlin-Coroutines system).
 * If it is not completed after [timeout] it returns null.
 * @param T the type of the result
 * @param timeout the timeout
 * @return the result or null
 * @author Fruxz
 * @since 1.0
 */
suspend fun <T> CompletableFuture<T>.await(timeout: Duration) = withTimeoutOrNull(timeout) { await() }

/**
 * This suspend function returns the result [T] of this [CompletableFuture],
 * when it is completed. (Uses Kotlin-Coroutines system).
 * If it is not completed after [timeout] the [block] block is [apply]'d on the result.
 * @param T the type of the result
 * @param timeout the timeout
 * @param block the block to apply on the result
 * @return the result or null
 * @author Fruxz
 * @since 1.0
 */
suspend fun <T> CompletableFuture<T>.await(timeout: Duration, block: (T) -> Unit) = withTimeoutOrNull(timeout) { await(block) }