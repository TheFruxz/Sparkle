package de.jet.library.tool.math

data class ModResult(
	val value: Long,
	val divide: Long,
) {

	val fittingTimes = value / divide

	val notUsed = value % divide

}