package de.fruxz.sparkle.framework.positioning.dependent

import de.fruxz.sparkle.framework.positioning.world.SimpleLocation
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.util.Vector

@Serializable
data class DependentComplexShape(
	val dependentShapes: List<DependentShape>
) : DependentShape {

	override val volume: Double by lazy {
		dependentShapes.sumOf { it.volume }
	}

	override val fullHeight: Double
		get() {
			val highest = dependentShapes.maxBy { it.center.y }
			val lowest = dependentShapes.maxBy { it.center.y }

			return ((highest.fullHeight / 2) - (lowest.fullHeight / 2))
		}

	override val fullWidth: Double
		get() {
			val highest = dependentShapes.maxBy { it.center.x }
			val lowest = dependentShapes.maxBy { it.center.x }

			return ((highest.fullWidth / 2) - (lowest.fullWidth / 2))
		}

	override val fullDepth: Double
		get() {
			val highest = dependentShapes.maxBy { it.center.z }
			val lowest = dependentShapes.maxBy { it.center.z }

			return ((highest.fullDepth / 2) - (lowest.fullDepth / 2))
		}

	override val center: SimpleLocation by lazy {
		SimpleLocation(
			dependentShapes.first().center.world,
			dependentShapes.map { it.center.x }.average(),
			dependentShapes.map { it.center.y }.average(),
			dependentShapes.map { it.center.z }.average(),
		)
	}

	override val blockLocations: List<SimpleLocation> by lazy {
		dependentShapes.flatMap { it.blockLocations }.distinctBy { it.x to it.y to it.z }
	}

	override fun contains(vector: Vector): Boolean = dependentShapes.any { it.contains(vector) }

	override fun contains(location: Location): Boolean = dependentShapes.any { it.contains(location) }

	override fun asShifted(toWorld: World): DependentShape = copy(
		dependentShapes = dependentShapes.map { it.asShifted(toWorld) }
	)

}