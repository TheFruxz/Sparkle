package dev.fruxz.sparkle.framework.util.cache

import dev.fruxz.sparkle.framework.marker.SparkleDSL
import kotlin.time.Duration

@SparkleDSL
fun <T> cached(
    keep: CachedProperty.CacheDuration = CachedProperty.CacheDuration.DEFAULT,
    default: () -> T
) = CachedProperty(default, keep)

@SparkleDSL
fun <T> cached(
    keep: Duration,
    default: () -> T
) = CachedProperty(default, keep)