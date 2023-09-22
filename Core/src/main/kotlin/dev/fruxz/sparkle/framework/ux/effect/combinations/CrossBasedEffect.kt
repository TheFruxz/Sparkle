package dev.fruxz.sparkle.framework.ux.effect.combinations

import org.bukkit.Location
import org.bukkit.entity.Entity

interface CrossBasedEffect : ParallelBasedEffect {

    fun play(locationSet: Set<Location>, entitySet: Set<Entity>)

    fun play(entities: Set<Entity>, vararg locations: Location?) =
        play(locationSet = locations.filterNotNull().toSet(), entitySet = entities)

    fun play(locations: Set<Location>, vararg entities: Entity?) =
        play(locationSet = locations, entitySet = entities.filterNotNull().toSet())

    fun play(location: Location, entity: Entity) =
        play(locationSet = setOf(location), entitySet = setOf(entity))

}