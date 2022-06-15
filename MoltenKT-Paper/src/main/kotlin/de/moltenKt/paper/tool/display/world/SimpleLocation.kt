package de.moltenKt.paper.tool.display.world

import de.moltenKt.paper.extension.paper.worldOrNull
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.util.Vector

/**
 * This object represents a non-directional location on a [World], by using the
 * [world]-name, the [x]-coordinate, the [y]-coordinate and the [z]-coordinate.
 * This class can be used inside yaml and json files, using the common
 * [ConfigurationSerializable] interface or the [Serializable] annotation.
 * @author Fruxz
 * @since 1.0
 */
@Serializable
@SerialName("WorldSimpleLocation")
data class SimpleLocation(
	val world: String,
	val x: Double,
	val y: Double,
	val z: Double,
) : ConfigurationSerializable {

	constructor(
		world: World,
		x: Double,
		y: Double,
		z: Double,
	) : this(world.name, x, y, z)

	constructor(
		world: String,
		x: Number,
		y: Number,
		z: Number,
	) : this(world, x.toDouble(), y.toDouble(), z.toDouble())

	constructor(map: Map<String, Any>) : this("" + map["world"], ("" + map["x"]).toDouble(), ("" + map["y"]).toDouble(), ("" + map["z"]).toDouble())

	/**
	 * This value computes this [SimpleLocation] as a [Location] on the [World]
	 * This requires the [World] with the [worldOrNull]-name to be existent.
	 * @author Fruxz
	 * @since 1.0
	 */
	val bukkit: Location by lazy {
		Location(worldOrNull(world), x, y, z)
	}

	val bukkitWorld: World
		get() = bukkit.world

	val blockX: Int
		get() = bukkit.blockX

	val blockY: Int
		get() = bukkit.blockY

	val blockZ: Int
		get() = bukkit.blockZ

	fun toVector(): Vector {
		return Vector(x, y, z)
	}

	override fun serialize() = mapOf(
		"world" to world,
		"x" to x,
		"y" to y,
		"z" to z,
	)

	companion object {

		/**
		 * This function creates a [SimpleLocation] from a [Location] parameter
		 * and the constructor of [SimpleLocation].
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		fun ofBukkit(location: Location) =
			with(location) { SimpleLocation(world.name, x, y, z) }

	}

}
