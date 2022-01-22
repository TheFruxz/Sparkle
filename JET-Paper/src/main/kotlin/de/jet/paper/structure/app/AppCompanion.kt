package de.jet.paper.structure.app

import de.jet.jvm.extension.forceCast
import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.jvm.tool.smart.identification.Identity
import de.jet.paper.app.JetCache

abstract class AppCompanion<T : App> : Identifiable<App> {

	/**
	 * This value represents the current [App] instance.
	 * It is extracted from the [JetCache.registeredApplications].
	 * If the cache, does not contain this [App] instance, a
	 * [NoSuchElementException] is thrown.
	 * The instance get registered during the [App.onLoad]/[App.preHello].
	 * @throws NoSuchElementException if the [App] instance is not registered in the [JetCache.registeredApplications]
	 * @author Fruxz
	 * @since 1.0
	 */
	val instance: T
		get() = JetCache.registeredApplications.first { it.identity == predictedIdentity.identity }.forceCast()

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
	abstract val predictedIdentity: Identity<App>

}