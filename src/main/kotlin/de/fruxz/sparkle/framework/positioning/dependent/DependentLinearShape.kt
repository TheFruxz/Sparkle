package de.fruxz.sparkle.framework.positioning.dependent

import de.fruxz.sparkle.framework.extension.world.copy
import de.fruxz.sparkle.framework.extension.world.velocityTo
import de.fruxz.sparkle.framework.positioning.relative.LinearShape
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.util.Vector
import kotlin.math.abs
import kotlin.math.roundToInt

@Serializable
data class DependentLinearShape(
    @Contextual val fromLocation: Location,
    @Contextual val toLocation: Location,
) : DependentShape, LinearShape {

    override val length: Double = fromLocation.toVector().distance(toLocation.toVector())

    override val volume: Double = .0

    override val center: Location by lazy {
        Location(
            fromLocation.world,
            (fromLocation.x + toLocation.x) / 2,
            (fromLocation.y + toLocation.y) / 2,
            (fromLocation.z + toLocation.z) / 2
        )
    }

    override val fullHeight: Double by lazy {
        abs(fromLocation.y - toLocation.y)
    }

    override val fullWidth: Double by lazy {
        abs(fromLocation.x - toLocation.x)
    }

    override val fullDepth: Double by lazy {
        abs(fromLocation.z - toLocation.z)
    }

    override val blockLocations: List<Location> by lazy {
        var result = listOf<Location>()
        val directionVector = fromLocation velocityTo toLocation
        val directionVectorLength = directionVector.length()

        for (i in 0 until directionVectorLength.roundToInt()) {
            val location = fromLocation.toVector().add(directionVector.clone().multiply(i.toDouble()).divide(Vector(directionVectorLength, directionVectorLength, directionVectorLength))).toLocation(fromLocation.world)
            result += location
        }

        return@lazy result
    }

    override fun contains(vector: Vector) = false

    override fun contains(location: Location) = false

    override fun asShifted(toWorld: World): DependentShape = copy(
        fromLocation = fromLocation.copy(world = toWorld),
        toLocation = toLocation.copy(world = toWorld)
    )

}
