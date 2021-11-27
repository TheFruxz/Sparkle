package de.jet.paper.extension.data

import de.jet.paper.tool.data.JetPath

fun jetPath(base: String) = JetPath(base)

operator fun JetPath.div(subPath: String) = apply {
	fullPath += ".$subPath"
}

operator fun JetPath.plus(subPath: String) = this / subPath