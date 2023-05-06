package dev.fruxz.sparkle.framework.util.json.serializer

import com.destroystokyo.paper.ParticleBuilder
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import org.bukkit.Particle

object ParticleBuilderSerializer : KSerializer<ParticleBuilder> {

	override val descriptor = buildClassSerialDescriptor("ParticleBuilder") {
		element<Particle>("particle")
		element("location", LocationSerializer.descriptor)
		element<Double>("offsetX")
		element<Double>("offsetY")
		element<Double>("offsetZ")
		element<Double>("extra")
		element<Boolean>("force")
	}

	override fun deserialize(decoder: Decoder) = decoder.decodeStructure(descriptor) {
		ParticleBuilder(decodeSerializableElement(descriptor, decodeElementIndex(descriptor), ParticleSerializer))
			.location(decodeSerializableElement(descriptor, decodeElementIndex(descriptor), LocationSerializer))
			.count(decodeIntElement(descriptor, decodeElementIndex(descriptor)))
			.offset(
				decodeDoubleElement(descriptor, decodeElementIndex(descriptor)),
				decodeDoubleElement(descriptor, decodeElementIndex(descriptor)),
				decodeDoubleElement(descriptor, decodeElementIndex(descriptor)),
			)
			.extra(decodeDoubleElement(descriptor, decodeElementIndex(descriptor)))
			.force(decodeBooleanElement(descriptor, decodeElementIndex(descriptor)))
	}

	@OptIn(ExperimentalSerializationApi::class)
	override fun serialize(encoder: Encoder, value: ParticleBuilder) = with(value) {
		with(encoder) {
			encodeStructure(descriptor) {
				encodeSerializableElement(descriptor, 0, ParticleSerializer, particle())
				encodeNullableSerializableElement(descriptor, 3, LocationSerializer, location())
				encodeIntElement(descriptor, 4, count())
				encodeDoubleElement(descriptor, 5, offsetX())
				encodeDoubleElement(descriptor, 6, offsetY())
				encodeDoubleElement(descriptor, 7, offsetZ())
				encodeDoubleElement(descriptor, 8, extra())
				encodeBooleanElement(descriptor, 9, force())
			}
		}
	}

}