package de.jet.paper.tool.position

import de.jet.jvm.extension.math.difference
import de.jet.jvm.tool.smart.Producible
import de.jet.paper.extension.paper.directionVectorVelocity
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector

data class LocationBox(
	var first: Location,
	var second: Location,
) : Producible<BoundingBox> {

	constructor(both: Location) : this(both, both)

	constructor(locations: Pair<Location, Location>) : this(locations.first, locations.second)

	constructor(locationEntry: Map.Entry<Location, Location>) : this(locationEntry.key, locationEntry.value)

	constructor(vararg locations: Location) : this(locations.first(), locations.last())

	val center: Vector
		get() = produce().center

	fun contains(vector: Vector) = produce().contains(vector)

	fun contains(location: Location) = contains(location.toVector())

	fun contains(entity: Entity) = contains(entity.location)

	fun contains(player: Player) = contains(player as Entity)

	fun contains(block: Block) = contains(block.location)

	fun updateLocations(firstLocation: Location, secondLocation: Location) = apply {
		updateFirstLocation(newLocation = firstLocation)
		updateLastLocation(newLocation = secondLocation)
	}

	fun updateLocations(locations: Pair<Location, Location>) =
		updateLocations(firstLocation = locations.first, secondLocation = locations.second)

	fun updateFirstLocation(newLocation: Location) = apply {
		first = newLocation
	}

	fun updateLastLocation(newLocation: Location) = apply {
		second = newLocation
	}

	val blockVolume: Double
		get() = (first.x.difference(second.x)+1) * (first.y.difference(second.y)+1) * (first.z.difference(second.z)+1)

	val distance: Double
		get() = first.distance(second)

	val directionVelocity: Vector
		get() = directionVectorVelocity(first, second)

	override fun produce() = BoundingBox.of(first, second)

}

/**
 * This function creates a new [LocationBox] object, representing
 * a range from one to another [Location]. This function is defined
 * as a [rangeTo] operator function. This function uses the [this]
 * [Location] as the first [Location] ([LocationBox.component1])
 * and the [other] [Location] ([LocationBox.second]) as the
 * second [Location] inside the [LocationBox].
 * @param other is the second location used to define the range.
 * @return a new [LocationBox] object.
 * @see LocationBox
 * @see Location
 * @see rangeTo
 * @author Fruxz
 * @since 1.0
 */
operator fun Location.rangeTo(other: Location) =
	LocationBox(this to other)
