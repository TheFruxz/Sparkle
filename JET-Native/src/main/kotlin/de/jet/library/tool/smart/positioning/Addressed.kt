package de.jet.library.tool.smart.positioning

data class Addressed<T>(
    val address: Address<T>,
    val value: T,
)
