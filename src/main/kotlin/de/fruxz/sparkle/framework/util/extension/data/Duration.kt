package de.fruxz.sparkle.framework.util.extension.data

import de.fruxz.sparkle.framework.util.extension.time.minecraftTicks
import kotlin.time.Duration

val Int.ticks: Duration
	get() = minecraftTicks

val Long.ticks: Duration
	get() = minecraftTicks

val Double.ticks: Duration
	get() = minecraftTicks

val Float.ticks: Duration
	get() = minecraftTicks