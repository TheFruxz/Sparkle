package de.jet.jvm.application.extension

import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.jvm.tool.smart.identification.Identity
import kotlin.reflect.KFunction1

interface AppExtension<RUNTIME, ACCESSOR_OUT, UNIT> : Identifiable<AppExtension<RUNTIME, ACCESSOR_OUT, UNIT>> {

	val extensionIdentity: Identity<AppExtension<RUNTIME, ACCESSOR_OUT, UNIT>>
		get() = identityObject

	val parallelRunAllowed: Boolean

	val runtimeAccessor: KFunction1<RUNTIME.() -> ACCESSOR_OUT, UNIT>

}