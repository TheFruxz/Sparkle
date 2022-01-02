package de.jet.jvm.application.extension

import de.jet.jvm.extension.input.requestTerminalInterchangeInput
import de.jet.jvm.interchange.ConsoleInterchangeBranch
import kotlin.reflect.KFunction1

/**
 * Extension to easily create terminal input requests inside an application
 * @author Fruxz
 * @since 1.0
 */
object TerminalConsoleExtension : AppExtension<MutableList<ConsoleInterchangeBranch>, Unit, Unit> {

	override val identity = "TerminalConsole"
	override val parallelRunAllowed = false
	override val runtimeAccessor: KFunction1<MutableList<ConsoleInterchangeBranch>.() -> Unit, Unit> = this::runTerminal

	private fun runTerminal(process: (MutableList<ConsoleInterchangeBranch>.() -> Unit)) =
		requestTerminalInterchangeInput(*buildList(process).toTypedArray())

}