package de.jet.library.structure.service

import de.jet.library.tool.smart.Identifiable
import de.jet.library.tool.smart.Logging
import de.jet.library.tool.tasky.Tasky
import de.jet.library.tool.tasky.TemporalAdvice

interface Service : Identifiable<Service>, Logging {

	val temporalAdvice: TemporalAdvice

	val process: Tasky.() -> Unit

}