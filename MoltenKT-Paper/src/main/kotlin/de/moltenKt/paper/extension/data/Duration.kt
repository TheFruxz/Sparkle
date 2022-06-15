package de.moltenKt.paper.extension.data

import de.moltenKt.paper.extension.timing.minecraftTicks
import kotlin.time.Duration

val Int.ticks: Duration
	get() = minecraftTicks

val Long.ticks: Duration
	get() = minecraftTicks

val Double.ticks: Duration
	get() = minecraftTicks

val Float.ticks: Duration
	get() = minecraftTicks