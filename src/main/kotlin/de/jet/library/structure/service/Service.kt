package de.jet.library.structure.service

import de.jet.library.tool.smart.Identifiable
import de.jet.library.tool.smart.Logging
import de.jet.library.tool.timing.tasky.Tasky
import de.jet.library.tool.timing.tasky.TemporalAdvice

interface Service : Identifiable<Service>, Logging {

	override val identity: String
		get() = "${this::class.simpleName}"

	val temporalAdvice: TemporalAdvice

	val process: Tasky.() -> Unit

}