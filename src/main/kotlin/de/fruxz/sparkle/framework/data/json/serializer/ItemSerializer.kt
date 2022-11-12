package de.fruxz.sparkle.framework.data.json.serializer

import de.fruxz.sparkle.framework.visual.item.Item
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ItemSerializer : KSerializer<Item> {

	override val descriptor = PrimitiveSerialDescriptor("Item", PrimitiveKind.STRING)

	override fun deserialize(decoder: Decoder): Item = Item(ItemStackSerializer.deserialize(decoder))

	override fun serialize(encoder: Encoder, value: Item) = ItemStackSerializer.serialize(encoder, value.asItemStack())

}