package de.moltenKt.paper.app.old_component.essentials.world

import de.moltenKt.jvm.extension.switchResult
import de.moltenKt.jvm.structure.DataStructureHolder
import de.moltenKt.jvm.structure.DataStructureItem
import de.moltenKt.jvm.tool.smart.positioning.Address
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class WorldStructureHolder(
	override val smashedStructure: List<WorldStructureObject>
) : DataStructureHolder<WorldStructureObject>(smashedStructure)

interface WorldStructureObject : DataStructureItem {
	val displayName: String
	val labels: List<String>
	val archived: Boolean

	fun renderLabels() = with(labels) { isEmpty().switchResult("No Labels", joinToString())}

	fun renderArchiveStatus() = archived.switchResult("<aqua>Archived", "<green>Active")

}

@Serializable
@SerialName("WorldStructureWorld")
data class WorldStructureWorld(
	override val displayName: String,
	override val identity: String,
	override val address: Address<DataStructureItem>,
	override val labels: List<String>,
	override val archived: Boolean,
	val visitors: List<String>
) : WorldStructureObject

@Serializable
@SerialName("WorldStructureDirectory")
data class WorldStructureDirectory(
	override val displayName: String,
	override val identity: String,
	override val address: Address<DataStructureItem>,
	override val labels: List<String>,
	override val archived: Boolean
) : WorldStructureObject