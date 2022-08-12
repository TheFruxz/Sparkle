package de.moltenKt.paper.tool.smart

import kotlinx.coroutines.ExecutorCoroutineDispatcher

interface ContextualInstance<T : KeyedIdentifiable<T>> : KeyedIdentifiable<T> {

    val threadContext: ExecutorCoroutineDispatcher

}