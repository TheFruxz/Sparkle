package de.moltenKt.jvm.tree

import de.moltenKt.jvm.tool.smart.identification.Identifiable
import de.moltenKt.jvm.tool.smart.identification.UUID

open class TreeBranchType(override val identity: String = UUID.randomString()) : Identifiable<TreeBranchType> {

    companion object {

        @JvmStatic
        val OBJECT = TreeBranchType("OBJECT")

        @JvmStatic
        val DIRECTORY = TreeBranchType("DIRECTORY")

    }

}