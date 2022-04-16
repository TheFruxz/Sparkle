package de.moltenKt.unfold.extension

import net.kyori.adventure.text.ComponentLike
import kotlin.time.Duration
import kotlin.time.toJavaDuration
import net.kyori.adventure.title.Title.Times as AdventureTimes
import net.kyori.adventure.title.Title as AdventureTitle

fun Title(
    title: ComponentLike,
    subtitle: ComponentLike,
    times: AdventureTimes? = null
) = AdventureTitle.title(title.asComponent(), subtitle.asComponent(), times)

fun Title(
    styledTitle: String,
    styledSubtitle: String,
    times: AdventureTimes? = null
) = AdventureTitle.title(styledTitle.asStyledComponent, styledSubtitle.asStyledComponent, times)

fun Times(
    fadeIn: Duration,
    stay: Duration,
    fadeOut: Duration
) = AdventureTimes.times(fadeIn.toJavaDuration(), stay.toJavaDuration(), fadeOut.toJavaDuration())