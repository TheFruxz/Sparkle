package de.jet.library.extension.data

import de.jet.minecraft.tool.data.JetPath

fun jetPath(base: String) = JetPath(base)

operator fun JetPath.div(subPath: String) = apply {
	fullPath += ".$subPath"
}

operator fun JetPath.plus(subPath: String) = this / subPath