package de.jet.paper.tool.position

import de.jet.jvm.extension.math.difference
import de.jet.jvm.tool.smart.Producible
import de.jet.paper.extension.paper.directionVectorVelocity
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector

/**
 * This data class represents a computer generated box, defined by 2 points.
 * These 2 points are locations and represent the corners of the box.
 * The box is defined by the difference of the two points, and each point
 * can be changed independently, without breaking the computational part.
 * This class heavily uses the [BoundingBox] class from the Bukkit API,
 * and assists the usability of this Bukkit utility class.
 * @param first The first point/corner of the box.
 * @param second The second point/corner of the box.
 * @author Fruxz
 * @since 1.0
 */
data class LocationBox(
	var first: Location,
	var second: Location,
) : Producible<BoundingBox> {

	constructor(both: Location) : this(both, both)

	constructor(locations: Pair<Location, Location>) : this(locations.first, locations.second)

	constructor(locationEntry: Map.Entry<Location, Location>) : this(locationEntry.key, locationEntry.value)

	constructor(vararg locations: Location) : this(locations.first(), locations.last())

	/**
	 * This function returns the center of the box.
	 * The computational part is done by the [BoundingBox],
	 * and the result is returned as a [Vector].
	 * @return The center of the box.
	 * @see BoundingBox.getCenter
	 * @author Fruxz
	 * @since 1.0
	 */
	val center: Vector
		get() = produce().center

	/**
	 * This function returns the center of the box.
	 * The computational part is done by the [BoundingBox],
	 * and the result is returned as a [Location].
	 * @return The center of the box.
	 * @see center
	 * @see BoundingBox.getCenter
	 * @author Fruxz
	 * @since 1.0
	 */
	val centerLocation: Location
		get() = produce().center.toLocation(first.world)

	/**
	 * This function returns, if the provided [vector] is
	 * located inside this box.
	 * The computational part is done by the [BoundingBox],
	 * and the result is returned as a [Boolean].
	 * @param vector The vector to check.
	 * @return If the vector is inside the box.
	 * @see BoundingBox.contains
	 * @author Fruxz
	 * @since 1.0
	 */
	fun contains(vector: Vector) = produce().contains(vector)

	/**
	 * This function returns, if the provided [location] is
	 * located inside this box (not observe world).
	 * The computational part is done by the [BoundingBox],
	 * and the result is returned as a [Boolean].
	 * @param location The location to check.
	 * @return If the location is inside the box.
	 * @see contains
	 * @author Fruxz
	 * @since 1.0
	 */
	fun contains(location: Location) = contains(location.toVector())

	/**
	 * This function returns, if the provided [entity] is
	 * located inside this box (not observe world).
	 * The computational part is done by the [BoundingBox],
	 * and the result is returned as a [Boolean].
	 * @param entity The entity to check.
	 * @return If the entity is inside the box.
	 * @see contains
	 * @author Fruxz
	 * @since 1.0
	 */
	fun contains(entity: Entity) = contains(entity.location)

	/**
	 * This function returns, if the provided [block] is
	 * located inside this box (not observe world).
	 * The computational part is done by the [BoundingBox],
	 * and the result is returned as a [Boolean].
	 * @param block The block to check.
	 * @return If the block is inside the box.
	 * @see contains
	 * @author Fruxz
	 * @since 1.0
	 */
	fun contains(block: Block) = contains(block.location)

	/**
	 * This function changes the [first] and [second] location
	 * of this box, by replacing them with the provided
	 * [firstLocation] and [secondLocation].
	 * This function returns itself, but updated.
	 * @param firstLocation The first location of the box.
	 * @param secondLocation The second location of the box.
	 * @return This box, updated.
	 * @see updateFirstLocation
	 * @see updateSecondLocation
	 * @author Fruxz
	 * @since 1.0
	 */
	fun updateLocations(firstLocation: Location, secondLocation: Location) = apply {
		updateFirstLocation(newLocation = firstLocation)
		updateSecondLocation(newLocation = secondLocation)
	}

	/**
	 * This function changes the [first] and [second] location
	 * of this box, by replacing them with the provided
	 * two [locations].
	 * This function returns itself, but updated.
	 * @param locations The locations of the new box.
	 * @return This box, updated.
	 * @see updateLocations
	 * @author Fruxz
	 * @since 1.0
	 */
	fun updateLocations(locations: Pair<Location, Location>) =
		updateLocations(firstLocation = locations.first, secondLocation = locations.second)

	/**
	 * This function changes the [first] location,
	 * by replacing it with the provided [newLocation].
	 * This function returns itself, but updated.
	 * @param newLocation The new location of the first location.
	 * @return This box, updated.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun updateFirstLocation(newLocation: Location) = apply {
		first = newLocation
	}

	/**
	 * This function changes the [second] location,
	 * by replacing it with the provided [newLocation].
	 * This function returns itself, but updated.
	 * @param newLocation The new location of the second location.
	 * @return This box, updated.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun updateSecondLocation(newLocation: Location) = apply {
		second = newLocation
	}

	/**
	 * This computational value computes the volume of this box.
	 * @author Fruxz
	 * @since 1.0
	 */
	val blockVolume: Double
		get() = (first.x.difference(second.x)+1) * (first.y.difference(second.y)+1) * (first.z.difference(second.z)+1)

	/**
	 * This computational value computes the distance between the
	 * [first] and [second] locations of this box.
	 * The measure unit is meters, or better known as blocks.
	 * @author Fruxz
	 * @since 1.0
	 */
	val distance: Double
		get() = first.distance(second)

	/**
	 * This computational value computes the required velocity,
	 * to reach the [second] location from the [first] location.
	 * This velocity is mostly used to push an entity to the
	 * [second] location.
	 * @author Fruxz
	 * @since 1.0
	 */
	val directionVelocity: Vector
		get() = directionVectorVelocity(first, second)

	/**
	 * This function creates a [BoundingBox] from the provided
	 * [first] and [second] locations.
	 * @author Fruxz
	 * @since 1.0
	 */
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
