package de.jet.library.extension.display

import de.jet.library.tool.timing.calendar.DisplayTime
import de.jet.library.tool.timing.calendar.DisplayTime.Format.SECONDS
import org.jetbrains.annotations.ApiStatus.Experimental
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Experimental
fun Duration.display() = DisplayTime(SECONDS, this.inWholeSeconds.toDouble())