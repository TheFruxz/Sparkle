package de.jet.library.console

class ConsoleSyntax(
    private vararg val syntaxVariables: ConsoleSyntaxVariable
) {

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
    ) {

        fun checkVariableContent(value: String) = when (contentType) {
            ContentType.NONE -> SyntaxCheck.produce(value.isBlank(), "Content not allowed at -$variableName!")
            ContentType.WORD -> SyntaxCheck.produce(value.isNotEmpty() && value.split(" ").size == 1, "Only one word allowed at -$variableName!")
            ContentType.STRING -> SyntaxCheck.produce(value.isNotBlank(), "Content is required at -$variableName!")
            ContentType.INT -> SyntaxCheck.produce(value.toIntOrNull() != null, "Integer/Number is required at -$variableName!")
            ContentType.DOUBLE -> SyntaxCheck.produce(value.toDoubleOrNull() != null, "Double/Float is required at -$variableName!")
            ContentType.BOOLEAN -> SyntaxCheck.produce(value.toBooleanStrictOrNull() != null, "Boolean is required at -$variableName!")
        }.let {
            if (check(value)) return@let it else return@let SyntaxCheck.failed("Variable input check failed at -$variableName!")
        }

    }

    fun checkInputContent(input: Array<String>) =
        ConsoleInput.processVariables(input).all { variable ->
            syntaxVariables.any { syntax ->
                syntax.variableName == variable.key && !syntax.checkVariableContent(variable.value).failed
            }
        }

    fun buildSyntaxString() = syntaxVariables.joinToString(" ") {
        if (it.contentType == ContentType.NONE) {
            "[${it.variableName}]"
        } else
            "[${it.variableName} <${it.contentType.name}>]"
    }

    fun buildUsedVariables(input: Array<String>) =
        ConsoleInput.processVariables(input).filter { entry ->
            syntaxVariables.any { it.variableName == entry.key }
        }

}
