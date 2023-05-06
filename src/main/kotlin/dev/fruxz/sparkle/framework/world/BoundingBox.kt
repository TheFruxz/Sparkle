package dev.fruxz.sparkle.framework.world

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector

/**
 * This function creates a copy of [this] [BoundingBox],
 * its parameters are filled with [this] data by default,
 * but can be changed on demand, like the copy functions
 * of data-classes.
 * @author Fruxz
 * @since 1.0
 */
fun BoundingBox.copy(
    fromVector: Vector = this.min,
    toVector: Vector = this.max,
) = BoundingBox.of(fromVector, toVector)

/**
 * This function returns, if the [location]'s [Location.x], [Location.y] and
 * [Location.z] is contained inside [this] [BoundingBox].
 * @author Fruxz
 * @since 1.0
 */
operator fun BoundingBox.contains(location: Location) = this.contains(location.toVector())

/**
 * This function returns, if the [Location.x], [Location.y] and [Location.z]
 * of the [Entity] is contained inside [this] [BoundingBox].
 * @author Fruxz
 * @since 1.0
 */
operator fun BoundingBox.contains(entity: Entity) = entity.location in this

/**
 * This function returns, if the [Location.x], [Location.y] and [Location.z]
 * of the [Block] is contained inside [this] [BoundingBox].
 * @author Fruxz
 * @since 1.0
 */
operator fun BoundingBox.contains(block: Block) = block.location in this