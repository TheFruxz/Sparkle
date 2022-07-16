package de.moltenKt.paper.tool.data.json.serializer

import de.moltenKt.paper.extension.paper.world
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