package de.moltenKt.jvm.extension.input

import de.moltenKt.jvm.application.console.ArgumentInput

/**
 * Processes all console variables out of [this] [Array]<[String]>
 * @author Fruxz
 * @since 1.0
 */
fun Array<String>.processConsoleVariables() =
    ArgumentInput.processVariables(this)

/**
 * Processes all console variables out of [this] [Collection]<[String]>
 * @author Fruxz
 * @since 1.0
 */
fun Collection<String>.processConsoleVariables() =
    ArgumentInput.processVariables(this.toList())