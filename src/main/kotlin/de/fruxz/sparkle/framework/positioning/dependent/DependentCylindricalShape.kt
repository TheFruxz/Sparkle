package de.fruxz.sparkle.framework.positioning.dependent

import de.fruxz.ascend.extension.math.ceilToInt
import de.fruxz.ascend.extension.math.floorToInt
import de.fruxz.sparkle.framework.positioning.relative.CylindricalShape
import de.fruxz.sparkle.framework.positioning.relative.Shape
import de.fruxz.sparkle.framework.positioning.world.SimpleLocation
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.util.Vector
import kotlin.math.pow

@Serializable
data class DependentCylindricalShape(
	override val center: SimpleLocation,
	override val direction: Shape.Direction,
	override val height: Double,
	override val radius: Double,
) : DependentShape, CylindricalShape {

	private val halfHeight by lazy {
		height / 2
	}

	override val volume: Double by lazy {
		Math.PI * radius.pow(2) * height
	}

	override val fullHeight: Double by lazy {
		when (direction) {
			Shape.Direction.Y -> height
			else -> radius * 2
		}
	}

	override val fullWidth: Double by lazy {
		when (direction) {
			Shape.Direction.X -> height
			else -> radius * 2
		}
	}

	override val fullDepth: Double by lazy {
		when (direction) {
			Shape.Direction.Z -> height
			else -> radius * 2
		}
	}

	override val blockLocations: List<SimpleLocation> by lazy {
		var output = listOf<SimpleLocation>()

		var attempts = 0
		val zLocations: IntRange
		val xLocations: IntRange
		val yLocations: IntRange

		when (direction) {
			Shape.Direction.X -> {
				zLocations = (center.z - radius).floorToInt()..(center.z + radius).ceilToInt()
				xLocations = (center.x - halfHeight).floorToInt()..(center.x + halfHeight).ceilToInt()
				yLocations = (center.y - radius).floorToInt()..(center.y + radius).ceilToInt()
			}
			Shape.Direction.Y -> {
				zLocations = (center.z - radius).floorToInt()..(center.z + radius).ceilToInt()
				xLocations = (center.x - radius).floorToInt()..(center.x + radius).ceilToInt()
				yLocations = (center.y - halfHeight).floorToInt()..(center.y + halfHeight).ceilToInt()
			}
			Shape.Direction.Z -> {
				zLocations = (center.z - halfHeight).floorToInt()..(center.z + halfHeight).ceilToInt()
				xLocations = (center.x - radius).floorToInt()..(center.x + radius).ceilToInt()
				yLocations = (center.y - radius).floorToInt()..(center.y + radius).ceilToInt()
			}
		}

		for (x in xLocations) {
			for (y in yLocations) {
				for (z in zLocations) {
					val location = SimpleLocation(center.world, x, y, z)
					attempts++
					if (contains(location)) {
						output += location
					}
				}
			}
		}

		return@lazy output
	}

	override fun contains(vector: Vector): Boolean {
		val x: ClosedFloatingPointRange<Double>
		val y: ClosedFloatingPointRange<Double>
		val z: ClosedFloatingPointRange<Double>

		when (direction) {
			Shape.Direction.X -> {
				x = (center.x - halfHeight)..(center.x + halfHeight)
				y = (center.y - radius)..(center.y + radius)
				z = (center.z - radius)..(center.z + radius)
			}
			Shape.Direction.Y -> {
				x = (center.x - radius)..(center.x + radius)
				y = (center.y - halfHeight)..(center.y + halfHeight)
				z = (center.z - radius)..(center.z + radius)
			}
			Shape.Direction.Z -> {
				x = (center.x - radius)..(center.x + radius)
				y = (center.y - radius)..(center.y + radius)
				z = (center.z - halfHeight)..(center.z + halfHeight)
			}
		}

		return x.contains(vector.x) && y.contains(vector.y) && z.contains(vector.z) && vector.distance(center.bukkit.toVector().apply {
			when (direction) {
				Shape.Direction.X -> {
					setX(vector.x)
				}
				Shape.Direction.Y -> {
					setY(vector.y)
				}
				Shape.Direction.Z -> {
					setZ(vector.z)
				}
			}
		}) <= radius

	}

	override fun contains(location: Location): Boolean {
		return contains(location.toVector()) && center.world == location.world.name
	}

	override fun asShifted(toWorld: World): DependentShape = copy(
		center = center.copy(world = toWorld.name)
	)

}
