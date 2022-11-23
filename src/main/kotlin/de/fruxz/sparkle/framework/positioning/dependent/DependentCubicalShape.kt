package de.fruxz.sparkle.framework.positioning.dependent

import de.fruxz.ascend.extension.math.floor
import de.fruxz.ascend.extension.math.floorToInt
import de.fruxz.ascend.tool.smart.composition.Producible
import de.fruxz.sparkle.framework.extension.structureManager
import de.fruxz.sparkle.framework.extension.world.copy
import de.fruxz.sparkle.framework.extension.world.velocityTo
import de.fruxz.sparkle.framework.positioning.relative.CubicalShape
import de.fruxz.sparkle.framework.positioning.relative.Shape
import kotlinx.serialization.Contextual
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
 * @param firstLocation The first point/corner of the box.
 * @param secondLocation The second point/corner of the box.
 * @author Fruxz
 * @since 1.0
 */
@Serializable
data class DependentCubicalShape(
	@Contextual val firstLocation: Location,
	@Contextual val secondLocation: Location,
) : DependentShape, Producible<BoundingBox>, ConfigurationSerializable, CubicalShape {

	constructor(both: Location) : this(both, both)

	constructor(locations: Pair<Location, Location>) : this(locations.first, locations.second)

	constructor(locationEntry: Map.Entry<Location, Location>) : this(locationEntry.key, locationEntry.value)

	constructor(vararg locations: Location) : this(locations.first(), locations.last())

	constructor(map: Map<String, Any?>) : this(map["first"] as Location, map["second"] as Location)

	/**
	 * This lazy val represents the [BoundingBox] of this [DependentCubicalShape].
	 * @author Fruxz
	 * @since 1.0
	 */
	val box: BoundingBox by lazy {
		BoundingBox.of(firstLocation, secondLocation)
	}

	/**
	 * Both locations [firstLocation] and [secondLocation] represented as
	 * a [Pair] of [Location]s.
	 * @return A [Pair] of [Location]s.
	 * @since 1.0
	 * @author Fruxz
	 */
	val locations: Pair<Location, Location> by lazy {
		Pair(firstLocation, secondLocation)
	}

	val corners: List<Location> by lazy {
		listOf(
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
	}

	/**
	 * The blocks hit by connection each corner of the box
	 * to every other corner of the box.
	 * @author Fruxz
	 * @since 1.0
	 */
	val gridBlockLocations: List<Location> by lazy {
		corners.flatMap { corner ->
			corners.map { second -> Shape.line(corner, second) }
		}.flatMap { it.blockLocations }
	}

	/**
	 * The blocks hit by connection each corner of the box
	 * to every other corner of the box, excluding cross-
	 * connections.
	 * @author Fruxz
	 * @since 1.0
	 */
	val outlineBlockLocations: List<Location> by lazy {
		corners.flatMap { corner ->
			corners.mapNotNull { second ->
				if (listOf(corner.x == second.x, corner.y == second.y, corner.z == second.z).count { it } >= 2) {
					Shape.line(corner, second)
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
		box.center
	}

	override val center: Location by lazy {
		box.center.toLocation(firstLocation.world)
	}

	override val volume: Double by lazy {
		fullWidth * fullHeight * fullDepth
	}

	override val fullWidth: Double by lazy {
		box.widthX
	}

	override val fullHeight: Double by lazy {
		box.height
	}

	override val fullDepth: Double by lazy {
		box.widthZ
	}

	override val fullSizeShape: DependentCubicalShape by lazy {
		copy()
	}

	override val blockLocations: List<Location> by lazy {
		buildList {
			val xL = listOf(firstLocation.x, secondLocation.x).sorted()
			val yL = listOf(firstLocation.y, secondLocation.y).sorted()
			val zL = listOf(firstLocation.z, secondLocation.z).sorted()

			for (x in xL.first().floorToInt()..xL.last().floorToInt()) {
				for (y in yL.first().floorToInt()..yL.last().floorToInt()) {
					for (z in zL.first().floorToInt()..zL.last().floorToInt()) {
						add(
							Location(
								firstLocation.world,
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

	override fun contains(vector: Vector) = box.contains(vector)

	override fun contains(location: Location) = contains(location.toVector())

	/**
	 * This function changes the [firstLocation] and [secondLocation] location
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
		firstLocation = firstLocation,
		secondLocation = secondLocation,
	)

	/**
	 * This function changes the [firstLocation] and [secondLocation] location
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
	 * This function changes the [firstLocation] location,
	 * by replacing it with the provided [newLocation].
	 * This function returns itself, but updated.
	 * @param newLocation The new location of the first location.
	 * @return This box, updated.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun updateFirstLocation(newLocation: Location) = copy(
		firstLocation = newLocation
	)

	/**
	 * This function changes the [secondLocation] location,
	 * by replacing it with the provided [newLocation].
	 * This function returns itself, but updated.
	 * @param newLocation The new location of the second location.
	 * @return This box, updated.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun updateSecondLocation(newLocation: Location) = copy(
		secondLocation = newLocation
	)

	/**
	 * This computational value computes the volume of this box.
	 * @author Fruxz
	 * @since 1.0
	 */
	val blockVolume: Int by lazy {
		val xL = listOf(firstLocation.x, secondLocation.x).sorted()
		val yL = listOf(firstLocation.y, secondLocation.y).sorted()
		val zL = listOf(firstLocation.z, secondLocation.z).sorted()

		(xL.last().floorToInt() - xL.first().floorToInt() + 1) *
				(yL.last().floorToInt() - yL.first().floorToInt() + 1) *
				(zL.last().floorToInt() - zL.first().floorToInt() + 1)
	}

	/**
	 * This computational value computes the distance between the
	 * [firstLocation] and [secondLocation] locations of this box.
	 * The measure unit is meters, or better known as blocks.
	 * @author Fruxz
	 * @since 1.0
	 */
	val distance: Double by lazy {
		firstLocation.distance(secondLocation)
	}

	/**
	 * This computational value computes the required velocity,
	 * to reach the [secondLocation] location from the [firstLocation] location.
	 * This velocity is mostly used to push an entity to the
	 * [secondLocation] location.
	 * @author Fruxz
	 * @since 1.0
	 */
	val directionVelocity: Vector by lazy {
		firstLocation velocityTo secondLocation
	}

	override val length: Double = abs(firstLocation.x - secondLocation.x)

	override val depth: Double = abs(firstLocation.z - secondLocation.z)

	override val height: Double = abs(firstLocation.y - secondLocation.y)

	val minX = min(firstLocation.x, secondLocation.x)
	val minY = min(firstLocation.y, secondLocation.y)
	val minZ = min(firstLocation.z, secondLocation.z)

	val maxX = max(firstLocation.x, secondLocation.x)
	val maxY = max(firstLocation.y, secondLocation.y)
	val maxZ = max(firstLocation.z, secondLocation.z)

	override fun asShifted(toWorld: World): DependentShape = copy(
		firstLocation = firstLocation.copy(world = toWorld),
		secondLocation = secondLocation.copy(world = toWorld),
	)

	/**
	 * This function returns a copy of this [DependentCubicalShape],
	 * but the [firstLocation] location represents the [minX], [minY], [minZ]
	 * vector and the [secondLocation] location represents the [maxX], [maxY],
	 * [maxZ] vector.
	 * This function does not modify this current object, only creating
	 * a different copy, because a [DependentCubicalShape] is immutable!
	 * @return a modified copy
	 * @author Fruxz
	 * @since 1.0
	 */
	fun sorted() = copy(
		firstLocation = Location(firstLocation.world, minX, minY, minZ),
		secondLocation = Location(secondLocation.world, maxX, maxY, maxZ),
	)

	fun structure(includeEntities: Boolean = false): Structure = with(sorted()) {
		structureManager.createStructure().apply {
			fill(firstLocation, secondLocation, includeEntities)
		}
	}

	/**
	 * This function creates a [BoundingBox] from the provided
	 * [firstLocation] and [secondLocation] locations.
	 * @author Fruxz
	 * @since 1.0
	 */
	override fun produce() = box

	override fun serialize() = mapOf(
		"first" to firstLocation,
		"second" to secondLocation,
	)

	companion object {

		/**
		 * This function creates a new [DependentCubicalShape] object, representing
		 * a range from one to another [Location]. This function is defined
		 * as a [rangeTo] operator function. This function uses the [this]
		 * [Location] as the first [Location] ([DependentCubicalShape.firstLocation])
		 * and the [other] [Location] ([DependentCubicalShape.secondLocation]) as the
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
