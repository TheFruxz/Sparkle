package de.jet.library.interchange

open class InterchangeStructureBranch(
    open val branchName: String,
    override val branches: List<InterchangeStructureBranch> = emptyList(),
) : InterchangeStructureBranchable
