package dev.fruxz.sparkle.framework.util.json.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Particle

object ParticleSerializer : KSerializer<Particle> {

	override val descriptor = PrimitiveSerialDescriptor("Particle", PrimitiveKind.STRING)

	override fun deserialize(decoder: Decoder): Particle = Particle.valueOf(decoder.decodeString())

	override fun serialize(encoder: Encoder, value: Particle) = encoder.encodeString(value.name)

}