package de.fruxz.sparkle.framework.extension.effect

import com.destroystokyo.paper.ParticleBuilder
import de.fruxz.sparkle.framework.effect.EffectDsl
import de.fruxz.sparkle.framework.effect.particle.ParticleData
import de.fruxz.sparkle.framework.effect.particle.ParticleType
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player

@EffectDsl
@Throws(IllegalStateException::class)
fun ParticleBuilder.playParticleEffect(reach: Double = .0) {
	val location = location()
	val internalReceivers = receivers()?.toList() ?: location?.world?.players

	if (location != null) {
		internalReceivers!!

		if (reach > 0) {
			val participants = location.getNearbyPlayers(reach).filter { internalReceivers.contains(it) }
			val computedParticleBuilder = receivers(participants)

			computedParticleBuilder.spawn()

		} else
			spawn()

	} else
		throw IllegalStateException("'location'[bukkit.Location] of ParticleBuilder cannot be null!")
}

@EffectDsl
@Throws(IllegalStateException::class)
fun ParticleBuilder.playParticleEffect(reach: Number = .0) =
	playParticleEffect(reach.toDouble())

@EffectDsl
fun ParticleBuilder.playParticleEffect() =
	playParticleEffect(.0)

@EffectDsl
fun ParticleBuilder.offset(offset: Number) = offset(offset.toDouble(), offset.toDouble(), offset.toDouble())

@EffectDsl
fun ParticleBuilder.offset(offsetX: Number, offsetZ: Number) = offset(offsetX.toDouble(), .0, offsetZ.toDouble())

@EffectDsl
fun <T : Any> particleOf(particleType: ParticleType<T>): ParticleData<T> = ParticleData(particleType)

@EffectDsl
fun <T : Any> buildParticle(particleType: ParticleType<T>, builder: ParticleData<T>.() -> Unit) =
	ParticleData(particleType).apply(builder)

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
) = ParticleBuilder(particle)
	.receivers(receivers)
	.source(source)
	.count(count)
	.offset(offsetX, offsetY, offsetZ)
	.extra(extra)
	.data(data)
	.force(force)
	.let { if (location != null) it.location(location) else it }