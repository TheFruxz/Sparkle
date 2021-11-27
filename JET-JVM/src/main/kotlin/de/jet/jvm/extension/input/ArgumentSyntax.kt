package de.jet.jvm.extension.input

import de.jet.jvm.console.ArgumentInput

fun Array<String>.processConsoleVariables() =
    ArgumentInput.processVariables(this)

fun Collection<String>.processConsoleVariables() =
    ArgumentInput.processVariables(this.toList())