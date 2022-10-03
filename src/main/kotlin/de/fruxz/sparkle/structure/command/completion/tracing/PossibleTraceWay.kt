package de.fruxz.sparkle.structure.command.completion.tracing

import de.fruxz.ascend.tool.smart.positioning.Address
import de.fruxz.ascend.tree.TreeBranch

data class PossibleTraceWay<T : TreeBranch<*, *, *>>(
	val address: Address<T>,
	val branch: T,
	val cachedCompletion: List<String>,
	val tracingDepth: Int,
	val usedQueryState: List<String>,
)