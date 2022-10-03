package de.fruxz.sparkle.framework.util.attachment

import de.fruxz.sparkle.framework.util.identification.KeyedIdentifiable
import kotlinx.coroutines.ExecutorCoroutineDispatcher

interface ContextualInstance<T : KeyedIdentifiable<T>> : KeyedIdentifiable<T> {

    val threadContext: ExecutorCoroutineDispatcher

}