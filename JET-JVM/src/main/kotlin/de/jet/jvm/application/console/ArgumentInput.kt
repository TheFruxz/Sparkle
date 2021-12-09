package de.jet.jvm.application.console

object ArgumentInput {

    fun processVariables(inputArray: Array<String>) =
        processVariables(input = inputArray.toList())

    fun processVariables(input: List<String>): Map<String, String> {
        return buildMap {
            input.withIndex().forEach { (index, value) ->
                if (value.startsWith("-")) {
                    val variableName = value.substring(1)
                    val contentPosition = index + 1
                    val contentContent = input.getOrNull(contentPosition)

                    if (input.size > contentPosition) {
                        if (!contentContent.isNullOrBlank() && !contentContent.startsWith("-")) {
                            if (contentContent.startsWith("\"")) {
                                val droppedContent = input.drop(index + 1)
                                val endOfLine = droppedContent.indexOfFirst { it.endsWith("\"") }

                                this[variableName] = droppedContent.subList(0, endOfLine+1).joinToString(" ").drop(1).dropLast(1)

                            } else {
                                this[variableName] = contentContent
                            }
                        } else {
                            this[variableName] = ""
                        }
                    } else {
                        this[variableName] = ""
                    }
                }
            }
        }
    }

}