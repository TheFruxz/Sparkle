package de.fruxz.sparkle.framework.util.data.json.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import org.bukkit.util.BoundingBox

object BoundingBoxSerializer : KSerializer<BoundingBox> {

	override val descriptor = buildClassSerialDescriptor("BoundingBox") {
		element<Double>("x1")
		element<Double>("x2")
		element<Double>("y1")
		element<Double>("y2")
		element<Double>("z1")
		element<Double>("z2")
	}

	override fun deserialize(decoder: Decoder) = decoder.decodeStructure(descriptor) {
		BoundingBox(
			decodeDoubleElement(descriptor, decodeElementIndex(descriptor)),
			decodeDoubleElement(descriptor, decodeElementIndex(descriptor)),
			decodeDoubleElement(descriptor, decodeElementIndex(descriptor)),
			decodeDoubleElement(descriptor, decodeElementIndex(descriptor)),
			decodeDoubleElement(descriptor, decodeElementIndex(descriptor)),
			decodeDoubleElement(descriptor, decodeElementIndex(descriptor)),
		)
	}

	override fun serialize(encoder: Encoder, value: BoundingBox) = with(value) {
		with(encoder) {
			encodeStructure(descriptor) {
				encodeDoubleElement(descriptor, 0, minX)
				encodeDoubleElement(descriptor, 1, minY)
				encodeDoubleElement(descriptor, 2, minZ)
				encodeDoubleElement(descriptor, 3, maxX)
				encodeDoubleElement(descriptor, 4, maxY)
				encodeDoubleElement(descriptor, 5, maxZ)
			}
		}
	}

}