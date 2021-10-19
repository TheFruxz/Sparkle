package de.jet.minecraft.app.component.essentials.point

import de.jet.library.tool.smart.identification.Identifiable
import kotlinx.serialization.Serializable
import org.bukkit.Bukkit
import org.bukkit.Location

@Serializable
data class Point(
	override val identity: String,
	val world: String,
	val x: Double,
	val y: Double,
	val z: Double,
	val pitch: Float,
	val yaw: Float,
) : Identifiable<Point> {

	constructor(identity: String, location: Location) : this(identity, location.world.name, location.x, location.y, location.z, location.pitch, location.yaw)

	val bukkitLocation: Location
		get() = Location(Bukkit.getWorld(world), x, y, z, yaw, pitch)

}
