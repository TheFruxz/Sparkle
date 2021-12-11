package de.jet.jvm.application.extension

import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.jvm.tool.smart.identification.Identity
import kotlin.reflect.KFunction1

interface AppExtension<RUNTIME, ACCESSOR_OUT> : Identifiable<AppExtension<RUNTIME, ACCESSOR_OUT>> {

	val extensionIdentity: Identity<AppExtension<RUNTIME, ACCESSOR_OUT>>
		get() = identityObject

	fun produceExtensionAccessorValue(): RUNTIME

	val parallelRunAllowed: Boolean

	val runtimeAccessor: KFunction1<RUNTIME.() -> ACCESSOR_OUT, ACCESSOR_OUT>

}