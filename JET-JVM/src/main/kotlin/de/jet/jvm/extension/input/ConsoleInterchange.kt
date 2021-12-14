@file:Suppress("unused")

package de.jet.jvm.extension.input

import de.jet.jvm.application.console.interchange.ConsoleInterchange
import de.jet.jvm.extension.collection.emptyString

/**
 * Constructs a new [ConsoleInterchange] using its [ConsoleInterchange.Builder].
 * @param name of the future interchange
 * @param process the modification of the [ConsoleInterchange.Builder] state
 * @author Fruxz
 * @since 1.0
 */
fun buildConsoleInterchange(name: String, process: ConsoleInterchange.Builder.() -> Unit = { }): ConsoleInterchange {
    val builder = ConsoleInterchange.Builder(name, emptyString())
    builder.apply(process)
    return builder.produce()
}

/**
 * Requests an input from the user, like a terminal input,
 * but only give the given [interchanges] as possible
 * input and syntax.
 * @param interchanges the possible input interchanges
 * @author Fruxz
 * @since 1.0
 */
fun requestTerminalInterchangeInput(vararg interchanges: ConsoleInterchange) {
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
                appendLine("- ${it.name}")
            }

            append("--- HELP MENU --- --- ---")

        })

    } else if (input.startsWith("exit")) {
        println("Okay, bye!")
        return
    } else {

        val call = input.split(" ").let { searched ->
            return@let interchanges.firstOrNull { it.name == searched[0] }
        }

        if (call != null) {
            val inputParameters = input.split(" ").drop(1)
            if (!call.performInterchange(inputParameters.joinToString(" "))) {
                val syntaxIssueReaction = call.syntaxIssue

                if (syntaxIssueReaction != null) {
                    syntaxIssueReaction(inputParameters)
                } else {
                    println("No response from interchange, seems that your input-syntax was wrong, try again!")
                }

            }

        } else {
            println("No interchange called '${input.split(" ")[0]}' found, try again or enter 'help'!")
        }

    }

    requestTerminalInterchangeInput(*interchanges)

}