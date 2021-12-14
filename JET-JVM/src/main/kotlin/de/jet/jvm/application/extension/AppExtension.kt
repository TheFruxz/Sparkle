package de.jet.jvm.application.extension

import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.jvm.tool.smart.identification.Identity
import kotlin.reflect.KFunction1

/**
 * This interface defines the functionality of an application extension.
 * An application extension is a class that is used to extend the functionality
 * of an JET application with additional 'runnables' processing stuff.
 * @param RUNTIME the type of data used in the lambda *(<HERE>).-> Unit*
 * @param ACCESSOR_OUT the type of data, that the lambda returns *().-> <HERE>*
 * @param UNIT the type of data, that the function (not the lambda) returns, usually [Unit]
 * @author Fruxz
 * @since 1.0
 */
interface AppExtension<RUNTIME, ACCESSOR_OUT, UNIT> : Identifiable<AppExtension<RUNTIME, ACCESSOR_OUT, UNIT>> {

	val extensionIdentity: Identity<AppExtension<RUNTIME, ACCESSOR_OUT, UNIT>>
		get() = identityObject

	val parallelRunAllowed: Boolean

	val runtimeAccessor: KFunction1<RUNTIME.() -> ACCESSOR_OUT, UNIT>

}