@file:Suppress("NOTHING_TO_INLINE")

package de.jet.library.extension

inline fun getResourceText(resource: String) =
    object {}.javaClass.classLoader.getResource(resource)?.readText() ?: throw NoSuchElementException("Resource $resource not found")

inline fun getResourceByteArray(resource: String) =
    object {}.javaClass.classLoader.getResource(resource)?.readBytes() ?: throw NoSuchElementException("Resource $resource not found")
