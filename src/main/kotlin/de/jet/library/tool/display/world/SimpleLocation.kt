package de.jet.library.tool.display.world

import kotlinx.serialization.Serializable
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World

@Serializable
data class SimpleLocation(
	val world: String,
	val x: Double,
	val y: Double,
	val z: Double,
) {

	constructor(
		world: World,
		x: Double,
		y: Double,
		z: Double,
	) : this(world.name, x, y, z)

	val bukkit: Location
		get() = Location(Bukkit.getWorld(world), x, y, z)

	companion object {

		fun ofBukkit(location: Location) =
			with(location) { SimpleLocation(world.name, x, y, z) }

	}

}
