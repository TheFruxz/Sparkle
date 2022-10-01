package de.moltenKt.paper.tool.position.dependent

import de.fruxz.ascend.extension.math.ceilToInt
import de.fruxz.ascend.extension.math.floorToInt
import de.moltenKt.paper.extension.paper.toSimpleLocation
import de.moltenKt.paper.tool.display.world.SimpleLocation
import de.moltenKt.paper.tool.position.relative.SphereShape
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.util.Vector
import kotlin.math.pow

@Serializable
data class DependentSphericalShape(
	override val center: SimpleLocation,
	override val radius: Double,
) : DependentShape, SphereShape {

	constructor(center: Location, radius: Double) : this(center.toSimpleLocation(), radius)

	override val volume: Double by lazy {
		4.0 / 3.0 * Math.PI * radius.pow(3)
	}

	override val fullHeight: Double by lazy {
		radius * 2
	}

	override val fullWidth: Double by lazy {
		radius * 2
	}

	override val fullDepth: Double by lazy {
		radius * 2
	}

	override val blockLocations: List<SimpleLocation> by lazy {
		buildList {
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
	}

	override fun contains(vector: Vector): Boolean = vector.distance(center.bukkit.toVector()).floorToInt() <= radius

	override fun contains(location: Location): Boolean = location.distance(center.bukkit).floorToInt() <= radius

	override fun asShifted(toWorld: World): DependentShape = copy(center = center.copy(world = toWorld.name))

}