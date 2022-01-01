package de.jet.jvm.tree

import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.jvm.tool.smart.identification.UUID

open class TreeBranchType(override val identity: String = UUID.randomString()) : Identifiable<TreeBranchType> {

    companion object {

        val OBJECT = TreeBranchType("OBJECT")
        val DIRECTORY = TreeBranchType("DIRECTORY")

    }

}