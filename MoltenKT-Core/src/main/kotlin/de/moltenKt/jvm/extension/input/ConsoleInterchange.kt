@file:Suppress("unused")

package de.moltenKt.jvm.extension.input

import de.moltenKt.jvm.interchange.ConsoleInterchangeBranch
import de.moltenKt.jvm.interchange.ConsoleInterchangeBranchType
import de.moltenKt.jvm.interchange.ConsoleInterchangeConfiguration
import de.moltenKt.jvm.tool.smart.positioning.Address

/**
 * Constructs a new [ConsoleInterchangeBranch].
 * @param name of the future interchange
 * @param process the modification of the [ConsoleInterchangeBranch] state
 * @author Fruxz
 * @since 1.0
 */
fun buildConsoleInterchange(name: String, process: ConsoleInterchangeBranch.() -> Unit = { }) =
	ConsoleInterchangeBranch(name, Address.address(name), emptyList(), ConsoleInterchangeBranchType.OBJECT, null).apply(
		process
	)

fun requestTerminalInterchangeInput(vararg interchanges: ConsoleInterchangeBranch) =
	requestTerminalInterchangeInput(ConsoleInterchangeConfiguration(interchanges.toList()))

/**
 * Requests an input from the user, like a terminal input,
 * but only give the given [interchanges] as possible
 * input and syntax.
 * @param interchanges the possible input interchanges
 * @author Fruxz
 * @since 1.0
 */
fun requestTerminalInterchangeInput(consoleInterchangeConfiguration: ConsoleInterchangeConfiguration, run: Int = 1) {
	val border = buildString {
		append("> ")
		repeat(90) { append('=') }
		append(" <")
	}

	with(consoleInterchangeConfiguration) {

		assert(interchanges.all { it.address.isRoot }) { "Not all branches are roots!" }

		println(buildString {
			if (run == 1) appendLine("Welcome to the custom MoltenKT-Core Console Interchange Console!")
			append("[>] Enter interchange/command, 'help' or 'exit':")
		})

		val input = readln()

		if (input.startsWith("help")) {

			println(buildString {

				appendLine("--- HELP MENU --- --- ---")
				appendLine("Here you can see all usable interchanges:")

				interchanges.forEach {
					appendLine("- ${it.identity}")
				}

				append("--- HELP MENU --- --- ---")

			})

		} else if (input.startsWith("exit")) {
			println("[!] Okay, bye!")
			return
		} else {

			fun printBorder() = println(buildString {
				appendLine()
				appendLine(border)
			})

			printBorder()

			if (!consoleInterchangeConfiguration.executeCommand(input)) {
				println("[!] No response from interchange, seems that your input-syntax was wrong, try again!")
			}

			printBorder()

		}

	}

	requestTerminalInterchangeInput(consoleInterchangeConfiguration, run + 1)

}