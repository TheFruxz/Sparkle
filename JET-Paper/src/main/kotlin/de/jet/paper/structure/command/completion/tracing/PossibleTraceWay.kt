package de.jet.paper.structure.command.completion.tracing

import de.jet.jvm.tool.smart.positioning.Address
import de.jet.jvm.tree.TreeBranch

data class PossibleTraceWay<T : TreeBranch<*, *, *>>(
	val address: Address<T>,
	val branch: T,
	val cachedCompletion: List<String>,
	val tracingDepth: Int,
	val usedQueryState: List<String>,
)