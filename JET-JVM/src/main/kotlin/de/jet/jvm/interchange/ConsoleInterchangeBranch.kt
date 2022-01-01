package de.jet.jvm.interchange

import de.jet.jvm.tool.smart.positioning.Address
import de.jet.jvm.tree.TreeBranchType
import de.jet.jvm.tree.TreeBranch

class ConsoleInterchangeBranch(
    identity: String,
    path: Address<TreeBranch<ConsoleInterchangeBranch, ((String) -> Unit)?, TreeBranchType>>,
    subBranches: List<ConsoleInterchangeBranch> = emptyList(),
    content: ((String) -> Unit)? = null,
) : TreeBranch<ConsoleInterchangeBranch, ((String) -> Unit)?, TreeBranchType>(identity, path, ConsoleInterchangeBranchType, subBranches, content) {

    fun getBestMatchFromCommandInput(commandInput: String): ConsoleInterchangeBranch? {
        return getBestMatchFromPath(Address(commandInput.replace(" ", "/")))
    }

    fun getBestMatchFromCommandInputWithParameters(commandInput: String): Pair<ConsoleInterchangeBranch?, String> {
        val (branch, remaining) = getBestMatchFromPathWithRemaining(Address(commandInput.replace(" ", "/")))
        return Pair(branch, remaining.addressString.replace("/", " "))
    }

}