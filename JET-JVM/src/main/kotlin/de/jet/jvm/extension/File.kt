@file:Suppress("NOTHING_TO_INLINE")

package de.jet.jvm.extension

import java.io.File
import java.nio.file.Path

/**
 * Returns the file inside the resource folder of the class, where the function is called from.
 * The file is returned by its content read by the [readText] function.
 * @param resource the file(+folder) path
 * @return the file content
 * @author Fruxz
 * @since 1.0
 */
inline fun getResourceText(resource: String) =
    object {}.javaClass.classLoader.getResource(resource)?.readText() ?: throw NoSuchElementException("Resource $resource not found")

/**
 * Returns the file inside the resource folder of the class, where the function is called from.
 * The file is returned by its content read by the [readBytes] function.
 * @param resource the file(+folder) path
 * @return the file content
 * @author Fruxz
 * @since 1.0
 */
inline fun getResourceByteArray(resource: String) =
    object {}.javaClass.classLoader.getResource(resource)?.readBytes() ?: throw NoSuchElementException("Resource $resource not found")

/**
 * Converts the string [this] into a full [File] using [this] as a [Path],
 * through the [Path.of] and the [Path.toFile] functions.
 * @author Fruxz
 * @since 1.0
 */
fun String.pathAsFile(): File =
    Path.of(this).toFile()

/**
 * Converts the string [this] into a base-based [File] using [this] as a [Path],
 * through the [Path.of] and the [Path.toFile] functions additionaly the
 * [System.getProperty]("user.dir") process.
 * @author Fruxz
 * @since 1.0
 */
fun String.pathAsFileFromRuntime() =
    File(System.getProperty("user.dir") + "/$this")

/**
 * This function adds an additional part to the [Path] using
 * the [Path.resolve] function and a [other] part attaching
 * to the [Path].
 * @param other is the additional path
 * @author Fruxz
 * @since 1.0
 */
operator fun Path.div(other: String): Path = resolve(other)
