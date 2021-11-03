package de.jet.minecraft.tool.data.json

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("JsonCollectionLike")
data class CollectionLike<T : Any>(
	val content: List<T>
)
