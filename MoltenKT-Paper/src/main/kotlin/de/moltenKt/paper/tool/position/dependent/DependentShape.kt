package de.moltenKt.paper.tool.position.dependent

import de.moltenKt.core.extension.classType.UUID
import de.moltenKt.paper.extension.paper.worlds
import de.moltenKt.paper.tool.display.world.SimpleLocation
import de.moltenKt.paper.tool.position.relative.Shape
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.util.Vector

interface DependentShape : Shape {

	/**
	 * This computational value represents the volume, that the Shape contains.
	 * Value is specified by each [DependentShape] implementation individually.
	 * @author Fruxz
	 * @since 1.0
	 */
	override val volume: Double

	/**
	 * This computational value represents the center of the Shape.
	 * In case of the [DependentComplexShape], that might not be inside the Shape.
	 * Value is specified by each [DependentShape] implementation individually.
	 * @author Fruxz
	 * @since 1.0
	 */
	val center: SimpleLocation

	/**
	 * This computational value represents the minHeight..maxHeight of the shape.
	 * Value is specified by each [DependentShape] implementation individually.
	 * This value is used, to determine the space occupied by the shape,
	 * if the shape gets stretched to a cube, without changing the min/max
	 * values of the height and width.
	 */
	override val fullHeight: Double

	/**
	 * This computational value represents the minWidth..maxWidth of the shape.
	 * Value is specified by each [DependentShape] implementation individually.
	 * This value is used, to determine the space occupied by the shape,
	 * if the shape gets stretched to a cube, without changing the min/max
	 * values of the height and width.
	 */
	override val fullWidth: Double

	/**
	 * This computational value represents the minDepth..maxDepth of the shape.
	 * Value is specified by each [DependentShape] implementation individually.
	 * This value is used, to determine the space occupied by the shape,
	 * if the shape gets stretched to a cube, without changing the min/max
	 * values of the height and width.
	 */
	override val fullDepth: Double

	/**
	 * This computational value represents the [fullWidth], [fullHeight] and [fullDepth]
	 * of the shape. This value is used, to determine the space occupied by the shape,
	 * if the shape gets stretched to a cube, without changing the min/max values of the
	 * height and width.
	 * @author Fruxz
	 * @since 1.0
	 */
	val fullSizeShape: DependentCubicalShape
		get() = DependentCubicalShape(center.copy(
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
	 * Value is specified by each [DependentShape] implementation individually.
	 * @author Fruxz
	 * @since 1.0
	 */
	val blockLocations: List<SimpleLocation>

	/**
	 * Returns, if the [Vector.x], [Vector.y] and [Vector.z] values of the given [Vector] are inside the shape.
	 * This function is used to easily check simple [Vector] without checking the correct world or chunk.
	 * This can result to wrong conclusions, if the world-check is required (Player in the OverWorld can be inside
	 * this [DependentShape], but also players in the Nether).
	 * To have more explicit checks, use [DependentShape.contains] with [Location] parameter instead, because this function
	 * has its own base, specified inside the [DependentShape].
	 * @param vector The [Vector] to check (not checking world and other specific values)
	 * @return True, if the [Vector] is inside the [DependentShape], false otherwise
	 * @author Fruxz
	 * @since 1.0
	 */
	fun contains(vector: Vector): Boolean

	/**
	 * Returns, if the given [Location] is inside the [DependentShape].
	 * This function also checks the world and other specific values, so it is more precise than [DependentShape.contains]
	 * with the [Vector] parameter.
	 * This function is specified inside the specific [DependentShape].
	 * @param location The [Location] to check
	 * @return True, if the [Location] is inside the [DependentShape], false otherwise
	 * @author Fruxz
	 * @since 1.0
	 */
	fun contains(location: Location): Boolean

	/**
	 * Returns, if the given [Block] is inside the [DependentShape].
	 * This is done by using the [DependentShape.contains] function with the [Location] parameter.
	 * So its precise and respects every aspect of the [SimpleLocation], like the world and the chunk.
	 * To use the [DependentShape.contains] function, the [simpleLocation] parameter is converted to
	 * a Bukkit [Location], via the [SimpleLocation.bukkit] computational value.
	 * @param simpleLocation The [SimpleLocation] to check
	 * @return True, if the [SimpleLocation] is inside the [DependentShape], false otherwise
	 * @author Fruxz
	 * @since 1.0
	 * @see DependentShape.contains
	 * @see SimpleLocation.bukkit
	 */
	fun contains(simpleLocation: SimpleLocation): Boolean = contains(location = simpleLocation.bukkit)

	/**
	 * Returns, if the given [Entity] is inside the [DependentShape].
	 * This is done by using the [DependentShape.contains] function with the [Location] parameter.
	 * So its precise and respects every aspect of the [Entity] [Location], like the world and the chunk.
	 * To use the [DependentShape.contains] function, the [entity] parameter is swapped to a [Location], via the
	 * [Entity.getLocation] function.
	 * @param entity The [Entity] to check
	 * @return True, if the [Entity] is inside the [DependentShape], false otherwise
	 * @author Fruxz
	 * @since 1.0
	 * @see DependentShape.contains
	 * @see Entity.getLocation
	 */
	fun contains(entity: Entity): Boolean = contains(location = entity.location)

	/**
	 * Returns, if the given [Block] is inside the [DependentShape].
	 * This is done by using the [DependentShape.contains] function with the [Location] parameter.
	 * So its precise and respects every aspect of the [Block] [Location], like the world and the chunk.
	 * To use the [DependentShape.contains] function, the [block] parameter is swapped to a [Location], via the
	 * [Block.getLocation] function.
	 * @param block The [Block] to check
	 * @return True, if the [Block] is inside the [DependentShape], false otherwise
	 * @author Fruxz
	 * @since 1.0
	 * @see DependentShape.contains
	 * @see Block.getLocation
	 */
	fun contains(block: Block): Boolean = contains(location = block.location)

	/**
	 * Returns a copy of the [DependentShape] with the [toWorld] as the world,
	 * instead of the world of the original [DependentShape].
	 * @param toWorld The world to change to
	 * @return A copy of the [DependentShape] with the [toWorld] as the world
	 * @author Fruxz
	 * @since 1.0
	 */
	fun asShifted(toWorld: World): DependentShape

	/**
	 * Returns a copy of the [DependentShape] with the [toWorldName] as the world,
	 * instead of the world of the original [DependentShape].
	 * @param toWorldName The world to change to
	 * @return A copy of the [DependentShape] with the [toWorldName] as the world
	 * @author Fruxz
	 * @since 1.0
	 */
	fun asShifted(toWorldName: String): DependentShape = asShifted(worlds.first { it.name == toWorldName })

	/**
	 * Returns a copy of the [DependentShape] with the [toWorldUid] as the world,
	 * instead of the world of the original [DependentShape].
	 * @param toWorldUid The world to change to
	 * @return A copy of the [DependentShape] with the [toWorldUid] as the world
	 * @author Fruxz
	 * @since 1.0
	 */
	fun asShifted(toWorldUid: UUID): DependentShape = asShifted(worlds.first { it.uid == toWorldUid })

}