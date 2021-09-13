package de.jet.library.extension.display

import de.jet.library.tool.timing.calendar.DisplayTime
import de.jet.library.tool.timing.calendar.DisplayTime.Format.SECONDS
import kotlin.time.Duration

fun Duration.display() = DisplayTime(SECONDS, this.inWholeSeconds.toDouble())