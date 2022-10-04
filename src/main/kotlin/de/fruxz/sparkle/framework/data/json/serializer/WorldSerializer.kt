package de.fruxz.sparkle.framework.data.json.serializer

import de.fruxz.sparkle.framework.extension.world
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.World

object WorldSerializer : KSerializer<World> {

	override val descriptor = PrimitiveSerialDescriptor("World", PrimitiveKind.STRING)

	override fun deserialize(decoder: Decoder): World = world(decoder.decodeString())

	override fun serialize(encoder: Encoder, value: World) = encoder.encodeString(value.name)

}