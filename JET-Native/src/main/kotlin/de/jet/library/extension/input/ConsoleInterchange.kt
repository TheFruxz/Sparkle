package de.jet.library.extension.input

import de.jet.library.console.interchange.ConsoleInterchange
import de.jet.library.extension.collection.emptyString

fun buildConsoleInterchange(name: String, process: ConsoleInterchange.Builder.() -> Unit = { }): ConsoleInterchange {
    val builder = ConsoleInterchange.Builder(name, emptyString())
    builder.apply(process)
    return builder.produce()
}

fun requestTerminalInterchangeInput(vararg interchanges: ConsoleInterchange) {
    println("Type your interchange/command:")
    val input = readln()

    if (input.startsWith("help") || input.startsWith("?")) {

        println(buildString {

            println("--- HELP MENU --- --- ---")
            println("Here you can see all usable interchanges:")

            interchanges.forEach {
                println("- ${it.name}")
            }

            print("--- HELP MENU --- --- ---")

        })

        requestTerminalInterchangeInput(*interchanges)

    } else {

        val call = input.split(" ").let { searched ->
            return@let interchanges.firstOrNull { it.name == searched[0] }
        }

        if (call != null) {
            println("call: '${input.split(" ").drop(1).joinToString(" ")}'")

            if (!call.performInterchange(input.split(" ").drop(1).joinToString(" "))) {
                println("No response from interchange, seems that your input-syntax was wrong, try again!")
                requestTerminalInterchangeInput(*interchanges)
            }

        } else {
            println("No interchange called '${input.split(" ")[0]}' found, try again or enter 'help'!")
            requestTerminalInterchangeInput(*interchanges)
        }

    }

}