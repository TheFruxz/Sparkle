package de.fruxz.sparkle.framework.util.extension.data

import de.fruxz.sparkle.framework.util.data.SparklePath

fun sparklePath(base: String) = SparklePath(base)

operator fun SparklePath.div(subPath: String) = apply {
	fullPath += ".$subPath"
}

operator fun SparklePath.plus(subPath: String) = this / subPath