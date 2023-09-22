package dev.fruxz.sparkle.framework.world

import dev.fruxz.ascend.tool.math.decimal.Percentage
import dev.fruxz.ascend.tool.math.decimal.Percentage.Companion.percent
import org.bukkit.Location
import org.bukkit.RegionAccessor
import org.bukkit.World
import org.bukkit.block.structure.Mirror
import org.bukkit.block.structure.StructureRotation
import org.bukkit.structure.Structure
import org.bukkit.util.BlockVector
import org.bukkit.util.BoundingBox
import kotlin.random.Random
import kotlin.random.asJavaRandom

/**
 * This function utilizes the [Structure.fill] function, to use the locations
 * of the [cubicalShape] to fill the structure with the content of the shape.
 * @param cubicalShape the area to fill into the shape
 * @param includeEntities if entities are filled into too
 * @return the modified instance
 * @author Fruxz
 * @since 1.0
 */
/* TODO
fun Structure.fill(
    cubicalShape: DependentCubicalShape,
    includeEntities: Boolean = true
) = apply { with(cubicalShape.sorted()) { fill(firstLocation, secondLocation.copy().add(1, 1, 1), includeEntities) } }
*/

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

/*TODO fun Structure.place(
    location: Location,
    includeEntities: Boolean = true,
    structureRotation: StructureRotation = StructureRotation.NONE,
    mirror: Mirror = Mirror.NONE,
    palette: Int = -1,
    integrity: Percentage = 100.percent,
    random: Random = Random,
): PlacedStructure {
    place(location, includeEntities, structureRotation, mirror, palette, integrity.decimal.toFloat(), random.asJavaRandom())

    return PlacedStructure(this, Shape.cube(location, getSecondCorner(location, structureRotation, mirror)))
}*/

fun Structure.place(
    regionAccessor: RegionAccessor,
    location: BlockVector,
    includeEntities: Boolean = true,
    structureRotation: StructureRotation = StructureRotation.NONE,
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
        StructureRotation.NONE -> {
            when (mirror) {
                Mirror.NONE -> {
                    secondCorner.x += structureLengthX - 1
                    secondCorner.y += structureHeightY - 1
                    secondCorner.z += structureDepthZ - 1
                }
                Mirror.FRONT_BACK -> {
                    secondCorner.x -= structureLengthX - 1
                    secondCorner.y += structureHeightY - 1
                    secondCorner.z += structureDepthZ - 1
                }
                Mirror.LEFT_RIGHT -> {
                    secondCorner.x += structureLengthX - 1
                    secondCorner.y += structureHeightY - 1
                    secondCorner.z -= structureDepthZ - 1
                }
            }
        }
        StructureRotation.CLOCKWISE_90 -> {
            when (mirror) {
                Mirror.NONE -> {
                    secondCorner.x -= structureDepthZ - 1
                    secondCorner.y += structureHeightY - 1
                    secondCorner.z += structureLengthX - 1
                }
                Mirror.FRONT_BACK -> {
                    secondCorner.x -= structureDepthZ - 1
                    secondCorner.y += structureHeightY - 1
                    secondCorner.z -= structureLengthX - 1
                }
                Mirror.LEFT_RIGHT -> {
                    secondCorner.x += structureDepthZ - 1
                    secondCorner.y += structureHeightY - 1
                    secondCorner.z += structureLengthX - 1
                }
            }
        }
        StructureRotation.CLOCKWISE_180 -> {
            when (mirror) {
                Mirror.NONE -> {
                    secondCorner.x -= structureLengthX - 1
                    secondCorner.y += structureHeightY - 1
                    secondCorner.z -= structureDepthZ - 1
                }
                Mirror.FRONT_BACK -> {
                    secondCorner.x += structureLengthX - 1
                    secondCorner.y += structureHeightY - 1
                    secondCorner.z -= structureDepthZ - 1
                }
                Mirror.LEFT_RIGHT -> {
                    secondCorner.x -= structureLengthX - 1
                    secondCorner.y += structureHeightY - 1
                    secondCorner.z += structureDepthZ - 1
                }
            }
        }
        StructureRotation.COUNTERCLOCKWISE_90 -> {
            when (mirror) {
                Mirror.NONE -> {
                    secondCorner.x += structureDepthZ - 1
                    secondCorner.y += structureHeightY - 1
                    secondCorner.z -= structureLengthX - 1
                }
                Mirror.FRONT_BACK -> {
                    secondCorner.x += structureDepthZ - 1
                    secondCorner.y += structureHeightY - 1
                    secondCorner.z += structureLengthX - 1
                }
                Mirror.LEFT_RIGHT -> {
                    secondCorner.x -= structureDepthZ - 1
                    secondCorner.y += structureHeightY - 1
                    secondCorner.z -= structureLengthX - 1
                }
            }
        }
    }

    return secondCorner
}

/* TODO
data class PlacedStructure(
    val raw: Structure,
    val destination: DependentCubicalShape,
)*/
