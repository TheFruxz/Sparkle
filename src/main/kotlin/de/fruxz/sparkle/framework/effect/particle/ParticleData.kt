package de.fruxz.sparkle.framework.effect.particle

import com.destroystokyo.paper.ParticleBuilder
import de.fruxz.ascend.extension.dump
import de.fruxz.sparkle.framework.effect.EffectDsl
import de.fruxz.sparkle.framework.positioning.relative.CubicalShape
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

data class ParticleData<T : Any>(
	val type: ParticleType<T>,
) : ParticleBuilder(type.type), ParticleEffect {

    @EffectDsl
    fun putData(data: T) =
        data(data)

    @EffectDsl
    fun offset(cube: CubicalShape) =
        offset(cube.length, cube.height, cube.depth)

    @EffectDsl
    fun edit(block: ParticleData<T>.() -> Unit) = apply(block)

    override fun play(): Unit = spawn().dump()

    override fun play(vararg locations: Location?): Unit = locations.forEach { location ->
        if (location == null) return@forEach
        location(location).spawn()
    }

    override fun play(vararg entities: Entity?): Unit =
        receivers(entities.filterIsInstance<Player>()).spawn().dump()

    override fun play(locations: Set<Location>, entities: Set<Entity>) {
        val receivers = entities.filterIsInstance<Player>()

        locations.forEach { location ->
            location(location).receivers(receivers).spawn()
        }

    }

}