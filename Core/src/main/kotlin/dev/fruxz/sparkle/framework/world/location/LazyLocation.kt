package dev.fruxz.sparkle.framework.world.location

import dev.fruxz.sparkle.framework.world.Location
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.util.Vector

@Serializable
data class LazyLocation(
    val world: String,
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float = 0F,
    val pitch: Float = 0F,
) {

    constructor(
        world: World,
        x: Double,
        y: Double,
        z: Double,
        yaw: Float = 0F,
        pitch: Float = 0F,
    ) : this(world.name, x, y, z, yaw, pitch)

    val asLocation: Location by lazy {
        Location(world, x, y, z, yaw, pitch)
    }

    val asVector: Vector by lazy {
        Vector(x, y, z)
    }

}
