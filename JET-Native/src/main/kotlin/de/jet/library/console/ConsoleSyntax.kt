package de.jet.library.console

import de.jet.library.extension.switchResult
import de.jet.library.tool.smart.identification.Identifiable

class ConsoleSyntax(
    private vararg val syntaxVariables: ConsoleSyntaxVariable
) {

    class ActivatedConsoleSyntax internal constructor(
        val consoleInput: Array<String>,
        val syntaxVariables: Array<out ConsoleSyntaxVariable>,
        val usedVariables: Map<String, String>,
    ) {

        fun isNoneOneUsed(variableName: String): Boolean {
            return usedVariables[variableName] == ""
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
        DOUBLE,
        BOOLEAN,
    }

    data class ConsoleSyntaxVariable(
        val variableName: String,
        val optional: Boolean,
        val contentType: ContentType = ContentType.NONE,
        val check: (String) -> Boolean = { true },
    ) : Identifiable<ConsoleSyntaxVariable> {

        override val identity = variableName

        fun checkVariableContent(value: String) = when (contentType) {
            ContentType.NONE -> SyntaxCheck.produce(value.isBlank(), "Content not allowed at -$variableName!")
            ContentType.WORD -> SyntaxCheck.produce(
                value.isNotEmpty() && value.split(" ").size == 1,
                "Only one word allowed at -$variableName!"
            )
            ContentType.STRING -> SyntaxCheck.produce(value.isNotBlank(), "Content is required at -$variableName!")
            ContentType.INT -> SyntaxCheck.produce(
                value.toIntOrNull() != null,
                "Integer/Number is required at -$variableName!"
            )
            ContentType.DOUBLE -> SyntaxCheck.produce(
                value.toDoubleOrNull() != null,
                "Double/Float is required at -$variableName!"
            )
            ContentType.BOOLEAN -> SyntaxCheck.produce(
                value.toBooleanStrictOrNull() != null,
                "Boolean is required at -$variableName!"
            )
        }.let {
            if (check(value)) return@let it else return@let SyntaxCheck.failed("Variable input check failed at -$variableName!")
        }

    }

    fun onlyRequiredVariables() =
        syntaxVariables.filter { !it.optional }

    fun checkInputContent(input: Array<String>): Boolean {
        return ConsoleInput.processVariables(input).let { consoleInputVariables ->
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

    fun buildSyntaxString() = syntaxVariables.joinToString(" ") {
        if (it.contentType == ContentType.NONE) {
            "[-${it.variableName}]${it.optional.switchResult("?", "!!")}"
        } else
            "[[-${it.variableName}] <${it.contentType.name}>]${it.optional.switchResult("?", "!!")}"
    }

    fun buildUsedVariables(input: Array<String>) =
        ConsoleInput.processVariables(input).filter { entry ->
            syntaxVariables.any { it.variableName == entry.key }
        }

    // TODO: 15.11.2021 Additional issue feedback (what was wrong) is coming in future release
    fun runWithSyntaxOrNotify(input: Array<String>, code: ActivatedConsoleSyntax.() -> Unit) {
        if (checkInputContent(input)) {
            code(ActivatedConsoleSyntax(input, syntaxVariables, buildUsedVariables(input)))
        } else
            println(buildString {
                append("Follow the SYNTAX: ${buildSyntaxString()}")
            })
    }

}
