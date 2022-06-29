package de.moltenKt.paper.tool.data.json.serializer

import de.moltenKt.paper.extension.paper.offlinePlayer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.OfflinePlayer

object OfflinePlayerSerializer : KSerializer<OfflinePlayer> {

	override val descriptor = PrimitiveSerialDescriptor("Player", PrimitiveKind.STRING)

	override fun deserialize(decoder: Decoder) = offlinePlayer(decoder.decodeSerializableValue(UUIDSerializer))

	override fun serialize(encoder: Encoder, value: OfflinePlayer) = encoder.encodeSerializableValue(UUIDSerializer, value.uniqueId)

}