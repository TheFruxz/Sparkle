package de.jet.jvm.extension

import java.util.*

/**
 * Creates a new [UUID] as a [String].
 * @see UUID.randomUUID
 * @author Fruxz
 * @since 1.0
 */
fun UUID.randomString() = "${UUID.randomUUID()}"