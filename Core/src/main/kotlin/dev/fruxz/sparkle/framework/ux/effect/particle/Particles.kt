package dev.fruxz.sparkle.framework.ux.effect.particle

import com.destroystokyo.paper.ParticleBuilder
import dev.fruxz.sparkle.framework.ux.effect.EffectDsl
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player

@EffectDsl
fun ParticleBuilder.offset(offset: Number) = offset(offset.toDouble(), offset.toDouble(), offset.toDouble())

@EffectDsl
fun ParticleBuilder.offset(offsetX: Number, offsetZ: Number) = offset(offsetX.toDouble(), this.offsetY(), offsetZ.toDouble())

@EffectDsl
fun buildParticle(type: Particle, builder: ParticleBuilder.() -> Unit): ParticleBuilder = ParticleBuilder(type).apply(builder)

@EffectDsl
fun ParticleBuilder.copy(
    particle: Particle = particle(),
    receivers: List<Player>? = receivers(),
    source: Player? = source(),
    location: Location? = location(),
    count: Int = count(),
    offsetX: Double = offsetX(),
    offsetY: Double = offsetY(),
    offsetZ: Double = offsetZ(),
    extra: Double = extra(),
    data: Any? = data(),
    force: Boolean = force(),
): ParticleBuilder = ParticleBuilder(particle)
    .receivers(receivers)
    .source(source)
    .count(count)
    .offset(offsetX, offsetY, offsetZ)
    .extra(extra)
    .data(data)
    .force(force)
    .let { if (location != null) it.location(location) else it }