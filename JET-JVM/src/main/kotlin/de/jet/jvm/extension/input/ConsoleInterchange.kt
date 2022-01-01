@file:Suppress("unused")

package de.jet.jvm.extension.input

import de.jet.jvm.interchange.ConsoleInterchangeBranch
import de.jet.jvm.interchange.ConsoleInterchangeBranchType
import de.jet.jvm.interchange.ConsoleInterchangeConfiguration
import de.jet.jvm.tool.smart.positioning.Address

/**
 * Constructs a new [ConsoleInterchange] using its [ConsoleInterchange.Builder].
 * @param name of the future interchange
 * @param process the modification of the [ConsoleInterchange.Builder] state
 * @author Fruxz
 * @since 1.0
 */
fun buildConsoleInterchange(name: String, process: ConsoleInterchangeBranch.() -> Unit = { }) =
    ConsoleInterchangeBranch(name, Address.address(name), emptyList(), ConsoleInterchangeBranchType.OBJECT, null).apply(process)

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
fun requestTerminalInterchangeInput(consoleInterchangeConfiguration: ConsoleInterchangeConfiguration) {
    with(consoleInterchangeConfiguration) {

        assert(interchanges.all { it.path.isRoot }) { "Not all branches are roots!" }

        println(buildString {
            appendLine("Welcome to the custom JET JVM Console Interchange Console!")
            append("Enter interchange/command, 'help' or 'exit':")
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
            println("Okay, bye!")
            return
        } else {

            if (!consoleInterchangeConfiguration.executeCommand(input)) {
                println("No response from interchange, seems that your input-syntax was wrong, try again!")
            }

        }

    }

    requestTerminalInterchangeInput(consoleInterchangeConfiguration)

}