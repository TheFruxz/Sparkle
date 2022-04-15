package de.moltenKt.paper.extension.effect

import com.destroystokyo.paper.ParticleBuilder

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

@Throws(IllegalStateException::class)
fun ParticleBuilder.playParticleEffect(reach: Number = .0) =
	playParticleEffect(reach.toDouble())

fun ParticleBuilder.playParticleEffect() =
	playParticleEffect(.0)

fun ParticleBuilder.offset(offset: Number) = offset(offset.toDouble(), offset.toDouble(), offset.toDouble())

fun ParticleBuilder.offset(offsetX: Number, offsetZ: Number) = offset(offsetX.toDouble(), .0, offsetZ.toDouble())