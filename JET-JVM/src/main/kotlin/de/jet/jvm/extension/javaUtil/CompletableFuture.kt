package de.jet.jvm.extension.javaUtil

import de.jet.jvm.extension.tryOrNull
import java.util.concurrent.CompletableFuture

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