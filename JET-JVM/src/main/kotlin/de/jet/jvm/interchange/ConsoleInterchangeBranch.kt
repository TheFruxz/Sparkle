package de.jet.jvm.interchange

import de.jet.jvm.tool.smart.positioning.Address
import de.jet.jvm.tree.TreeBranch
import de.jet.jvm.tree.TreeBranchType

class ConsoleInterchangeBranch(
    identity: String,
    path: Address<TreeBranch<ConsoleInterchangeBranch, ((String) -> Unit)?, TreeBranchType>>,
    subBranches: List<ConsoleInterchangeBranch> = emptyList(),
    branchType: ConsoleInterchangeBranchType = ConsoleInterchangeBranchType.OBJECT,
    content: ((String) -> Unit)?,
) : TreeBranch<ConsoleInterchangeBranch, ((String) -> Unit)?, TreeBranchType>(identity, path, branchType, subBranches, content) {

    fun getBestMatchFromCommandInput(commandInput: String): ConsoleInterchangeBranch? {
        return getBestMatchFromPath(Address(commandInput.replace(" ", "/")))
    }

    fun getBestMatchFromCommandInputWithParameters(commandInput: String): Pair<ConsoleInterchangeBranch?, String> {
        val (branch, remaining) = getBestMatchFromPathWithRemaining(Address(commandInput.replace(" ", "/")))
        return Pair(branch, remaining.addressString.replace("/", " ").removePrefix(" "))
    }

    fun branch(
        identity: String,
        path: Address<TreeBranch<ConsoleInterchangeBranch, ((String) -> Unit)?, TreeBranchType>> = this.path / identity,
        branchType: ConsoleInterchangeBranchType = ConsoleInterchangeBranchType.OBJECT,
        process: ConsoleInterchangeBranch.() -> Unit,
    ) {
        subBranches += ConsoleInterchangeBranch(
            identity = identity,
            path = path,
            subBranches = listOf(),
            branchType = branchType,
            content = null,
        ).apply(process)
    }

}
