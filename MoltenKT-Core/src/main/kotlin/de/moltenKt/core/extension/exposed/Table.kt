package de.moltenKt.core.extension.exposed

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table

/**
 * This function utilizes the [SchemaUtils.create] function to create a table.
 * @author Fruxz
 * @since 1.0
 */
fun Table.createTable() = SchemaUtils.create(this)