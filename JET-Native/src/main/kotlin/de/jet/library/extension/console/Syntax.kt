package de.jet.library.extension.console

import de.jet.library.console.ConsoleInput

fun Array<String>.processConsoleVariables() =
    ConsoleInput.processVariables(this)

fun Collection<String>.processConsoleVariables() =
    ConsoleInput.processVariables(this.toList())