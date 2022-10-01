package de.fruxz.sparkle.tool.data.json.serializer

import io.ktor.util.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.inventory.ItemStack

object ItemStackSerializer : KSerializer<ItemStack> {

	override val descriptor = PrimitiveSerialDescriptor("ItemStack", PrimitiveKind.STRING)

	override fun deserialize(decoder: Decoder) = ItemStack.deserializeBytes(decoder.decodeString().decodeBase64Bytes())

	override fun serialize(encoder: Encoder, value: ItemStack) = encoder.encodeString(value.serializeAsBytes().encodeBase64())

}