package de.jet.library.extension.effect

import com.destroystokyo.paper.ParticleBuilder
import de.jet.library.extension.checkAllObjects
import de.jet.library.extension.isNotNull
import kotlin.jvm.Throws

@Throws(IllegalStateException::class)
fun ParticleBuilder.playParticleEffect(reach: Double = .0, onlySavedReceivers: Boolean = true) {
	val location = location()
	val receivers = if (onlySavedReceivers) receivers() else location?.world?.players

	if (checkAllObjects(location, receivers) { isNotNull }) {
		receivers!!
		location!!

		if (reach > 0) {
			val participants = location.getNearbyPlayers(reach).filter { receivers.contains(it) }
			val computedParticleBuilder = receivers(participants)

			computedParticleBuilder.spawn()

		} else
			spawn()

	} else
		throw IllegalStateException("Both values 'location'[bukkit.Location] ${if (onlySavedReceivers) "and 'receivers'[bukkit.Player[]] " else ""}of ParticleBuilder cannot be null!")
}

@Throws(IllegalStateException::class)
fun ParticleBuilder.playParticleEffect(reach: Number = .0, onlySavedReceivers: Boolean = true) =
	playParticleEffect(reach.toDouble(), onlySavedReceivers)

fun ParticleBuilder.playParticleEffect() =
	playParticleEffect(.0)

fun ParticleBuilder.offset(offset: Number) = offset(offset.toDouble(), offset.toDouble(), offset.toDouble())

fun ParticleBuilder.offset(offsetX: Number, offsetZ: Number) = offset(offsetX.toDouble(), .0, offsetZ.toDouble())