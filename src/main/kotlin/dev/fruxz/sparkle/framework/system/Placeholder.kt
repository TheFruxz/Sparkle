package dev.fruxz.sparkle.framework.system

import java.util.*

// TODO remove this, part of ascend & stacked

fun <T> Iterable<T>.toSortedSet(): SortedSet<T> {
    return when (this) {
        is SortedSet -> this
        else -> toSortedSet(compareBy { this.indexOf(it) })
    }
}

fun <T> Array<T>.toSortedSet(): SortedSet<T> =
    toSortedSet(compareBy { this.indexOf(it) })

annotation class StyledString