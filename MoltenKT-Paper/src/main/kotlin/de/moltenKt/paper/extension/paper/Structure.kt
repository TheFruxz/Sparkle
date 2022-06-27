package de.moltenKt.paper.extension.paper

import de.moltenKt.core.tool.math.Percentage
import de.moltenKt.core.tool.math.Percentage.Companion.percent
import de.moltenKt.paper.extension.effect.buildParticle
import de.moltenKt.paper.extension.effect.offset
import de.moltenKt.paper.extension.tasky.doAsync
import de.moltenKt.paper.tool.effect.particle.ParticleType
import de.moltenKt.paper.tool.effect.particle.ParticleType.Companion
import de.moltenKt.paper.tool.position.dependent.DependentCubicalShape
import kotlinx.coroutines.delay
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.RegionAccessor
import org.bukkit.World
import org.bukkit.block.structure.Mirror
import org.bukkit.block.structure.StructureRotation
import org.bukkit.block.structure.StructureRotation.NONE
import org.bukkit.structure.Structure
import org.bukkit.util.BlockVector
import org.bukkit.util.BoundingBox
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
	random: Random = Random.Default,
) = apply { place(location, includeEntities, structureRotation, mirror, palette, integrity.decimal.toFloat(), random.asJavaRandom()) }

fun Structure.place(
	regionAccessor: RegionAccessor,
	location: BlockVector,
	includeEntities: Boolean = true,
	structureRotation: StructureRotation = NONE,
	mirror: Mirror = Mirror.NONE,
	palette: Int = -1,
	integrity: Percentage = 100.percent,
	random: Random = Random.Default,
) = apply { place(regionAccessor, location, includeEntities, structureRotation, mirror, palette, integrity.decimal.toFloat(), random.asJavaRandom()) }