package de.moltenKt.core.tree

import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.core.tool.smart.identification.UUID

open class TreeBranchType(override val identity: String = UUID.randomString()) : Identifiable<TreeBranchType> {

    companion object {

        @JvmStatic
        val OBJECT = TreeBranchType("OBJECT")

        @JvmStatic
        val DIRECTORY = TreeBranchType("DIRECTORY")

    }

}