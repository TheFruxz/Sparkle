package de.jet.jvm.application.extension

import de.jet.jvm.application.console.interchange.ConsoleInterchange
import de.jet.jvm.extension.input.requestTerminalInterchangeInput
import kotlin.reflect.KFunction1

/**
 * Extension to easily create terminal input requests inside a application
 * @author Fruxz
 * @since 1.0
 */
object TerminalConsoleExtension : AppExtension<MutableList<ConsoleInterchange>, Unit, Unit> {

	override val identity = "TerminalConsole"
	override val parallelRunAllowed = false
	override val runtimeAccessor: KFunction1<MutableList<ConsoleInterchange>.() -> Unit, Unit> = this::runTerminal

	private fun runTerminal(process: (MutableList<ConsoleInterchange>.() -> Unit)) =
		requestTerminalInterchangeInput(*buildList(process).toTypedArray())

}