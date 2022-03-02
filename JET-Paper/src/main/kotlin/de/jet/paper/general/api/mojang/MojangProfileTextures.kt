package de.jet.paper.general.api.mojang

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("MojangProfileTextures")
data class MojangProfileTextures @OptIn(ExperimentalSerializationApi::class) constructor(
	val cape: MojangProfileCape = MojangProfileCape("empty", "empty"),
	val custom: Boolean,
	val raw: MojangProfileRaw,
	val skin: MojangProfileSkin,
	val slim: Boolean
)