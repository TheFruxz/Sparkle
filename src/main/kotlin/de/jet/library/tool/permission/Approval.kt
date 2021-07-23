package de.jet.library.tool.permission

import de.jet.library.structure.smart.Identifiable
import kotlinx.serialization.Serializable

@Serializable
data class Approval(
	override val id: String
) : Identifiable<Approval>