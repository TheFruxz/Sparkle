package de.moltenKt.paper.extension.paper

import de.moltenKt.paper.tool.position.dependent.DependentCubicalShape
import org.bukkit.World
import org.bukkit.structure.Structure
import org.bukkit.util.BoundingBox

/**
 * This function utilizes the [Structure.fill] function, to use the locations
 * of the [cubicalShape] to fill the structure with the content of the shape.
 * @param cubicalShape the area to fill into the shape
 * @param includeEntities if entities are filled into too
 * @return the modified instance
 * @author Fruxz
 * @since 1.0
 */
fun Structure.fill(cubicalShape: DependentCubicalShape, includeEntities: Boolean = false) =
	apply { with(cubicalShape) { fill(firstLocation, secondLocation.clone().add(1, 1, 1), includeEntities) } }

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
fun Structure.fill(boundingBox: BoundingBox, world: World, includeEntities: Boolean = false) =
	apply { fill(boundingBox.min.toLocation(world), boundingBox.max.toLocation(world).clone().add(1, 1, 1), includeEntities) }