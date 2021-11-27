package de.jet.minecraft.app.component.essentials.world

import de.jet.library.extension.switchResult
import de.jet.library.structure.DataStructureHolder
import de.jet.library.structure.DataStructureItem
import de.jet.jvm.tool.smart.positioning.Address
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class WorldStructureHolder(
	override val smashedStructure: List<WorldStructureObject>
) : DataStructureHolder<WorldStructureObject>(smashedStructure) {

}

interface WorldStructureObject : DataStructureItem {
	val displayName: String
	val labels: List<String>
	val archived: Boolean

	fun renderLabels() = with(labels) { isEmpty().switchResult("No Labels", joinToString())}

	fun renderArchiveStatus() = archived.switchResult("§bArchived", "§aActive")

}

@Serializable
@SerialName("WorldStructureWorld")
data class WorldStructureWorld(
	override val displayName: String,
	override val identity: String,
	override val path: Address<DataStructureItem>,
	override val labels: List<String>,
	override val archived: Boolean,
	val visitors: List<String>
) : WorldStructureObject

@Serializable
@SerialName("WorldStructureDirectory")
data class WorldStructureDirectory(
	override val displayName: String,
	override val identity: String,
	override val path: Address<DataStructureItem>,
	override val labels: List<String>,
	override val archived: Boolean
) : WorldStructureObject