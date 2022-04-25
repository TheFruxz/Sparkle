package de.moltenKt.paper.tool.position

import de.moltenKt.paper.extension.paper.directionVectorVelocity
import de.moltenKt.paper.extension.paper.toSimpleLocation
import de.moltenKt.paper.tool.display.world.SimpleLocation
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.util.Vector
import kotlin.math.abs
import kotlin.math.roundToInt

data class LinearShape(
    val fromLocation: SimpleLocation,
    val toLocation: SimpleLocation,
) : Shape {

    override val volume: Double = .0

    override val center: SimpleLocation by lazy {
        SimpleLocation(
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

    override val blockLocations: List<SimpleLocation> by lazy {
        val result = mutableListOf<SimpleLocation>()
        val directionVector = directionVectorVelocity(fromLocation.toVector(), toLocation.toVector())
        val directionVectorLength = directionVector.length()

        for (i in 0 until directionVectorLength.roundToInt()) {
            val location = fromLocation.toVector().add(directionVector.clone().multiply(i.toDouble()).divide(Vector(directionVectorLength, directionVectorLength, directionVectorLength))).toLocation(fromLocation.bukkitWorld).toSimpleLocation()
            result.add(location)
        }

        return@lazy result
    }

    override fun contains(vector: Vector) = false

    override fun contains(location: Location) = false

    override fun asShifted(toWorld: World): Shape = copy(
        fromLocation = fromLocation.copy(world = toWorld.name),
        toLocation = toLocation.copy(world = toWorld.name)
    )

}
