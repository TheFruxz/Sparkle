package de.jet.jvm.extension.data

import de.jet.jvm.tool.devlang.JSON
import de.jet.jvm.tool.devlang.SQL
import de.jet.jvm.tool.devlang.YAML
import org.intellij.lang.annotations.Language
import java.net.URL

/**
 * Creates a new instance of [JSON] with the code inside.
 * @param value The code to be used.
 * @return The new instance of [JSON].
 * @author Fruxz
 * @since 1.0
 */
fun json(@Language("json") value: String) = JSON(value)

/**
 * Creates a new instance of [SQL] with the code inside.
 * @param value The code to be used.
 * @return The new instance of [SQL].
 * @author Fruxz
 * @since 1.0
 */
fun sql(@Language("sql") value: String) = SQL(value)

/**
 * Creates a new instance of [YAML] with the code inside.
 * @param value The code to be used.
 * @return The new instance of [YAML].
 * @author Fruxz
 * @since 1.0
 */
fun yaml(@Language("yaml") value: String) = YAML(value)

/**
 * Creates a new instance of [URL] containing the
 * given [value] url as the content.
 * @return The new instance of [URL].
 * @author Fruxz
 * @since 1.0
 */
fun url(@Language("url") value: String) = URL(value)