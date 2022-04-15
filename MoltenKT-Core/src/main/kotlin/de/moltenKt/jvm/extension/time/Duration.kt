package de.moltenKt.jvm.extension.time

import kotlin.time.Duration

/**
 * Takes the [Duration.inWholeMilliseconds] and divides it by 50,
 * to represents the duration in minecraft-ticks (20 ticks in a second).
 * @author Fruxz
 * @since 1.0
 */
val Duration.inWholeMinecraftTicks: Long
	get() = this.inWholeMilliseconds / 50