package de.fruxz.sparkle.framework.infrastructure.app

import de.fruxz.ascend.extension.forceCast
import de.fruxz.sparkle.server.SparkleApp.Infrastructure
import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.sparkle.framework.util.identification.KeyedIdentifiable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import net.kyori.adventure.key.Key

abstract class AppCompanion<T : App> : KeyedIdentifiable<T> {

	/**
	 * This value represents the current [App] instance.
	 * It is extracted from the [SparkleCache.registeredApps].
	 * If the cache, does not contain this [App] instance, a
	 * [NoSuchElementException] is thrown.
	 * The instance get registered during the [App.onLoad]/[App.preHello].
	 * @throws NoSuchElementException if the [App] instance is not registered in the [SparkleCache.registeredApps]
	 * @author Fruxz
	 * @since 1.0
	 */
	val instance: T
		get() = SparkleCache.registeredApps.firstOrNull { it.identityKey == identityKey }?.forceCast<T>()
			?: error("This app is not registered inside the 'registeredApps' instance! Maybe Sparkle is shadowed inside the plugin using Sparkle? This would lead to this error!")

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
	override val identityKey: Key
		get() = Key.key(Infrastructure.SYSTEM_IDENTITY, predictedIdentity)

	/**
	 * This value represents the identity, which is expected
	 * to be the identity of the [App].
	 * This has to be the same identity, as the identity of
	 * the real [App]!
	 * @author Fruxz
	 * @since 1.0
	 */
	abstract val predictedIdentity: String

}