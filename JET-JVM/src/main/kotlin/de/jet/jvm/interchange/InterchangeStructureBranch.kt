package de.jet.jvm.interchange

import de.jet.jvm.extension.forceCast
import de.jet.jvm.extension.forceCastOrNull

/**
 * This open class defines the functions and the structure of JVM Interchange
 * branches and their behavior.
 * @param branchName is the name of the branch
 * @param path is their location inside the interchange structure
 * @param branches are the sub-branches of this branch
 * @author Fruxz
 * @since 1.0
 */
open class InterchangeStructureBranch(
    open val branchName: String,
    open val path: String,
    override val branches: List<InterchangeStructureBranch> = emptyList(),
) : InterchangeStructureBranchable {

    fun <T : InterchangeStructureBranch> getStructureBranches(): List<T> {
        val branchList = mutableListOf<T>()
        this.forceCastOrNull<T>()?.let { branchList.add(it) }
        branches.forEach { branch ->
            branchList.add(branch.forceCast())
        }
        return branchList
    }

}
