package de.fruxz.sparkle.framework.util.effect

import org.bukkit.Location
import org.bukkit.entity.Entity

/**
 * This interface represents an effect, which can be played at a
 * location, at an entity and at both parameters at the same time!
 * @author Fruxz
 * @since 1.0
 */
interface CrossBasedEffect : ParallelBasedEffect {

	fun play(locations: Set<Location>, entities: Set<Entity>)

	fun play(location: Location, entities: Set<Entity>) =
		play(setOf(location), entities)

	fun play(entity: Entity, locations: Set<Location>) =
		play(locations, setOf(entity))

	fun play(location: Location, entity: Entity) =
		play(setOf(location), setOf(entity))

}