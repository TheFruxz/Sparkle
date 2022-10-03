package de.fruxz.sparkle.framework.util.data.json.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import org.bukkit.util.Vector

object VectorSerializer : KSerializer<Vector> {

	override val descriptor = buildClassSerialDescriptor("Vector") {
		element<Double>("x")
		element<Double>("y")
		element<Double>("z")
	}

	override fun deserialize(decoder: Decoder) = decoder.decodeStructure(descriptor) {
		Vector(
			decodeDoubleElement(descriptor, decodeElementIndex(descriptor)),
			decodeDoubleElement(descriptor, decodeElementIndex(descriptor)),
			decodeDoubleElement(descriptor, decodeElementIndex(descriptor)),
		)
	}

	override fun serialize(encoder: Encoder, value: Vector) = with(value) {
		with(encoder) {
			encodeStructure(descriptor) {
				encodeDoubleElement(descriptor, 0, x)
				encodeDoubleElement(descriptor, 1, y)
				encodeDoubleElement(descriptor, 2, z)
			}
		}
	}

}