package de.moltenKt.paper.extension.paper

import de.fruxz.ascend.extension.div
import de.fruxz.ascend.extension.getHomePath
import de.fruxz.ascend.tool.math.Percentage
import de.fruxz.ascend.tool.math.Percentage.Companion.percent
import de.moltenKt.paper.extension.effect.buildParticle
import de.moltenKt.paper.extension.effect.offset
import de.moltenKt.paper.extension.tasky.doAsync
import de.moltenKt.paper.tool.effect.particle.ParticleType
import de.moltenKt.paper.tool.effect.particle.ParticleType.Companion
import de.moltenKt.paper.tool.position.dependent.DependentCubicalShape
import de.moltenKt.paper.tool.position.relative.Shape
import kotlinx.coroutines.delay
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.RegionAccessor
import org.bukkit.World
import org.bukkit.block.structure.Mirror
import org.bukkit.block.structure.Mirror.FRONT_BACK
import org.bukkit.block.structure.Mirror.LEFT_RIGHT
import org.bukkit.block.structure.StructureRotation
import org.bukkit.block.structure.StructureRotation.*
import org.bukkit.structure.Structure
import org.bukkit.util.BlockVector
import org.bukkit.util.BoundingBox
import kotlin.io.path.Path
import kotlin.io.path.writeText
import kotlin.random.Random
import kotlin.random.asJavaRandom
import kotlin.time.Duration.Companion.seconds

/**
 * This function utilizes the [Structure.fill] function, to use the locations
 * of the [cubicalShape] to fill the structure with the content of the shape.
 * @param cubicalShape the area to fill into the shape
 * @param includeEntities if entities are filled into too
 * @return the modified instance
 * @author Fruxz
 * @since 1.0
 */
fun Structure.fill(
	cubicalShape: DependentCubicalShape,
	includeEntities: Boolean = true
) = apply { with(cubicalShape.sorted()) { fill(firstLocation, secondLocation.copy().add(1, 1, 1), includeEntities) } }

/**
 * This function utilizes the [Structure.fill] function, to use the locations
 * of the [boundingBox] to fill the structure with the content of the box.
 * @param boundingBox the area to fill into the shape
 * @param world the world, on which the [boundingBox] is located
 * @param includeEntities if entities are filled into too
 * @return the modified instance
 * @author Fruxz
 * @since 1.0
 */
fun Structure.fill(
	boundingBox: BoundingBox,
	world: World,
	includeEntities: Boolean = true
) = apply { fill(boundingBox.min.toLocation(world), boundingBox.max.toLocation(world).clone().add(1, 1, 1), includeEntities) }

fun Structure.place(
	location: Location,
	includeEntities: Boolean = true,
	structureRotation: StructureRotation = NONE,
	mirror: Mirror = Mirror.NONE,
	palette: Int = -1,
	integrity: Percentage = 100.percent,
	random: Random = Random,
): PlacedStructure {
	place(location, includeEntities, structureRotation, mirror, palette, integrity.decimal.toFloat(), random.asJavaRandom())

	return PlacedStructure(this, Shape.cube(location, getSecondCorner(location, structureRotation, mirror)))
}

fun Structure.place(
	regionAccessor: RegionAccessor,
	location: BlockVector,
	includeEntities: Boolean = true,
	structureRotation: StructureRotation = NONE,
	mirror: Mirror = Mirror.NONE,
	palette: Int = -1,
	integrity: Percentage = 100.percent,
	random: Random = Random,
) = apply { place(regionAccessor, location, includeEntities, structureRotation, mirror, palette, integrity.decimal.toFloat(), random.asJavaRandom()) }

fun Structure.getSecondCorner(firstCorner: Location, rotationOfThisStructure: StructureRotation, mirror: Mirror): Location {
	val secondCorner = firstCorner.copy(
		x = firstCorner.x,
		y = firstCorner.y,
		z = firstCorner.z
	)

	val structureLengthX = size.x
	val structureHeightY = size.y
	val structureDepthZ = size.z

	when (rotationOfThisStructure) {
		NONE -> {
			when (mirror) {
				Mirror.NONE -> {
					secondCorner.x += structureLengthX - 1
					secondCorner.y += structureHeightY - 1
					secondCorner.z += structureDepthZ - 1
				}
				FRONT_BACK -> {
					secondCorner.x -= structureLengthX - 1
					secondCorner.y += structureHeightY - 1
					secondCorner.z += structureDepthZ - 1
				}
				LEFT_RIGHT -> {
					secondCorner.x += structureLengthX - 1
					secondCorner.y += structureHeightY - 1
					secondCorner.z -= structureDepthZ - 1
				}
			}
		}
		CLOCKWISE_90 -> {
			when (mirror) {
				Mirror.NONE -> {
					secondCorner.x -= structureDepthZ - 1
					secondCorner.y += structureHeightY - 1
					secondCorner.z += structureLengthX - 1
				}
				FRONT_BACK -> {
					secondCorner.x -= structureDepthZ - 1
					secondCorner.y += structureHeightY - 1
					secondCorner.z -= structureLengthX - 1
				}
				LEFT_RIGHT -> {
					secondCorner.x += structureDepthZ - 1
					secondCorner.y += structureHeightY - 1
					secondCorner.z += structureLengthX - 1
				}
			}
		}
		CLOCKWISE_180 -> {
			when (mirror) {
				Mirror.NONE -> {
					secondCorner.x -= structureLengthX - 1
					secondCorner.y += structureHeightY - 1
					secondCorner.z -= structureDepthZ - 1
				}
				FRONT_BACK -> {
					secondCorner.x += structureLengthX - 1
					secondCorner.y += structureHeightY - 1
					secondCorner.z -= structureDepthZ - 1
				}
				LEFT_RIGHT -> {
					secondCorner.x -= structureLengthX - 1
					secondCorner.y += structureHeightY - 1
					secondCorner.z += structureDepthZ - 1
				}
			}
		}
		COUNTERCLOCKWISE_90 -> {
			when (mirror) {
				Mirror.NONE -> {
					secondCorner.x += structureDepthZ - 1
					secondCorner.y += structureHeightY - 1
					secondCorner.z -= structureLengthX - 1
				}
				FRONT_BACK -> {
					secondCorner.x += structureDepthZ - 1
					secondCorner.y += structureHeightY - 1
					secondCorner.z += structureLengthX - 1
				}
				LEFT_RIGHT -> {
					secondCorner.x -= structureDepthZ - 1
					secondCorner.y += structureHeightY - 1
					secondCorner.z -= structureLengthX - 1
				}
			}
		}
	}

	return secondCorner
}

data class PlacedStructure(
	val raw: Structure,
	val destination: DependentCubicalShape,
)