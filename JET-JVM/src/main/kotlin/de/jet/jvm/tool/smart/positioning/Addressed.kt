@file:Suppress("unused")

package de.jet.jvm.tool.smart.positioning

/**
 * A positioned element with an address.
 * Kinda the same as a withIndex() in a list.
 * @param address the address of the element
 * @param value the value of the element
 * @author Fruxz
 * @since 1.0
 */
data class Addressed<T>(
    val address: Address<T>,
    val value: T,
)
