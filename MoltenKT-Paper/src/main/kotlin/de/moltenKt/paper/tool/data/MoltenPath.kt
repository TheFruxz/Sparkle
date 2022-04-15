package de.moltenKt.paper.tool.data

import de.moltenKt.jvm.tool.smart.identification.Identifiable

/**
 * The path used inside yaml files
 */
data class MoltenPath(
	private val base: String,
): Identifiable<MoltenPath> {

	var fullPath: String = base

	override val identity: String
		get() = fullPath

}
