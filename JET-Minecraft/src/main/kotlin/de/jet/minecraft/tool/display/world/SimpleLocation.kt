package de.jet.minecraft.tool.display.world

import de.jet.library.extension.paper.getWorld
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.configuration.serialization.ConfigurationSerializable

@Serializable
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

	constructor(map: Map<String, Any>) : this("" + map["world"], ("" + map["x"]).toDouble(), ("" + map["y"]).toDouble(), ("" + map["z"]).toDouble())

	val bukkit: Location
		get() = Location(getWorld(world), x, y, z)

	override fun serialize() = mapOf(
		"world" to world,
		"x" to x,
		"y" to y,
		"z" to z,
	)

	companion object {

		fun ofBukkit(location: Location) =
			with(location) { SimpleLocation(world.name, x, y, z) }

	}

}
