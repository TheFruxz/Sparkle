package de.moltenKt.paper.extension.paper

import com.destroystokyo.paper.ParticleBuilder
import de.moltenKt.paper.tool.display.world.SimpleLocation
import de.moltenKt.paper.tool.position.LocationBox
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector
import kotlin.math.roundToInt

operator fun Location.plus(location: Location) = LocationBox(this, location)

fun Location.displayString(
	withNames: Boolean = true,
	withRotation: Boolean = false,
	displayX: Char = 'x',
	displayY: Char = 'y',
	displayZ: Char = 'z',
	displayYaw: String = "yaw",
	displayPitch: String = "pitch"
) =
	with(this) {
		if (withNames) {
			"$displayX: $x $displayY: $y $displayZ: $z" + if (withRotation) "$displayYaw: ${yaw.roundToInt()} $displayPitch: ${pitch.roundToInt()}" else ""
		} else
			"$x $y $z" + if (withRotation) "$displayYaw: ${yaw.roundToInt()} $displayPitch: ${pitch.roundToInt()}" else ""
	}

fun LocationBox.displayString() = with(locations) { "${first.displayString()} to ${second.displayString()}" }

fun BoundingBox.contains(location: Location) = this.contains(location.toVector())

fun BoundingBox.contains(entity: Entity) = this.contains(entity.location)

fun BoundingBox.contains(player: Player) = this.contains(player as Entity)

fun BoundingBox.contains(block: Block) = this.contains(block.location)

fun box(location1: Location, location2: Location) = BoundingBox.of(location1, location2)

fun directionVectorVelocity(start: Location, destination: Location) =
	directionVectorVelocity(start.toVector(), destination.toVector())

fun directionVectorVelocity(start: Vector, destination: Vector) = destination.subtract(start)

val Block.safeBoundingBox: BoundingBox
	get() = BoundingBox.of(location, location.clone().add(Vector(1.0, 1.0, 1.0)))

fun ParticleBuilder.positioning(world: World, boundingBox: BoundingBox) =
	location(world, boundingBox.centerX, boundingBox.centerY, boundingBox.centerZ)
		.offset(boundingBox.widthX / 2, boundingBox.height / 2, boundingBox.widthZ / 2)

fun ParticleBuilder.positioning(entity: Entity) = with(entity.boundingBox) {
	this@positioning.location(entity.world, centerX, centerY, centerZ)
		.offset(widthX/2, height/2, widthZ/2)
}

fun ParticleBuilder.positioning(block: Block) = with(block.boundingBox) {
	this@positioning.location(block.world, centerX, centerY, centerZ)
		.offset(widthX/2, height/2, widthZ/2)
}

fun Location.getNearbyBlocks(radius: Int) = mutableListOf<Block>().apply {
	for (x in -radius until radius) {
		for (y in -radius until radius) {
			for (z in -radius until radius) {
				add(world.getBlockAt(blockX + x, blockY + y, blockZ + z))
			}
		}
	}
}.toSet()

/**
 * This functions takes a ([Number] to [Number] to [Number]) [Triple],
 * to use the values as the parameters of the [Location].
 * - the *first* value is the `x-coordinate`
 * - the *second* value is the `y-coordinate`
 * - the *third* value is the `z-coordinate`
 * @param world the world, where the location-data is located at
 * @return the generated Location-object
 * @author Fruxz
 * @since 1.0
 */
fun Pair<Pair<Number, Number>, Number>.asLocation(world: World) =
	Location(world, first.first.toDouble(), first.second.toDouble(), second.toDouble())

/**
 * This functions takes a ([Number] to [Number] to [Number]) [Triple],
 * to use the values as the parameters of the [Location].
 * - the *first* value is the `x-coordinate`
 * - the *second* value is the `y-coordinate`
 * - the *third* value is the `z-coordinate`
 * @param worldName the name of the world, where the location data is located at
 * @return the generated Location-object, or null if the world does not exist
 * @author Fruxz
 * @since 1.0
 */
fun Pair<Pair<Number, Number>, Number>.asLocation(worldName: String) =
	getWorld(worldName)?.let { asLocation(it) }

/**
 * This function adds the given [x], [y] and [z] values to the current [Location].
 * @param x the x-coordinate to add
 * @param y the y-coordinate to add
 * @param z the z-coordinate to add
 * @return the modified Location-object
 * @author Fruxz
 * @since 1.0
 */
fun Location.add(x: Number = 0, y: Number = 0, z: Number = 0) =
	add(x.toDouble(), y.toDouble(), z.toDouble())

/**
 * This function subtracts the given [x], [y] and [z] values from the current [Location].
 * @param x the x-coordinate to subtract
 * @param y the y-coordinate to subtract
 * @param z the z-coordinate to subtract
 * @return the modified Location-object
 * @author Fruxz
 * @since 1.0
 */
fun Location.subtract(x: Number = 0, y: Number = 0, z: Number = 0) =
	subtract(x.toDouble(), y.toDouble(), z.toDouble())

/**
 * This function creates a new simple location object,
 * with this location data.
 * @author Fruxz
 * @since 1.0
 */
fun Location.toSimpleLocation() = with(this) { SimpleLocation(world.name, x, y, z) }
