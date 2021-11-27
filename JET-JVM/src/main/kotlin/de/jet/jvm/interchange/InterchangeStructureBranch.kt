package de.jet.jvm.interchange

import de.jet.jvm.extension.forceCast

open class InterchangeStructureBranch(
    open val branchName: String,
    open val path: String,
    override val branches: List<InterchangeStructureBranch> = emptyList(),
) : InterchangeStructureBranchable {

    fun <T : InterchangeStructureBranch> getStructureBranches(): List<T> {
        val branchList = mutableListOf<T>()
        branches.forEach { branch ->
            branchList.add(branch.forceCast())
        }
        return branchList
    }

}
