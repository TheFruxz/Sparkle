package dev.fruxz.sparkle.framework.util

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

val Long.ticks: Duration
    get() = (this * 50).milliseconds

val Int.ticks: Duration
    get() = toLong().ticks