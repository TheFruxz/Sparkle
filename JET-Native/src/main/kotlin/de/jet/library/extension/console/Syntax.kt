package de.jet.library.extension.console

import de.jet.library.console.ArgumentInput

fun Array<String>.processConsoleVariables() =
    ArgumentInput.processVariables(this)

fun Collection<String>.processConsoleVariables() =
    ArgumentInput.processVariables(this.toList())