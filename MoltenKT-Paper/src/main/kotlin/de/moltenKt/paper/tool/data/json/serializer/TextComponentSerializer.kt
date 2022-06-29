package de.moltenKt.paper.tool.data.json.serializer

import de.moltenKt.unfold.extension.asStyledComponent
import de.moltenKt.unfold.extension.asStyledString
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.text.TextComponent

object TextComponentSerializer : KSerializer<TextComponent> {

	override val descriptor = PrimitiveSerialDescriptor("TextComponent", PrimitiveKind.STRING)

	override fun deserialize(decoder: Decoder) = decoder.decodeString().asStyledComponent

	override fun serialize(encoder: Encoder, value: TextComponent) = encoder.encodeString(value.asStyledString)

}