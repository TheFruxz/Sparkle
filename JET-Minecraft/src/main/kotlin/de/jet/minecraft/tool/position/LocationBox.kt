package de.jet.minecraft.tool.position

import de.jet.library.tool.smart.Producible
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector

data class LocationBox(
	var first: Location,
	var last: Location,
) : Producible<BoundingBox> {

	constructor(both: Location) : this(both, both)

	constructor(locations: Pair<Location, Location>) : this(locations.first, locations.second)

	constructor(locationEntry: Map.Entry<Location, Location>) : this(locationEntry.key, locationEntry.value)

	constructor(vararg locations: Location) : this(locations.first(), locations.last())

	val center: Vector
		get() = produce().center

	fun contains(vector: Vector) = produce().contains(vector)

	fun contains(location: Location) = contains(location.toVector())

	fun contains(entity: Entity) = contains(entity.location)

	fun contains(player: Player) = contains(player as Entity)

	fun contains(block: Block) = contains(block.location)

	fun updateLocations(firstLocation: Location, secondLocation: Location) = apply {
		updateFirstLocation(newLocation = firstLocation)
		updateLastLocation(newLocation = secondLocation)
	}

	fun updateLocations(locations: Pair<Location, Location>) =
		updateLocations(firstLocation = locations.first, secondLocation = locations.second)

	fun updateFirstLocation(newLocation: Location) = apply {
		first = newLocation
	}

	fun updateLastLocation(newLocation: Location) = apply {
		last = newLocation
	}

	override fun produce() = BoundingBox.of(first, last)

}
