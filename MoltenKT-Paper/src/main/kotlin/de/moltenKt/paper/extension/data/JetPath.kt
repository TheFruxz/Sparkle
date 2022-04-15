package de.moltenKt.paper.extension.data

import de.moltenKt.paper.tool.data.MoltenPath

fun moltenPath(base: String) = MoltenPath(base)

operator fun MoltenPath.div(subPath: String) = apply {
	fullPath += ".$subPath"
}

operator fun MoltenPath.plus(subPath: String) = this / subPath