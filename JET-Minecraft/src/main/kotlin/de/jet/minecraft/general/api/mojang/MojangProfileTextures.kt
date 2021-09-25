package de.jet.minecraft.general.api.mojang

import kotlinx.serialization.Serializable

@Serializable
data class MojangProfileTextures(
	val cape: MojangProfileCape = MojangProfileCape("empty", "empty"),
	val custom: Boolean,
	val raw: MojangProfileRaw,
	val skin: MojangProfileSkin,
	val slim: Boolean
)