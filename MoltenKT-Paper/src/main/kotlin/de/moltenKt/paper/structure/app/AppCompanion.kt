package de.moltenKt.paper.structure.app

import de.moltenKt.core.extension.forceCast
import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.core.tool.smart.identification.Identity
import de.moltenKt.paper.app.MoltenCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

abstract class AppCompanion<T : App> : Identifiable<App> {

	/**
	 * This value represents the current [App] instance.
	 * It is extracted from the [MoltenCache.registeredApps].
	 * If the cache, does not contain this [App] instance, a
	 * [NoSuchElementException] is thrown.
	 * The instance get registered during the [App.onLoad]/[App.preHello].
	 * @throws NoSuchElementException if the [App] instance is not registered in the [MoltenCache.registeredApps]
	 * @author Fruxz
	 * @since 1.0
	 */
	val instance: T
		get() = MoltenCache.registeredApps.first { it.identity == predictedIdentity.identity }.forceCast()

	/**
	 * This value represents the coroutine scope of the [App], that
	 * holds this instance of the [AppCompanion].
	 * It's a [SupervisorJob], so it can be used to cancel all coroutines
	 * and does not crash the application on heavy issues.
	 * @author Fruxz
	 * @since 1.0
	 */
	val coroutineScope = CoroutineScope(SupervisorJob())

	/**
	 * This value represents the identity of this [App].
	 * The [instance] is used to get the [identity].
	 * @see instance
	 * @author Fruxz
	 * @since 1.0
	 */
	final override val identity: String
		get() = instance.identity

	/**
	 * This value represents the identity, which is expected
	 * to be the identity of the [App].
	 * This has to be the same identity, as the identity of
	 * the real [App]!
	 * @author Fruxz
	 * @since 1.0
	 */
	abstract val predictedIdentity: Identity<T>

}