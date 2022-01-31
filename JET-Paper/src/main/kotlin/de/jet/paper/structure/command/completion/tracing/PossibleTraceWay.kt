package de.jet.paper.structure.command.completion.tracing

import de.jet.jvm.tool.smart.positioning.Address
import de.jet.jvm.tree.TreeBranch
import de.jet.jvm.tree.TreeBranchType
import de.jet.paper.structure.command.completion.CompletionBranch
import de.jet.paper.structure.command.completion.component.CompletionComponent

data class PossibleTraceWay(
	val address: Address<TreeBranch<CompletionBranch, List<CompletionComponent>, TreeBranchType>>,
	val cachedCompletion: List<String>,
	val usedQueryState: List<String>,
)