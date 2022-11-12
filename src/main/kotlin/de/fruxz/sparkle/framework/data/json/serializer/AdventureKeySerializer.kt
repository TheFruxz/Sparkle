package de.fruxz.sparkle.framework.data.json.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.key.Key

object AdventureKeySerializer : KSerializer<Key> {

	override val descriptor = PrimitiveSerialDescriptor("AdventureKey", PrimitiveKind.STRING)

	override fun deserialize(decoder: Decoder): Key = Key.key(decoder.decodeString())

	override fun serialize(encoder: Encoder, value: Key) = encoder.encodeString(value.asString())

}