package de.moltenKt.paper.tool.position

import de.moltenKt.core.extension.math.ceilToInt
import de.moltenKt.core.extension.math.floorToInt
import de.moltenKt.paper.extension.paper.toSimpleLocation
import de.moltenKt.paper.tool.display.world.SimpleLocation
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.util.Vector
import kotlin.math.pow

@Serializable
data class CylindricalShape(
	val bottomCenter: SimpleLocation,
	val height: Double,
	val radius: Double,
) : Shape {

	constructor(bottomCenter: Location, height: Double, radius: Double) : this(
		bottomCenter.toSimpleLocation(),
		height,
		radius
	)

	override val center: SimpleLocation
		get() = bottomCenter.copy(y = bottomCenter.y + (height / 2))

	override val volume: Double
		get() = Math.PI * radius.pow(2) * height

	override val fullHeight: Double
		get() = height

	override val fullWidth: Double
		get() = radius * 2

	override val fullDepth: Double
		get() = radius * 2

	override val blockLocations: List<SimpleLocation>
		get() = buildList {
			for (y in bottomCenter.y.floorToInt()..(bottomCenter.y + height).ceilToInt()) {
				for (x in (bottomCenter.x - radius).floorToInt()..(bottomCenter.x + radius).ceilToInt()) {
					for (z in (bottomCenter.z - radius).floorToInt()..(bottomCenter.z + radius).ceilToInt()) {
						if (contains(vector = Vector(x, y, z)))
							add(SimpleLocation(bottomCenter.world, x.toDouble(), y.toDouble(), z.toDouble()))
					}
				}
			}
		}

	override fun contains(vector: Vector): Boolean {
		if (vector.y < bottomCenter.y || vector.y > bottomCenter.y + height) return false
		if ((bottomCenter.copy(y = vector.y).bukkit.distance(vector.toLocation(bottomCenter.bukkit.world))).floorToInt() > radius) return false

		return true
	}

	override fun contains(location: Location): Boolean {
		if (location.y < bottomCenter.y || location.y > bottomCenter.y + height) return false
		if (bottomCenter.copy(y = location.y).bukkit.distance(location).floorToInt() > radius) return false

		return true
	}

}