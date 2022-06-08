package de.moltenKt.paper.tool.position.dependent

import de.moltenKt.paper.tool.display.world.SimpleLocation
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.util.Vector

@Serializable
data class DependentComplexShape<T : DependentShape<T>>(
	val dependentShapes: List<T>
) : DependentShape<DependentComplexShape<T>> {

	override val volume: Double by lazy {
		dependentShapes.sumOf { it.volume }
	}

	override val fullHeight: Double by lazy {
		var outgoing = dependentShapes.first().center
		var minHeight = -.0
		var maxHeight = .0

		for (shape in dependentShapes) {
			val height = shape.center.y - outgoing.y
			if (height < minHeight) {
				minHeight = height
			}
			if (height > maxHeight) {
				maxHeight = height
			}
			outgoing = shape.center
		}

		return@lazy maxHeight - minHeight
	}

	override val fullWidth: Double by lazy {
		var outgoing = dependentShapes.first().center
		var minWidth = -.0
		var maxWidth = .0

		for (shape in dependentShapes) {
			val width = shape.center.x - outgoing.x
			if (width < minWidth) {
				minWidth = width
			}
			if (width > maxWidth) {
				maxWidth = width
			}
			outgoing = shape.center
		}

		return@lazy maxWidth - minWidth
	}

	override val fullDepth: Double by lazy {
		var outgoing = dependentShapes.first().center
		var minDepth = -.0
		var maxDepth = .0

		for (shape in dependentShapes) {
			val depth = shape.center.z - outgoing.z
			if (depth < minDepth) {
				minDepth = depth
			}
			if (depth > maxDepth) {
				maxDepth = depth
			}
			outgoing = shape.center
		}

		return@lazy maxDepth - minDepth
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

	override fun asShifted(toWorld: World): DependentComplexShape<T> = copy(
		dependentShapes = dependentShapes.map { it.asShifted(toWorld) }
	)

}