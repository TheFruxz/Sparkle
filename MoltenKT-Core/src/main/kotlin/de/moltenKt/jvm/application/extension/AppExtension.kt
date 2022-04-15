package de.moltenKt.jvm.application.extension

import de.moltenKt.jvm.tool.smart.identification.Identifiable
import de.moltenKt.jvm.tool.smart.identification.Identity
import kotlin.reflect.KFunction1

/**
 * This interface defines the functionality of an application extension.
 * An application extension is a class that is used to extend the functionality
 * of an MoltenKT application with additional 'runnable' processing stuff.
 * @param RUNTIME the type of data used in the lambda *(<HERE>).-> Unit*
 * @param ACCESSOR_OUT the type of data, that the lambda returns *().-> <HERE>*
 * @param UNIT the type of data, that the function (not the lambda) returns, usually [Unit]
 * @author Fruxz
 * @since 1.0
 */
interface AppExtension<RUNTIME, ACCESSOR_OUT, UNIT> : Identifiable<AppExtension<RUNTIME, ACCESSOR_OUT, UNIT>> {

	/**
	 * The identity of this extension.
	 * @return the identity of this extension
	 * @author Fruxz
	 * @since 1.0
	 */
	val extensionIdentity: Identity<out AppExtension<RUNTIME, ACCESSOR_OUT, UNIT>>
		get() = identityObject

	/**
	 * This value defines, if this extension can be run multiple times parallel.
	 * @return true, if this extension can be run multiple times parallel, false otherwise
	 * @author Fruxz
	 * @since 1.0
	 */
	val parallelRunAllowed: Boolean

	/**
	 * This value defines the process, that is executed by this extension.
	 * @return the process, that is executed by this extension
	 * @author Fruxz
	 * @since 1.0
	 */
	val runtimeAccessor: KFunction1<RUNTIME.() -> ACCESSOR_OUT, UNIT>

}