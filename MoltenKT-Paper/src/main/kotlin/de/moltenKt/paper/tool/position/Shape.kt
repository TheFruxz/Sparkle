package de.moltenKt.paper.tool.position

import de.moltenKt.core.annotation.NotPerfect
import de.moltenKt.paper.extension.paper.add
import de.moltenKt.paper.extension.paper.toSimpleLocation
import de.moltenKt.paper.tool.display.world.SimpleLocation
import de.moltenKt.paper.tool.position.Shape.Direction.Y
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.util.Vector
import kotlin.math.pow
import kotlin.math.sqrt

interface Shape {

	/**
	 * This computational value represents the volume, that the Shape contains.
	 * Value is specified by each [Shape] implementation individually.
	 * @author Fruxz
	 * @since 1.0
	 */
	val volume: Double

	/**
	 * This computational value represents the center of the Shape.
	 * In case of the [ComplexShape], that might not be inside the Shape.
	 * Value is specified by each [Shape] implementation individually.
	 * @author Fruxz
	 * @since 1.0
	 */
	val center: SimpleLocation

	/**
	 * This computational value represents the minHeight..maxHeight of the shape.
	 * Value is specified by each [Shape] implementation individually.
	 * This value is used, to determine the space occupied by the shape,
	 * if the shape gets stretched to a cube, without changing the min/max
	 * values of the height and width.
	 */
	val fullHeight: Double

	/**
	 * This computational value represents the minWidth..maxWidth of the shape.
	 * Value is specified by each [Shape] implementation individually.
	 * This value is used, to determine the space occupied by the shape,
	 * if the shape gets stretched to a cube, without changing the min/max
	 * values of the height and width.
	 */
	val fullWidth: Double

	/**
	 * This computational value represents the minDepth..maxDepth of the shape.
	 * Value is specified by each [Shape] implementation individually.
	 * This value is used, to determine the space occupied by the shape,
	 * if the shape gets stretched to a cube, without changing the min/max
	 * values of the height and width.
	 */
	val fullDepth: Double

	/**
	 * This computational value represents the [fullWidth], [fullHeight] and [fullDepth]
	 * of the shape. This value is used, to determine the space occupied by the shape,
	 * if the shape gets stretched to a cube, without changing the min/max values of the
	 * height and width.
	 * @author Fruxz
	 * @since 1.0
	 */
	val fullSizeShape: CubicalShape
		get() = CubicalShape(center.copy(
			x = center.x - fullWidth / 2,
			y = center.y - fullHeight / 2,
			z = center.z - fullDepth / 2,
		), center.copy (
			x = center.x + fullWidth / 2,
			y = center.y + fullHeight / 2,
			z = center.z + fullDepth / 2,
		))

	/**
	 * This computational value represents every block-center location inside the Shape.
	 * Value is specified by each [Shape] implementation individually.
	 * @author Fruxz
	 * @since 1.0
	 */
	val blockLocations: List<SimpleLocation>

	/**
	 * Returns, if the [Vector.x], [Vector.y] and [Vector.z] values of the given [Vector] are inside the shape.
	 * This function is used to easily check simple [Vector] without checking the correct world or chunk.
	 * This can result to wrong conclusions, if the world-check is required (Player in the OverWorld can be inside
	 * this [Shape], but also players in the Nether).
	 * To have more explicit checks, use [Shape.contains] with [Location] parameter instead, because this function
	 * has its own base, specified inside the [Shape].
	 * @param vector The [Vector] to check (not checking world and other specific values)
	 * @return True, if the [Vector] is inside the [Shape], false otherwise
	 * @author Fruxz
	 * @since 1.0
	 */
	fun contains(vector: Vector): Boolean

	/**
	 * Returns, if the given [Location] is inside the [Shape].
	 * This function also checks the world and other specific values, so it is more precise than [Shape.contains]
	 * with the [Vector] parameter.
	 * This function is specified inside the specific [Shape].
	 * @param location The [Location] to check
	 * @return True, if the [Location] is inside the [Shape], false otherwise
	 * @author Fruxz
	 * @since 1.0
	 */
	fun contains(location: Location): Boolean

	/**
	 * Returns, if the given [Block] is inside the [Shape].
	 * This is done by using the [Shape.contains] function with the [Location] parameter.
	 * So its precise and respects every aspect of the [SimpleLocation], like the world and the chunk.
	 * To use the [Shape.contains] function, the [simpleLocation] parameter is converted to
	 * a Bukkit [Location], via the [SimpleLocation.bukkit] computational value.
	 * @param simpleLocation The [SimpleLocation] to check
	 * @return True, if the [SimpleLocation] is inside the [Shape], false otherwise
	 * @author Fruxz
	 * @since 1.0
	 * @see Shape.contains
	 * @see SimpleLocation.bukkit
	 */
	fun contains(simpleLocation: SimpleLocation): Boolean = contains(location = simpleLocation.bukkit)

	/**
	 * Returns, if the given [Entity] is inside the [Shape].
	 * This is done by using the [Shape.contains] function with the [Location] parameter.
	 * So its precise and respects every aspect of the [Entity] [Location], like the world and the chunk.
	 * To use the [Shape.contains] function, the [entity] parameter is swapped to a [Location], via the
	 * [Entity.getLocation] function.
	 * @param entity The [Entity] to check
	 * @return True, if the [Entity] is inside the [Shape], false otherwise
	 * @author Fruxz
	 * @since 1.0
	 * @see Shape.contains
	 * @see Entity.getLocation
	 */
	fun contains(entity: Entity): Boolean = contains(location = entity.location)

	/**
	 * Returns, if the given [Block] is inside the [Shape].
	 * This is done by using the [Shape.contains] function with the [Location] parameter.
	 * So its precise and respects every aspect of the [Block] [Location], like the world and the chunk.
	 * To use the [Shape.contains] function, the [block] parameter is swapped to a [Location], via the
	 * [Block.getLocation] function.
	 * @param block The [Block] to check
	 * @return True, if the [Block] is inside the [Shape], false otherwise
	 * @author Fruxz
	 * @since 1.0
	 * @see Shape.contains
	 * @see Block.getLocation
	 */
	fun contains(block: Block): Boolean = contains(location = block.location)

	companion object {

		/**
		 * This function creates a new [CubicalShape] from the given parameters.
		 * @param fromLocation the x&y&z start-location of the shape
		 * @param toLocation the x&y&z end-location of the shape
		 * @return The created [CubicalShape]
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		fun cube(fromLocation: Location, toLocation: Location): CubicalShape = CubicalShape(fromLocation, toLocation)

		/**
		 * This function creates a new [CubicalShape] from the given parameters.
		 * It uses the center and then stretches the shape to the given size.
		 * @param center The center of the shape
		 * @param size The size of the shape
		 * @return The created [CubicalShape]
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		fun cube(center: Location, size: Double) = cube(center.clone().subtract(size / 2, size / 2, size / 2), center.clone().add(size / 2, size / 2, size / 2))

		/**
		 * This function creates a new [SphericalShape] from the given parameters.
		 * @param center The center of the shape
		 * @param radius The radius of the shape
		 * @return The created [SphericalShape]
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		fun sphere(center: Location, radius: Double) = SphericalShape(center, radius)

		/**
		 * This function creates a new [CylindricalShape] from the given parameters.
		 * The location is the center of the bottom surface of the shape.
		 * @param center The center of the shape
		 * @param height The height of the shape
		 * @param radius The radius of the shape
		 * @return The created [CylindricalShape]
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		fun cylinder(center: Location, height: Double, radius: Double, direction: Direction = Y) = CylindricalShape(center.toSimpleLocation(), direction, height, radius)

		/**
		 * This function creates a new [PyramidalShape] from the given parameters.
		 * The location is the peak of the shape.
		 * @param peak The peak of the shape
		 * @param height The height of the shape
		 * @param groundWidth The size of the bottom surface of the shape (x)
		 * @param groundDepth The size of the bottom surface of the shape (z)
		 * @return The created [PyramidalShape]
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		@NotPerfect
		fun pyramid(peak: Location, height: Double, groundWidth: Double, groundDepth: Double) = PyramidalShape(peak.toSimpleLocation(), height, groundWidth, groundDepth)

		/**
		 * This function creates a new [PyramidalShape] from the given parameters.
		 * The location is the peak of the shape.
		 * @param peak The peak of the shape
		 * @param height The height of the shape
		 * @param groundSize The size of the bottom surface of the shape (x&z)
		 * @return The created [PyramidalShape]
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		@NotPerfect
		fun pyramid(peak: Location, height: Double, groundSize: Double) = pyramid(peak, height, groundSize, groundSize)

		/**
		 * This function creates a new [PyramidalShape] from the given parameters.
		 * The location is the peak of the shape.
		 * @param peak The peak of the shape
		 * @param height The height of the shape and the size of the bottom surface of the shape (x&z)
		 * @return The created [PyramidalShape]
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		@NotPerfect
		fun pyramid(peak: Location, height: Double) = pyramid(peak, height, height)

		/**
		 * This function creates a new [PyramidalShape] from the given parameters.
		 * The location is the peak of the shape.
		 * @param peak The peak of the shape
		 * @param groundWidth The size of the bottom surface of the shape (x)
		 * @param groundDepth The size of the bottom surface of the shape (z)
		 * @return The created [PyramidalShape]
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		@NotPerfect
		fun pyramidAutoHeight(peak: Location, groundWidth: Double, groundDepth: Double) = sqrt(groundWidth.pow(2.0) + groundDepth.pow(2.0)).let { height ->
			pyramid(peak.add(y = height), height, groundWidth, groundDepth)
		}

		/**
		 * This function creates a new [LinearShape] from the given parameters.
		 * This is a line without any volume.
		 * @param start The start of the line
		 * @param end The end of the line
		 * @return The created [LinearShape]
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		fun line(start: Location, end: Location) = LinearShape(start.toSimpleLocation(), end.toSimpleLocation())

		/**
		 * This function creates a new [ComplexShape] containing all the given [shapes].
		 * @param shapes The shapes to add to the complex shape
		 * @return The created [ComplexShape]
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		fun complex(vararg shapes: Shape) = ComplexShape(shapes.toList())

	}

	enum class Direction {

		X, Y, Z;

	}

}