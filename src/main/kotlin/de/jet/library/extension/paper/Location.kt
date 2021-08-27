package de.jet.library.extension.paper

import com.destroystokyo.paper.ParticleBuilder
import de.jet.library.tool.position.LocationBox
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

fun LocationBox.displayString() = "${first.displayString()} to ${last.displayString()}"

fun BoundingBox.contains(location: Location) = this.contains(location.toVector())

fun BoundingBox.contains(entity: Entity) = this.contains(entity.location)

fun BoundingBox.contains(player: Player) = this.contains(player as Entity)

fun BoundingBox.contains(block: Block) = this.contains(block.location)

fun box(location1: Location, location2: Location) = BoundingBox.of(location1, location2)

fun directionVectorVelocity(from: Location, to: Location) = to.toVector().subtract(from.toVector())

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