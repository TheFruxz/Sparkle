package de.fruxz.sparkle.framework.data

import de.fruxz.ascend.tool.smart.identification.Identifiable

/**
 * The path used inside yaml files
 */
data class SparklePath(
	private val base: String,
): Identifiable<SparklePath> {

	var fullPath: String = base

	override val identity: String
		get() = fullPath

	//

}
