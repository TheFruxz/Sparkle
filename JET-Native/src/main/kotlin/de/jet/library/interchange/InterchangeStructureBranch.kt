package de.jet.library.interchange

import de.jet.library.tool.smart.positioning.Address
import de.jet.library.tool.smart.positioning.Addressed

open class InterchangeStructureBranch(
    open val branchName: String,
    override val branches: List<InterchangeStructureBranch> = emptyList(),
) : InterchangeStructureBranchable {

    fun <T : InterchangeStructureBranch> getBranchList(path: String = branchName, subbranches: Boolean = true): List<Addressed<T>> {
        val branchList = mutableListOf<Addressed<T>>()
        branches.forEach { branch ->
            branchList.add(Addressed(Address<T>("$path/${branch.branchName}"), branch as T))

            if (subbranches) {
                branchList.addAll(branch.getBranchList<T>("$path/${branch.branchName}/", subbranches).map {
                    Addressed(Address(path + "/" + branch.branchName + "/" + it.value.branchName), it.value)
                })
            }
        }
        return branchList
    }

}
