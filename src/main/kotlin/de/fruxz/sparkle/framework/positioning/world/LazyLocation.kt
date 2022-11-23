package de.fruxz.sparkle.framework.positioning.world

import de.fruxz.sparkle.framework.extension.world.Location
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.util.Vector

/**
 * A way, to save location data, without directly requiring a running world.
 * @author Fruxz
 * @since 1.0
 */
@Serializable
@SerialName("LazyLocation")
data class LazyLocation(
	val world: String,
	val x: Double,
	val y: Double,
	val z: Double,
	val yaw: Float = 0F,
	val pitch: Float = 0F,
) : ConfigurationSerializable {

	constructor(
		world: World,
		x: Double,
		y: Double,
		z: Double,
		yaw: Float = 0F,
		pitch: Float = 0F,
	) : this(world.name, x, y, z, yaw, pitch)

	constructor(map: Map<String, Any>) : this(
		world = map["world"] as String,
		x = map["x"] as Double,
		y = map["y"] as Double,
		z = map["z"] as Double,
		yaw = map["yaw"] as Float,
		pitch = map["pitch"] as Float,
	)

	val asLocation: Location
		get() = Location(world, x, y, z, yaw, pitch)

	val asVector: Vector
		get() = Vector(x, y, z)

	override fun serialize() = mapOf(
		"world" to world,
		"x" to x,
		"y" to y,
		"z" to z,
		"yaw" to yaw,
		"pitch" to pitch,
	)

}
