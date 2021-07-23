package de.jet.library.tool.data

import de.jet.library.structure.smart.Identifiable

/**
 * The path used inside yaml files
 */
data class JetPath(
	private val base: String,
): Identifiable<JetPath> {

	var fullPath: String = base

	override val id: String
		get() = fullPath

}
