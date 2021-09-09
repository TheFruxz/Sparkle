package de.jet.library.extension.data

import de.jet.library.tool.devlang.JSON
import de.jet.library.tool.devlang.SQL
import de.jet.library.tool.devlang.YAML
import org.intellij.lang.annotations.Language

fun json(@Language("json") value: String) = JSON(value)

fun sql(@Language("sql") value: String) = SQL(value)

fun yaml(@Language("yaml") value: String) = YAML(value)