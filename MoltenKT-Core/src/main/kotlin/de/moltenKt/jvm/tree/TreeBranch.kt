package de.moltenKt.jvm.tree

import de.moltenKt.jvm.extension.forceCast
import de.moltenKt.jvm.tool.smart.identification.Identifiable
import de.moltenKt.jvm.tool.smart.positioning.Address
import de.moltenKt.jvm.tool.smart.positioning.Pathed

/**
 * This open class represents a branch of a tree, containing and based on another branches.
 * @param SUBBRANCH the type of the subbranches, have to be the same as this origin branch type!
 * @param CONTENT the content, contained & stored in each branch
 * @param BRANCH_TYPE the type of the branch (useful for directories, etc.)
 * @param identity the name of the branch
 * @param address the path of the branch (have to contain the whole path, like 'this/is/a/path')
 * @param branchType the type of the branch
 * @param subBranches the subbranches of the branch, that is a list of [SUBBRANCH]es
 * @param content the content of the branch, which is a [CONTENT]
 * @author Fruxz
 * @since 1.0
 */
open class TreeBranch<SUBBRANCH : TreeBranch<SUBBRANCH, CONTENT, BRANCH_TYPE>, CONTENT, BRANCH_TYPE : TreeBranchType>(
    override var identity: String,
    override var address: Address<SUBBRANCH> = Address.address(identity),
    open var branchType: BRANCH_TYPE,
    open var subBranches: List<SUBBRANCH>,
    open var content: CONTENT,
) : Identifiable<SUBBRANCH>, Pathed<SUBBRANCH> {

    /**
     * This function replaces the current [content] of the branch with the given new [content].
     * @param content the new content of the branch
     * @return the new state of the branch
     * @author Fruxz
     * @since 1.0
     */
    fun content(content: CONTENT) = apply { this.content = content }

    /**
     * This function returns every subbranch, and its subranches, and so on in a single list.
     * @return the list of all subbranches, and their subbranches, and so on
     * @author Fruxz
     * @since 1.0
     */
    fun flatSubBranches(): List<SUBBRANCH> {
        return subBranches.flatMap { it.flatSubBranches() }
    }

    /**
     * This function returns every subbranch, and its subranches, and so on in a single list, additionally with this branch.
     * @return the list of all subbranches, and their subbranches, and so on, additionally with this branch
     * @author Fruxz
     * @since 1.0
     */
    fun allKnownBranches(): List<SUBBRANCH> {
        return ((subBranches.flatMap { it.allKnownBranches() } + this.forceCast<SUBBRANCH>()).distinctBy { it.address })
    }

    /**
     * This function returns the [SUBBRANCH], which fits the most to the given [identity], or null,
     * if there is no branch with the given [identity], that fits correctly at all.
     * @param path the path of the branch searched with the parameters used on top.
     * @author Fruxz
     * @since 1.0
     */
    fun getBestMatchFromPath(path: Address<TreeBranch<SUBBRANCH, CONTENT, BRANCH_TYPE>>): SUBBRANCH? {
        return allKnownBranches()
            .filter { path.addressString.startsWith(it.address.addressString) }
            .maxByOrNull { obj -> obj.addressObject.let { address -> return@let address.addressString.split(address.divider) }.let { split -> split.size + split.last().length } }
    }

    /**
     * This function returns the [SUBBRANCH], which fits the most to the given [identity], or null,
     * if there is no branch with the given [identity], that fits correctly at all.
     * Additionally, this function returns the not used / remaining parts of the given [path].
     * @param path the path of the branch searched with the parameters used on top.
     * @author Fruxz
     * @since 1.0
     */
    fun getBestMatchFromPathWithRemaining(path: Address<TreeBranch<SUBBRANCH, CONTENT, BRANCH_TYPE>>): Pair<SUBBRANCH?, Address<TreeBranch<SUBBRANCH, CONTENT, BRANCH_TYPE>>> {
        val bestMatch = getBestMatchFromPath(path)
        return bestMatch to Address.address(path.addressString.removePrefix(bestMatch?.addressString ?: ""))
    }

}

fun <SUBBRANCH : TREE, CONTENT, BRANCH_TYPE : TreeBranchType, TREE : TreeBranch<SUBBRANCH, CONTENT, BRANCH_TYPE>> TREE.searchBranchByAddress(address: Address<TREE>): SUBBRANCH? =
    flatSubBranches().firstOrNull { it.address == address }