package de.moltenKt.paper.structure.command.completion.tracing

import de.moltenKt.jvm.tool.smart.positioning.Address
import de.moltenKt.jvm.tree.TreeBranch

data class PossibleTraceWay<T : TreeBranch<*, *, *>>(
	val address: Address<T>,
	val branch: T,
	val cachedCompletion: List<String>,
	val tracingDepth: Int,
	val usedQueryState: List<String>,
)