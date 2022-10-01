package de.fruxz.sparkle.extension.data

import de.fruxz.sparkle.tool.data.SparklePath

fun sparklePath(base: String) = SparklePath(base)

operator fun SparklePath.div(subPath: String) = apply {
	fullPath += ".$subPath"
}

operator fun SparklePath.plus(subPath: String) = this / subPath