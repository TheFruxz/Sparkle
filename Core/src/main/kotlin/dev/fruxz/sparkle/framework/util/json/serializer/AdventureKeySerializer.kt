package dev.fruxz.sparkle.framework.util.json.serializer

import kotlinx.serialization.ContextualSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.key.Key
import kotlin.reflect.KClass

@OptIn(ExperimentalSerializationApi::class)
object AdventureKeySerializer : KSerializer<Key> {

	override val descriptor = ContextualSerializer(KeyImpl, null, emptyArray()).descriptor

	override fun deserialize(decoder: Decoder): Key = Key.key(decoder.decodeString())

	override fun serialize(encoder: Encoder, value: Key) = encoder.encodeString(value.asString())

}

internal val KeyImpl: KClass<*> = Class.forName("net.kyori.adventure.key.KeyImpl").kotlin