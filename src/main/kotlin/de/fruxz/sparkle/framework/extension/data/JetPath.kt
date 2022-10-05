package de.fruxz.sparkle.framework.extension.data

import de.fruxz.sparkle.framework.data.SparklePath

fun sparklePath(base: String) = SparklePath(base)

operator fun SparklePath.div(subPath: String) = apply {
	fullPath += ".$subPath"
}

operator fun SparklePath.plus(subPath: String) = this / subPath