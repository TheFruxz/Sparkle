package de.jet.library.extension.data

import de.jet.library.tool.devlang.JSON
import de.jet.library.tool.devlang.SQL
import de.jet.library.tool.devlang.YAML
import org.intellij.lang.annotations.Language
import java.net.URL

fun json(@Language("json") value: String) = JSON(value)

fun sql(@Language("sql") value: String) = SQL(value)

fun yaml(@Language("yaml") value: String) = YAML(value)

fun url(@Language("url") value: String) = URL(value)