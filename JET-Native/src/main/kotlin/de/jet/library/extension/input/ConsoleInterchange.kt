package de.jet.library.extension.input

import de.jet.library.console.interchange.ConsoleInterchange

fun buildConsoleInterchange(name: String, process: ConsoleInterchange.Builder.() -> Unit = { }): ConsoleInterchange {
    val builder = ConsoleInterchange.Builder(name)
    builder.apply(process)
    return builder.produce()
}