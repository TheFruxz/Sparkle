package de.fruxz.sparkle.framework.positioning.dependent

import de.fruxz.ascend.extension.math.floor
import de.fruxz.ascend.extension.math.floorToInt
import de.fruxz.ascend.tool.smart.composition.Producible
import de.fruxz.sparkle.framework.extension.structureManager
import de.fruxz.sparkle.framework.extension.world.toSimpleLocation
import de.fruxz.sparkle.framework.extension.world.velocityTo
import de.fruxz.sparkle.framework.positioning.relative.CubicalShape
import de.fruxz.sparkle.framework.positioning.relative.Shape
import de.fruxz.sparkle.framework.positioning.world.SimpleLocation
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.structure.Structure
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

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
data class DependentCubicalShape(
	val first: SimpleLocation,
	val second: SimpleLocation,
) : DependentShape, Producible<BoundingBox>, ConfigurationSerializable, CubicalShape {

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
	val locations: Pair<Location, Location> by lazy {
		Pair(first.bukkit, second.bukkit)
	}

	/**
	 * The first location represented as a bukkit location.
	 * @return The first location represented as a bukkit location.
	 * @since 1.0
	 * @author Fruxz
	 */
	val firstLocation: Location by lazy {
		first.bukkit
	}

	/**
	 * The second location represented as a bukkit location
	 * @return The second location represented as a bukkit location
	 * @since 1.0
	 * @author Fruxz
	 */
	val secondLocation: Location by lazy {
		second.bukkit
	}

	val corners: List<SimpleLocation> by lazy {
		var corners = listOf<SimpleLocation>()

		corners += listOf(
			center.copy(
				x = center.x - (fullWidth / 2),
				y = center.y - (fullHeight / 2),
				z = center.z - (fullDepth / 2),
			),
			center.copy(
				x = center.x + (fullWidth / 2),
				y = center.y - (fullHeight / 2),
				z = center.z - (fullDepth / 2),
			),
			center.copy(
				x = center.x + (fullWidth / 2),
				y = center.y - (fullHeight / 2),
				z = center.z + (fullDepth / 2),
			),
			center.copy(
				x = center.x - (fullWidth / 2),
				y = center.y - (fullHeight / 2),
				z = center.z + (fullDepth / 2),
			),
			center.copy(
				x = center.x - (fullWidth / 2),
				y = center.y + (fullHeight / 2),
				z = center.z - (fullDepth / 2),
			),
			center.copy(
				x = center.x + (fullWidth / 2),
				y = center.y + (fullHeight / 2),
				z = center.z - (fullDepth / 2),
			),
			center.copy(
				x = center.x + (fullWidth / 2),
				y = center.y + (fullHeight / 2),
				z = center.z + (fullDepth / 2),
			),
			center.copy(
				x = center.x - (fullWidth / 2),
				y = center.y + (fullHeight / 2),
				z = center.z + (fullDepth / 2),
			),
		)

		return@lazy corners
	}

	/**
	 * The blocks hit by connection each corner of the box
	 * to every other corner of the box.
	 * @author Fruxz
	 * @since 1.0
	 */
	val gridBlockLocations: List<SimpleLocation> by lazy {
		corners.flatMap { corner ->
			corners.map { second -> Shape.line(corner.bukkit, second.bukkit) }
		}.flatMap { it.blockLocations }
	}

	/**
	 * The blocks hit by connection each corner of the box
	 * to every other corner of the box, excluding cross-
	 * connections.
	 * @author Fruxz
	 * @since 1.0
	 */
	val outlineBlockLocations: List<SimpleLocation> by lazy {
		corners.flatMap { corner ->
			corners.mapNotNull { second ->
				if (listOf(corner.x == second.x, corner.y == second.y, corner.z == second.z).count { it } >= 2) {
					Shape.line(corner.bukkit, second.bukkit)
				} else
					null
			}
		}.flatMap { it.blockLocations }
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
	val centerVector: Vector by lazy {
		produce().center
	}

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
	val centerLocation: Location by lazy {
		produce().center.toLocation(first.bukkit.world)
	}

	override val center: SimpleLocation by lazy {
		centerLocation.toSimpleLocation()
	}

	override val volume: Double by lazy {
		fullWidth * fullHeight * fullDepth
	}

	override val fullWidth: Double by lazy {
		produce().widthX
	}

	override val fullHeight: Double by lazy {
		produce().height
	}

	override val fullDepth: Double by lazy {
		produce().widthZ
	}

	override val fullSizeShape: DependentCubicalShape by lazy {
		copy()
	}

	override val blockLocations: List<SimpleLocation> by lazy {
		buildList {
			val xL = listOf(first.x, second.x).sorted()
			val yL = listOf(first.y, second.y).sorted()
			val zL = listOf(first.z, second.z).sorted()

			for (x in xL.first().floorToInt()..xL.last().floorToInt()) {
				for (y in yL.first().floorToInt()..yL.last().floorToInt()) {
					for (z in zL.first().floorToInt()..zL.last().floorToInt()) {
						add(
							SimpleLocation(
								first.world,
								x.toDouble().floor() + .5,
								y.toDouble().floor() + .5,
								z.toDouble().floor() + .5
							)
						)
					}
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
	fun updateLocations(firstLocation: Location, secondLocation: Location) = copy(
		first = firstLocation.toSimpleLocation(),
		second = secondLocation.toSimpleLocation()
	)

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
	fun updateFirstLocation(newLocation: Location) = copy(
		first = newLocation.toSimpleLocation()
	)

	/**
	 * This function changes the [second] location,
	 * by replacing it with the provided [newLocation].
	 * This function returns itself, but updated.
	 * @param newLocation The new location of the second location.
	 * @return This box, updated.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun updateSecondLocation(newLocation: Location) = copy(
		second = newLocation.toSimpleLocation()
	)

	/**
	 * This computational value computes the volume of this box.
	 * @author Fruxz
	 * @since 1.0
	 */
	val blockVolume: Int by lazy {
		val xL = listOf(first.x, second.x).sorted()
		val yL = listOf(first.y, second.y).sorted()
		val zL = listOf(first.z, second.z).sorted()

		(xL.last().floorToInt() - xL.first().floorToInt() + 1) *
				(yL.last().floorToInt() - yL.first().floorToInt() + 1) *
				(zL.last().floorToInt() - zL.first().floorToInt() + 1)
	}

	/**
	 * This computational value computes the distance between the
	 * [first] and [second] locations of this box.
	 * The measure unit is meters, or better known as blocks.
	 * @author Fruxz
	 * @since 1.0
	 */
	val distance: Double by lazy {
		first.bukkit.distance(second.bukkit)
	}

	/**
	 * This computational value computes the required velocity,
	 * to reach the [second] location from the [first] location.
	 * This velocity is mostly used to push an entity to the
	 * [second] location.
	 * @author Fruxz
	 * @since 1.0
	 */
	val directionVelocity: Vector by lazy {
		first.bukkit velocityTo second.bukkit
	}

	override val length: Double = abs(first.x - second.x)

	override val depth: Double = abs(first.z - second.z)

	override val height: Double = abs(first.y - second.y)

	val minX = min(first.x, second.x)
	val minY = min(first.y, second.y)
	val minZ = min(first.z, second.z)

	val maxX = max(first.x, second.x)
	val maxY = max(first.y, second.y)
	val maxZ = max(first.z, second.z)

	override fun asShifted(toWorld: World): DependentShape = copy(
		first = first.copy(world = toWorld.name),
		second = second.copy(world = toWorld.name),
	)

	/**
	 * This function returns a copy of this [DependentCubicalShape],
	 * but the [first] location represents the [minX], [minY], [minZ]
	 * vector and the [second] location represents the [maxX], [maxY],
	 * [maxZ] vector.
	 * This function does not modify this current object, only creating
	 * a different copy, because a [DependentCubicalShape] is immutable!
	 * @return a modified copy
	 * @author Fruxz
	 * @since 1.0
	 */
	fun sorted() = copy(
		first = SimpleLocation(first.world, minX, minY, minZ),
		second = SimpleLocation(second.world, maxX, maxY, maxZ),
	)

	fun structure(includeEntities: Boolean = false): Structure = with(sorted()) {
		structureManager.createStructure().apply {
			fill(firstLocation, secondLocation, includeEntities)
		}
	}

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
		 * This function creates a new [DependentCubicalShape] object, representing
		 * a range from one to another [Location]. This function is defined
		 * as a [rangeTo] operator function. This function uses the [this]
		 * [Location] as the first [Location] ([DependentCubicalShape.component1])
		 * and the [other] [Location] ([DependentCubicalShape.second]) as the
		 * second [Location] inside the [DependentCubicalShape].
		 * @param other is the second location used to define the range.
		 * @return a new [DependentCubicalShape] object.
		 * @see DependentCubicalShape
		 * @see Location
		 * @see rangeTo
		 * @author Fruxz
		 * @since 1.0
		 */
		operator fun Location.rangeTo(other: Location) =
			DependentCubicalShape(this to other)

	}

}
