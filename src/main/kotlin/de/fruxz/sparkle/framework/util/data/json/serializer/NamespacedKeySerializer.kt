package de.fruxz.sparkle.framework.util.data.json.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.NamespacedKey

object NamespacedKeySerializer : KSerializer<NamespacedKey> {

	override val descriptor = PrimitiveSerialDescriptor("NamespacedKey", PrimitiveKind.STRING)

	override fun deserialize(decoder: Decoder): NamespacedKey = NamespacedKey.fromString(decoder.decodeString())!!

	override fun serialize(encoder: Encoder, value: NamespacedKey) = encoder.encodeString(value.asString())

}