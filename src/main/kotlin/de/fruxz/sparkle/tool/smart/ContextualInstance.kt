package de.fruxz.sparkle.tool.smart

import kotlinx.coroutines.ExecutorCoroutineDispatcher

interface ContextualInstance<T : KeyedIdentifiable<T>> : KeyedIdentifiable<T> {

    val threadContext: ExecutorCoroutineDispatcher

}