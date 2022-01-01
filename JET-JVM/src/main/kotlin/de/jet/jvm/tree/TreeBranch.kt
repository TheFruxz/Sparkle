package de.jet.jvm.tree

import de.jet.jvm.extension.forceCast
import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.jvm.tool.smart.positioning.Address
import de.jet.jvm.tool.smart.positioning.Pathed

open class TreeBranch<SUBBRANCH : TreeBranch<SUBBRANCH, CONTENT>, CONTENT>(
    override val identity: String,
    override val path: Address<TreeBranch<SUBBRANCH, CONTENT>>,
    private val subBranches: List<SUBBRANCH>,
    val content: CONTENT,
) : Identifiable<TreeBranch<SUBBRANCH, CONTENT>>, Pathed<TreeBranch<SUBBRANCH, CONTENT>> {

    fun directSubBranches() = subBranches.distinctBy { it.path }

    fun flatSubBranches(): List<SUBBRANCH> {
        return subBranches.flatMap { it.flatSubBranches() }.distinctBy { it.path }
    }

    fun allKnownBranches(): List<SUBBRANCH> {
        return (subBranches.flatMap { it.allKnownBranches() } + this.forceCast<SUBBRANCH>()).distinctBy { it.path }
    }

    fun getBestMatchFromPath(path: Address<TreeBranch<SUBBRANCH, CONTENT>>): SUBBRANCH? {
        return allKnownBranches()
            .filter { path.addressString.startsWith(it.path.addressString) }
            .maxByOrNull { it.addressString.length }
    }

    fun getBestMatchFromPathWithRemaining(path: Address<TreeBranch<SUBBRANCH, CONTENT>>): Pair<SUBBRANCH?, Address<TreeBranch<SUBBRANCH, CONTENT>>> {
        val bestMatch = getBestMatchFromPath(path)
        return bestMatch to Address.address(path.addressString.removePrefix(bestMatch?.addressString ?: ""))
    }

}