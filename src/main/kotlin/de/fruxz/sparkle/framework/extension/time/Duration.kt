package de.fruxz.sparkle.framework.extension.time

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

val Int.minecraftTicks: Duration
    get() = (this.toLong() * 50).milliseconds

val Long.minecraftTicks: Duration
    get() = (this * 50).milliseconds

val Double.minecraftTicks: Duration
    get() = (this * 50).milliseconds

val Float.minecraftTicks: Duration
    get() = (this * 50F).toDouble().milliseconds