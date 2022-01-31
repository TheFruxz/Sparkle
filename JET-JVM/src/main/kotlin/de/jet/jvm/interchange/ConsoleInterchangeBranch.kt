package de.jet.jvm.interchange

import de.jet.jvm.tool.smart.positioning.Address
import de.jet.jvm.tree.TreeBranch

/**
 * This class defines a branch of the console interchange.
 * @author Fruxz
 * @since 1.0
 */
class ConsoleInterchangeBranch(
	override var identity: String,
	override var address: Address<TreeBranch<ConsoleInterchangeBranch, ((String) -> Unit)?, ConsoleInterchangeBranchType>>,
	override var subBranches: List<ConsoleInterchangeBranch> = emptyList(),
	override var branchType: ConsoleInterchangeBranchType = ConsoleInterchangeBranchType.OBJECT,
	override var content: ((String) -> Unit)?,
) : TreeBranch<ConsoleInterchangeBranch, ((String) -> Unit)?, ConsoleInterchangeBranchType>(identity, address, branchType, subBranches, content) {

    /**
     * This function returns the best matching branch of the [commandInput] input
     * @param commandInput The input to match
     * @return The best matching branch
     * @since 1.0
     * @author Fruxz
     */
    fun getBestMatchFromCommandInput(commandInput: String): ConsoleInterchangeBranch? {
        return getBestMatchFromPath(Address.address(commandInput.replace(" ", "/")))
    }

    /**
     * This function returns the best matching branch of the [address] input and the
     * remaining, not used parameters.
     * @param commandInput The path to match
     * @return The best matching branch + remaining, not used parameters
     * @since 1.0
     * @author Fruxz
     */
    fun getBestMatchFromCommandInputWithParameters(commandInput: String): Pair<ConsoleInterchangeBranch?, String> {
        val (branch, remaining) = getBestMatchFromPathWithRemaining(Address.address(commandInput.replace(" ", "/")))
        return Pair(branch, remaining.addressString.replace("/", " ").removePrefix(" "))
    }

    /**
     * This function creates and attaches a new branch to the current branch.
     * @param identity The identity of the new branch
     * @param path The path of the new branch
     * @param branchType The type of the new branch
     * @param process The process of the new branch
     * @author Fruxz
     * @since 1.0
     */
    fun branch(
	    identity: String,
	    path: Address<TreeBranch<ConsoleInterchangeBranch, ((String) -> Unit)?, ConsoleInterchangeBranchType>> = this.address / identity,
	    branchType: ConsoleInterchangeBranchType = ConsoleInterchangeBranchType.OBJECT,
	    process: ConsoleInterchangeBranch.() -> Unit,
    ) {
        subBranches += ConsoleInterchangeBranch(
            identity = identity,
            address = path,
            subBranches = listOf(),
            branchType = branchType,
            content = null,
        ).apply(process)
    }

}
