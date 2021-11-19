package de.jet.library.interchange

import de.jet.library.tool.smart.identification.Identifiable

open class InterchangeStructure(
    val name: String,
    override val branches: List<InterchangeStructureBranch> = emptyList(),
) : Identifiable<InterchangeStructure>, InterchangeStructureBranch(name) {

    override val identity = name

}
