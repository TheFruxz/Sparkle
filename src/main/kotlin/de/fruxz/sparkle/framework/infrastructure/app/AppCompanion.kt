package de.fruxz.sparkle.framework.infrastructure.app

import dev.fruxz.ascend.extension.forceCast
import de.fruxz.sparkle.framework.identification.KeyedIdentifiable
import de.fruxz.sparkle.server.SparkleApp
import de.fruxz.sparkle.server.SparkleApp.Infrastructure
import de.fruxz.sparkle.server.SparkleCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import net.kyori.adventure.key.Key
import org.bukkit.Bukkit

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
			?: error("This app is not registered inside the 'registeredApps' instance! Maybe Sparkle is shadowed inside the plugin using Sparkle? This would lead to such an error!")

	/**
	 * This value represents the coroutine scope of the [App], that
	 * holds this instance of the [AppCompanion].
	 * It's a [SupervisorJob], so it can be used to cancel all coroutines
	 * and does not crash the application on heavy issues.
	 * @author Fruxz
	 * @since 1.0
	 */
	val coroutineScope: CoroutineScope by lazy {
		if (predictedIdentity == SparkleApp.predictedIdentity) {
			CoroutineScope(SupervisorJob())
		} else
			CoroutineScope(SupervisorJob() + SparkleApp.coroutineScope.coroutineContext)
	}

	/**
	 * This value represents the identity of this [App].
	 * The [instance] is used to get the [identity].
	 * @see instance
	 * @author Fruxz
	 * @since 1.0
	 */
	override val identityKey: Key by lazy {
		Key.key(Infrastructure.SYSTEM_IDENTITY, predictedIdentity.lowercase())
	}

	/**
	 * This value represents the expected identity of this [App].
	 * The identity is extracted from the plugins class and name.
	 * @author Fruxz
	 * @since 1.0
	 */
	val predictedIdentity: String by lazy {
		Bukkit.getPluginManager().plugins.firstOrNull { it.javaClass == this::class.java.declaringClass }?.name
			?: throw NoSuchElementException("Failed to retrieve the App instance of ${this::class.java.declaringClass.simpleName}!")
	}

}