package de.jet.library.interchange

import de.jet.library.tool.smart.positioning.Address
import de.jet.library.tool.smart.positioning.Addressed

open class InterchangeStructureBranch(
    open val branchName: String,
    override val branches: List<InterchangeStructureBranch> = emptyList(),
) : InterchangeStructureBranchable {

    fun getBranchList(path: String = branchName, subbranches: Boolean = true): List<Addressed<InterchangeStructureBranch>> {
        val branchList = mutableListOf<Addressed<InterchangeStructureBranch>>()
        val branchListIterator = branches.iterator()
        while (branchListIterator.hasNext()) {
            val branch = branchListIterator.next()

            branchList.add(Addressed(Address("$path/${branch.branchName}"), branch))

            if (subbranches) {
                branchList.addAll(branch.getBranchList("$path/${branch.branchName}/", subbranches).map {
                    Addressed(Address(path + "/" + branch.branchName + "/" + it.value.branchName), it.value)
                })
            }

        }
        return branchList
    }

}
