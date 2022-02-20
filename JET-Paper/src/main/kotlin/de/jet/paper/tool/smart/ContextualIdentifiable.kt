package de.jet.paper.tool.smart

import kotlinx.coroutines.ExecutorCoroutineDispatcher

interface ContextualIdentifiable<T> : VendorsIdentifiable<T> {

    val threadContext: ExecutorCoroutineDispatcher

}