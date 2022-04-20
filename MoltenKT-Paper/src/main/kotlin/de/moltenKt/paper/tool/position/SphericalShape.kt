package de.moltenKt.paper.tool.position

import de.moltenKt.core.extension.math.ceilToInt
import de.moltenKt.core.extension.math.floorToInt
import de.moltenKt.paper.tool.display.world.SimpleLocation
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.util.Vector
import kotlin.math.pow

@Serializable
data class SphericalShape(
	override val center: SimpleLocation,
	val radius: Double,
) : Shape {

	override val volume: Double
		get() = 4.0 / 3.0 * Math.PI * radius.pow(3)

	override val fullHeight: Double
		get() = radius * 2

	override val fullWidth: Double
		get() = radius * 2

	override val fullDepth: Double
		get() = radius * 2

	override val blockLocations: List<SimpleLocation>
		get() = buildList {
			/*for (x in -radius.toInt()..radius.toInt()) {
				for (y in -radius.toInt()..radius.toInt()) {
					for (z in -radius.toInt()..radius.toInt()) {
						val vector = Vector(x.toDouble(), y.toDouble(), z.toDouble())
						if (contains(vector = vector)) {
							add(SimpleLocation(center.world, center.x + x, center.y + y, center.z + z))
						}
					}
				}
			}*/
			for (x in (center.x - radius).floorToInt()..(center.x + radius).ceilToInt()) {
				for (y in (center.y - radius).floorToInt()..(center.y + radius).ceilToInt()) {
					for (z in (center.z - radius).floorToInt()..(center.z + radius).ceilToInt()) {
						val vector = Vector(x.toDouble(), y.toDouble(), z.toDouble())
						if (contains(vector = vector)) {
							add(SimpleLocation(center.world, x.toDouble(), y.toDouble(), z.toDouble()))
						}
					}
				}
			}
		}

	override fun contains(vector: Vector): Boolean = vector.distance(center.bukkit.toVector()).floorToInt() <= radius

	override fun contains(location: Location): Boolean = location.distance(center.bukkit).floorToInt() <= radius

}