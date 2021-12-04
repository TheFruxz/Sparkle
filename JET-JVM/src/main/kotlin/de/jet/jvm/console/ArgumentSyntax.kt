@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package de.jet.jvm.console

import de.jet.jvm.annotation.NotPerfect
import de.jet.jvm.annotation.NotTested
import de.jet.jvm.annotation.NotWorking
import de.jet.jvm.console.ArgumentSyntax.ContentType.*
import de.jet.jvm.console.ArgumentSyntax.SyntaxCheck.Companion
import de.jet.jvm.extension.switchResult
import de.jet.jvm.tool.smart.identification.Identifiable

class ArgumentSyntax(
    private vararg val syntaxVariables: ConsoleSyntaxVariable
) {

    class ActivatedConsoleSyntax internal constructor(
        val consoleInput: Array<String>,
        val syntaxVariables: Array<out ConsoleSyntaxVariable>,
        val usedVariables: Map<String, String>,
    ) {

        fun isNoneOneUsed(variableName: String): Boolean {
            return usedVariables.containsKey(variableName) && usedVariables[variableName]?.isBlank() == true
        }

        fun isSet(variableName: String) =
            usedVariables.containsKey(variableName)

        fun getVariable(variableName: String): Any? {
            return usedVariables.toList().firstOrNull { it.first == variableName }?.second
        }

    }

    interface SyntaxCheck {

        val failed: Boolean
        val message: String

        companion object {

            internal fun failed(message: String) = object : SyntaxCheck {
                override val failed = true
                override val message = message
            }

            internal fun succeed() = object : SyntaxCheck {
                override val failed = false
                override val message = "success"
            }

            internal fun produce(success: Boolean, message: String = "") = when (success) {
                true -> succeed()
                else -> failed(message)
            }

        }

    }

    enum class ContentType {
        NONE,
        WORD,
        STRING,
        INT,
        LONG,
        BYTE,
        SHORT,
        DOUBLE,
        FLOAT,
        BOOLEAN,
    }

    data class ConsoleSyntaxVariable(
        val variableName: String,
        val optional: Boolean,
        val contentType: ContentType = NONE,
        val check: (String) -> Boolean = { true },
    ) : Identifiable<ConsoleSyntaxVariable> {

        override val identity = variableName

        fun checkVariableContent(value: String) = when (contentType) {
            NONE -> Companion.produce(
                value.isBlank(),
                "Content ('$value') not allowed at -$variableName! (isset-variable)"
            )
            WORD -> Companion.produce(
                value.isNotEmpty() && value.split(" ").size == 1,
                "Only one word (string with only one word) allowed at -$variableName!"
            )
            STRING -> Companion.produce(value.isNotBlank(), "Content is required at -$variableName!")
            INT -> Companion.produce(
                value.toIntOrNull() != null,
                "Integer/Number is required at -$variableName!"
            )
            LONG -> Companion.produce(
                value.toLongOrNull() != null,
                "Long/Number is required at -$variableName"
            )
            BYTE -> Companion.produce(
                value.toByteOrNull() != null,
                "Byte/Number is required at -$variableName"
            )
            SHORT -> Companion.produce(
                value.toShortOrNull() != null,
                "Short/Number is required at -$variableName"
            )
            DOUBLE -> Companion.produce(
                value.toDoubleOrNull() != null,
                "Double/Number is required at -$variableName!"
            )
            FLOAT -> Companion.produce(
                value.toFloatOrNull() != null,
                "Float/Number is required at -$variableName"
            )
            BOOLEAN -> Companion.produce(
                value.toBooleanStrictOrNull() != null,
                "Boolean is required at -$variableName!"
            )
        }.let {
            if (check(value)) return@let it else return@let Companion.failed("Variable input check failed at -$variableName!")
        }

    }

    private fun onlyRequiredVariables() =
        syntaxVariables.filter { !it.optional }

    @Suppress("unused")
    fun checkInputContent(input: Array<String>): Boolean {
        return ArgumentInput.processVariables(input).let { consoleInputVariables ->
            return@let consoleInputVariables.all { variable ->
                syntaxVariables.any { syntax ->
                    syntax.variableName == variable.key &&
                            !syntax.checkVariableContent(variable.value).failed
                }
            } && consoleInputVariables.keys
                .containsAll(onlyRequiredVariables()
                    .map { mapped -> mapped.variableName }
                ) && consoleInputVariables.keys.all { cIVkeys ->
                syntaxVariables.map { it.variableName }.contains(cIVkeys)
            }
        }
    }

    @NotPerfect
    @NotTested
    @NotWorking
    fun checkInputContentWithFeedback(input: Array<String>): SyntaxCheck {

        val processedVariables = ArgumentInput.processVariables(input)

        val syntaxVariableNames = syntaxVariables.map(ConsoleSyntaxVariable::variableName)

        syntaxVariableNames.let { syntaxVariableName ->

            processedVariables.keys.forEach { key ->
                if (!syntaxVariableName.contains(key))
                    return Companion.failed("Your variable -$key is not provided by the software!")
            }

            processedVariables.forEach { map ->
                syntaxVariables.first { it.variableName == map.key }.checkVariableContent(map.value).let { s ->
                    if (s.failed) {
                        return s
                    }
                }
            }

        }

        onlyRequiredVariables().forEach { required ->
            if (!processedVariables.containsKey(required.variableName)) {
                return Companion.failed("The variable ${required.variableName} is required to be used!")
            }
        }

        return Companion.succeed()
    }

    private fun buildSyntaxString() = syntaxVariables.joinToString(" ") {
        if (it.contentType == NONE) {
            "[-${it.variableName}]${it.optional.switchResult("?", "!!")}"
        } else
            "[[-${it.variableName}] <${it.contentType.name}>]${it.optional.switchResult("?", "!!")}"
    }

    fun buildUsedVariables(input: Array<String>) =
        ArgumentInput.processVariables(input).filter { entry ->
            syntaxVariables.any { it.variableName == entry.key }
        }

    // TODO: 15.11.2021 Additional issue feedback (what was wrong) is coming in future release
    @Suppress("unused")
    fun runWithSyntaxOrNotify(input: Array<String>, code: ActivatedConsoleSyntax.() -> Unit) {
        val checkInput = checkInputContentWithFeedback(input)
        if (!checkInput.failed) {
            code(ActivatedConsoleSyntax(input, syntaxVariables, buildUsedVariables(input)))
        } else
            println(buildString {
                appendLine(checkInput.message)
                append("Follow the SYNTAX: ${buildSyntaxString()}")
            })
    }

}
