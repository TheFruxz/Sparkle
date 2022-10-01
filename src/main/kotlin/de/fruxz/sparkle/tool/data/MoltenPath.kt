package de.fruxz.sparkle.tool.data

import de.fruxz.ascend.tool.smart.identification.Identifiable

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
