package de.moltenKt.paper.tool.data.json.serializer

import com.destroystokyo.paper.ParticleBuilder
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player

object ParticleBuilderSerializer : KSerializer<ParticleBuilder> {

	override val descriptor = buildClassSerialDescriptor("ParticleBuilder") {
		element<Particle>("particle")
		element<List<Player>>("receivers")
		element<Player>("source")
		element("location", LocationSerializer.descriptor)
		element<Double>("offsetX")
		element<Double>("offsetY")
		element<Double>("offsetZ")
		element<Double>("extra")
		element<Boolean>("force")
	}

	override fun deserialize(decoder: Decoder) = decoder.decodeStructure(descriptor) {
		ParticleBuilder(decodeSerializableElement(descriptor, decodeElementIndex(descriptor), ParticleSerializer))
			.receivers(decodeSerializableElement(descriptor, decodeElementIndex(descriptor), ListSerializer(PlayerSerializer)))
			.source(decodeSerializableElement(descriptor, decodeElementIndex(descriptor), PlayerSerializer))
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
				encodeNullableSerializableElement(descriptor, 1, ListSerializer(PlayerSerializer), receivers()?.toList())
				encodeNullableSerializableElement(descriptor, 2, PlayerSerializer, source())
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