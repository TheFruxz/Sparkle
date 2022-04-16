package de.moltenKt.core.extension.display

import de.moltenKt.core.tool.timing.clock.DisplayTime
import de.moltenKt.core.tool.timing.clock.DisplayTime.Format.SECONDS
import kotlin.time.Duration

/**
 * This function creates a [DisplayTime] from a [Duration] using its [Duration.inWholeSeconds] value.
 * @return [DisplayTime]
 * @author Fruxz
 * @since 1.0
 */
fun Duration.display() = DisplayTime(SECONDS, this.inWholeSeconds.toDouble())