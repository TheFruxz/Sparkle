package de.fruxz.sparkle.extension.data

import de.fruxz.sparkle.tool.data.MoltenPath

fun moltenPath(base: String) = MoltenPath(base)

operator fun MoltenPath.div(subPath: String) = apply {
	fullPath += ".$subPath"
}

operator fun MoltenPath.plus(subPath: String) = this / subPath