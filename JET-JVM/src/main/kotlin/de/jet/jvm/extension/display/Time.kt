package de.jet.jvm.extension.display

import de.jet.jvm.tool.timing.calendar.DisplayTime
import de.jet.jvm.tool.timing.calendar.DisplayTime.Format.SECONDS
import kotlin.time.Duration

/**
 * This function creates a [DisplayTime] from a [Duration] using its [Duration.inWholeSeconds] value.
 * @return [DisplayTime]
 * @author Fruxz
 * @since 1.0
 */
fun Duration.display() = DisplayTime(SECONDS, this.inWholeSeconds.toDouble())