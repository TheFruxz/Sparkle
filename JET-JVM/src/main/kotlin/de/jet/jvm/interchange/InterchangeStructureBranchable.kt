package de.jet.jvm.interchange

/**
 * This interface represents every class/object, which is branchable
 * and also inside an interchange structure located
 * @author Fruxz
 * @since 1.0
 */
@Suppress("SpellCheckingInspection")
interface InterchangeStructureBranchable{
    val branches: List<InterchangeStructureBranch>
}