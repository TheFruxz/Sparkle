package de.moltenKt.paper.tool.position

import de.moltenKt.core.extension.math.ceilToInt
import de.moltenKt.core.extension.math.difference
import de.moltenKt.core.extension.math.floorToInt
import de.moltenKt.core.tool.smart.Producible
import de.moltenKt.paper.extension.paper.directionVectorVelocity
import de.moltenKt.paper.extension.paper.toSimpleLocation
import de.moltenKt.paper.tool.display.world.SimpleLocation
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.configuration.serialization.ConfigurationSerializable
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
@Serializable
data class CubicalShape(
	var first: SimpleLocation,
	var second: SimpleLocation,
) : Producible<BoundingBox>, ConfigurationSerializable, Shape {

	constructor(first: Location, second: Location) : this(first.toSimpleLocation(), second.toSimpleLocation())

	constructor(both: Location) : this(both, both)

	constructor(locations: Pair<Location, Location>) : this(locations.first, locations.second)

	constructor(locationEntry: Map.Entry<Location, Location>) : this(locationEntry.key, locationEntry.value)

	constructor(vararg locations: Location) : this(locations.first(), locations.last())

	constructor(map: Map<String, Any?>) : this(map["first"] as SimpleLocation, map["second"] as SimpleLocation)

	/**
	 * Both locations [first] and [second] represented as
	 * a [Pair] of [Location]s.
	 * @return A [Pair] of [Location]s.
	 * @since 1.0
	 * @author Fruxz
	 */
	val locations: Pair<Location, Location>
		get() = Pair(first.bukkit, second.bukkit)

	/**
	 * The first location represented as a bukkit location.
	 * @return The first location represented as a bukkit location.
	 * @since 1.0
	 * @author Fruxz
	 */
	var firstLocation: Location
		get() = first.bukkit
		set(value) {
			first = value.toSimpleLocation()
		}

	/**
	 * The second location represented as a bukkit location
	 * @return The second location represented as a bukkit location
	 * @since 1.0
	 * @author Fruxz
	 */
	var secondLocation: Location
		get() = second.bukkit
		set(value) {
			second = value.toSimpleLocation()
		}

	/**
	 * This function returns the center of the box.
	 * The computational part is done by the [BoundingBox],
	 * and the result is returned as a [Vector].
	 * @return The center of the box.
	 * @see BoundingBox.getCenter
	 * @author Fruxz
	 * @since 1.0
	 */
	val centerVector: Vector
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
		get() = produce().center.toLocation(first.bukkit.world)

	override val center: SimpleLocation
		get() = centerLocation.toSimpleLocation()

	override val volume: Double
		get() = fullWidth * fullHeight * fullDepth

	override val fullWidth: Double
		get() = produce().widthX

	override val fullHeight: Double
		get() = produce().height

	override val fullDepth: Double
		get() = produce().widthZ

	override val fullSizeShape: CubicalShape
		get() = copy()

	override val blockLocations: List<SimpleLocation>
		get() = buildList {
			for (x in first.x.floorToInt()..second.x.ceilToInt()) {
				for (y in first.y.floorToInt()..second.y.ceilToInt()) {
					for (z in first.z.floorToInt()..second.z.ceilToInt()) {
						add(SimpleLocation(first.world, x.toDouble(), y.toDouble(), z.toDouble()))
					}
				}
			}
		}

	override fun contains(vector: Vector) = produce().contains(vector)

	override fun contains(location: Location) = contains(location.toVector())

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
		first = newLocation.toSimpleLocation()
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
		second = newLocation.toSimpleLocation()
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
		get() = first.bukkit.distance(second.bukkit)

	/**
	 * This computational value computes the required velocity,
	 * to reach the [second] location from the [first] location.
	 * This velocity is mostly used to push an entity to the
	 * [second] location.
	 * @author Fruxz
	 * @since 1.0
	 */
	val directionVelocity: Vector
		get() = directionVectorVelocity(first.bukkit, second.bukkit)

	/**
	 * This function creates a [BoundingBox] from the provided
	 * [first] and [second] locations.
	 * @author Fruxz
	 * @since 1.0
	 */
	override fun produce() = BoundingBox.of(first.bukkit, second.bukkit)

	override fun serialize() = mapOf(
		"first" to first,
		"second" to second,
	)

	companion object {

		/**
		 * This function creates a new [CubicalShape] object, representing
		 * a range from one to another [Location]. This function is defined
		 * as a [rangeTo] operator function. This function uses the [this]
		 * [Location] as the first [Location] ([CubicalShape.component1])
		 * and the [other] [Location] ([CubicalShape.second]) as the
		 * second [Location] inside the [CubicalShape].
		 * @param other is the second location used to define the range.
		 * @return a new [CubicalShape] object.
		 * @see CubicalShape
		 * @see Location
		 * @see rangeTo
		 * @author Fruxz
		 * @since 1.0
		 */
		operator fun Location.rangeTo(other: Location) =
			CubicalShape(this to other)

	}

}
