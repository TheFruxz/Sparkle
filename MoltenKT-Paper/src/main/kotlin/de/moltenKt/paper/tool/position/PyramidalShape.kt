package de.moltenKt.paper.tool.position

import de.moltenKt.core.annotation.NotPerfect
import de.moltenKt.core.extension.math.ceil
import de.moltenKt.core.extension.math.ceilToInt
import de.moltenKt.core.extension.math.floor
import de.moltenKt.core.extension.math.floorToInt
import de.moltenKt.paper.tool.display.world.SimpleLocation
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.util.Vector

@Serializable
@NotPerfect
data class PyramidalShape(
	override val center: SimpleLocation,
	val height: Double,
	val groundWidth: Double,
	val groundDepth: Double,
) : Shape {

	val halfWidth = groundWidth / 2
	val halfHeight = height / 2
	val halfDepth = groundDepth / 2

	override val volume: Double by lazy {
		(1/3.0) * (groundWidth * groundDepth) * height
	}

	override val fullHeight: Double by lazy {
		height
	}

	override val fullWidth: Double by lazy {
		groundWidth
	}

	override val fullDepth: Double by lazy {
		groundDepth
	}

	override val blockLocations: List<SimpleLocation> by lazy {
		val output = mutableListOf<SimpleLocation>()

		for (x in center.x.let { (it - halfWidth).floorToInt()..(it + halfWidth).ceilToInt() }) {
			for (y in center.y.let { (it - halfHeight).floorToInt()..(it + halfHeight).ceilToInt() }) {
				for (z in center.z.let { (it - halfDepth).floorToInt()..(it + halfDepth).ceilToInt() }) {
					if (contains(Vector(x.toDouble(), y.toDouble(), z.toDouble()))) {
						output.add(SimpleLocation(center.world, x, y, z))
					}
				}
			}
		}

		return@lazy output
	}

	override fun contains(vector: Vector): Boolean {
		val widthOfPyramidAtHeightY = halfWidth - (((vector.y - center.y) * (groundWidth / height) + halfWidth) / 2)
		val depthOfPyramidAtHeightY = halfDepth - (((vector.y - center.y) * (groundDepth / height) + halfDepth) / 2)

		if (vector.x !in (center.x - widthOfPyramidAtHeightY).floor()..(center.x + widthOfPyramidAtHeightY).ceil()) return false
		if (vector.z !in (center.z - depthOfPyramidAtHeightY).floor()..(center.z + depthOfPyramidAtHeightY).ceil()) return false
		if (vector.y < center.y - halfHeight || vector.y > center.y + halfHeight) return false

		return true
	}

	override fun contains(location: Location): Boolean =
		contains(location.toVector()) && location.world == center.bukkit.world

}