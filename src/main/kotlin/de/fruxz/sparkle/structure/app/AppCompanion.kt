package de.fruxz.sparkle.structure.app

import de.fruxz.ascend.extension.forceCast
import de.fruxz.ascend.extension.tryOrNull
import de.fruxz.ascend.tool.smart.identification.Identifiable
import de.fruxz.ascend.tool.smart.identification.Identity
import de.fruxz.sparkle.app.MoltenApp
import de.fruxz.sparkle.app.MoltenApp.Infrastructure
import de.fruxz.sparkle.app.MoltenCache
import de.fruxz.sparkle.tool.smart.KeyedIdentifiable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Namespaced

abstract class AppCompanion<T : App> : KeyedIdentifiable<T> {

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
		get() = MoltenCache.registeredApps.firstOrNull { it.identityKey == identityKey }?.forceCast<T>()
			?: error("This app is not registered inside the 'registeredApps' instance! Maybe MoltenKT-Paper is shadowed inside the plugin using MoltenKT? This would lead to this error!")

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