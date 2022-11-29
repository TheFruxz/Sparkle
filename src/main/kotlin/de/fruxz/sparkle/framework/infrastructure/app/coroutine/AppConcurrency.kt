package de.fruxz.sparkle.framework.infrastructure.app.coroutine

import de.fruxz.sparkle.framework.context.AppComposable
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * This interface helps, to define concurrency templates for the
 * code execution of an App.
 * It is defined as an interface, to allow third party implementations
 * and extensions, to integrate their own concurrency templates.
 * @author Fruxz
 * @since 1.0
 */
interface AppConcurrency {

	val onLoadContext: AppComposable<CoroutineContext>
	val onEnableContext: AppComposable<CoroutineContext>

	companion object {

		/**
		 * This is the preferred default concurrency template, but it
		 * requires more computational power, to get back the main thread.
		 * Sometimes you should choose to stay at the bukkit-default [sync],
		 * which executes the code at the main thread, if it is used in the
		 * App's onLoad or onEnable method.
		 * @author Fruxz
		 * @since 1.0
		 */
		val async = object : AppConcurrency {
			override val onLoadContext: AppComposable<CoroutineContext> = AppComposable { EmptyCoroutineContext }
			override val onEnableContext: AppComposable<CoroutineContext> = AppComposable { EmptyCoroutineContext}
		}

		/**
		 * This tries to execute the code at the main thread, if it is used
		 * in the App's onLoad or onEnable method. This is the common bukkit
		 * behavior, but can be way slower, because it runs synchronously.
		 * @author Fruxz
		 * @since 1.0
		 */
		val sync = object : AppConcurrency {
			override val onLoadContext: AppComposable<CoroutineContext> = AppComposable { it.syncDispatcher }
			override val onEnableContext: AppComposable<CoroutineContext> = AppComposable { it.syncDispatcher }
		}

	}

}