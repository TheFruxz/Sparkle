package de.fruxz.sparkle.extension.data

import de.fruxz.sparkle.extension.timing.minecraftTicks
import kotlin.time.Duration

val Int.ticks: Duration
	get() = minecraftTicks

val Long.ticks: Duration
	get() = minecraftTicks

val Double.ticks: Duration
	get() = minecraftTicks

val Float.ticks: Duration
	get() = minecraftTicks