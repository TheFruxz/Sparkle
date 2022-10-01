package de.fruxz.sparkle.tool.data.json.serializer

import de.fruxz.sparkle.extension.paper.Location
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import org.bukkit.Location

object LocationSerializer : KSerializer<Location> {

	override val descriptor = buildClassSerialDescriptor("Location") {
		element<String>("world")
		element<Double>("x")
		element<Double>("y")
		element<Double>("z")
		element<Float>("yaw")
		element<Float>("pitch")
	}

	override fun deserialize(decoder: Decoder) = decoder.decodeStructure(descriptor) {
		Location(
			worldName = decodeStringElement(descriptor, decodeElementIndex(descriptor)),
			x = decodeDoubleElement(descriptor, decodeElementIndex(descriptor)),
			y = decodeDoubleElement(descriptor, decodeElementIndex(descriptor)),
			z = decodeDoubleElement(descriptor, decodeElementIndex(descriptor)),
			yaw = decodeFloatElement(descriptor, decodeElementIndex(descriptor)),
			pitch = decodeFloatElement(descriptor, decodeElementIndex(descriptor)),
		)
	}

	override fun serialize(encoder: Encoder, value: Location) = with(value) {
		with(encoder) {
			encodeStructure(descriptor) {
				encodeStringElement(descriptor, 0, world.name)
				encodeDoubleElement(descriptor, 1, x)
				encodeDoubleElement(descriptor, 2, y)
				encodeDoubleElement(descriptor, 3, z)
				encodeFloatElement(descriptor, 4, yaw)
				encodeFloatElement(descriptor, 5, pitch)
			}
		}
	}

}