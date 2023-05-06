package dev.fruxz.sparkle.framework.world

import org.bukkit.util.Vector

/**
 * This function creates a copy of [this] [Vector],
 * its parameters are filled with [this] data by default,
 * but can be changed on demand, like the copy functions
 * of data-classes.
 * @author Fruxz
 * @since 1.0
 */
fun Vector.copy(
    x: Number = this.x,
    y: Number = this.y,
    z: Number = this.z,
) = Vector(x.toDouble(), y.toDouble(), z.toDouble())

/**
 * This function computes the required velocity, to reach the [destination]
 * from [this] source position.
 * @author Fruxz
 * @since 1.0
 */
infix fun Vector.velocityTo(destination: Vector) = destination.subtract(this)