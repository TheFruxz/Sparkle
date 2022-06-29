package de.moltenKt.paper.tool.data.json.serializer

import de.moltenKt.paper.extension.paper.player
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.entity.Player

object PlayerSerializer : KSerializer<Player> {

	override val descriptor = PrimitiveSerialDescriptor("Player", PrimitiveKind.STRING)

	override fun deserialize(decoder: Decoder) = player(decoder.decodeSerializableValue(UUIDSerializer))

	override fun serialize(encoder: Encoder, value: Player) = encoder.encodeSerializableValue(UUIDSerializer, value.uniqueId)

}