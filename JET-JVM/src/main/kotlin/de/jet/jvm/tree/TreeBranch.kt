package de.jet.jvm.tree

import de.jet.jvm.extension.forceCast
import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.jvm.tool.smart.positioning.Address
import de.jet.jvm.tool.smart.positioning.Pathed

open class TreeBranch<SUBBRANCH : TreeBranch<SUBBRANCH, CONTENT, BRANCH_TYPE>, CONTENT, BRANCH_TYPE : TreeBranchType>(
    override val identity: String,
    override val path: Address<TreeBranch<SUBBRANCH, CONTENT, BRANCH_TYPE>>,
    val branchType: TreeBranchType,
    private val subBranches: List<SUBBRANCH>,
    val content: CONTENT,
) : Identifiable<TreeBranch<SUBBRANCH, CONTENT, BRANCH_TYPE>>, Pathed<TreeBranch<SUBBRANCH, CONTENT, BRANCH_TYPE>> {

    fun directSubBranches() = subBranches.distinctBy { it.path }

    fun flatSubBranches(): List<SUBBRANCH> {
        return subBranches.flatMap { it.flatSubBranches() }.distinctBy { it.path }
    }

    fun allKnownBranches(): List<SUBBRANCH> {
        return (subBranches.flatMap { it.allKnownBranches() } + this.forceCast<SUBBRANCH>()).distinctBy { it.path }
    }

    fun getBestMatchFromPath(path: Address<TreeBranch<SUBBRANCH, CONTENT, BRANCH_TYPE>>): SUBBRANCH? {
        return allKnownBranches()
            .filter { path.addressString.startsWith(it.path.addressString) }
            .maxByOrNull { it.addressString.length }
    }

    fun getBestMatchFromPathWithRemaining(path: Address<TreeBranch<SUBBRANCH, CONTENT, BRANCH_TYPE>>): Pair<SUBBRANCH?, Address<TreeBranch<SUBBRANCH, CONTENT, BRANCH_TYPE>>> {
        val bestMatch = getBestMatchFromPath(path)
        return bestMatch to Address.address(path.addressString.removePrefix(bestMatch?.addressString ?: ""))
    }

}