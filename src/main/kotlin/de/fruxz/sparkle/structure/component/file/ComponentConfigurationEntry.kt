package de.fruxz.sparkle.structure.component.file

import kotlinx.serialization.Serializable

@Serializable
data class ComponentConfigurationEntry(
	val identity: String,
	var isAutoStart: Boolean,
	var isBlocked: Boolean,
)
