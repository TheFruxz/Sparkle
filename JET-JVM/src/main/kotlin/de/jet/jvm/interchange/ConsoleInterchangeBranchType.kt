package de.jet.jvm.interchange

import de.jet.jvm.tool.smart.identification.UUID
import de.jet.jvm.tree.TreeBranchType

class ConsoleInterchangeBranchType(identity: String = UUID.randomString()) : TreeBranchType(identity) {

	companion object {

		val OBJECT = ConsoleInterchangeBranchType("OBJECT")
		val BRIDGE = ConsoleInterchangeBranchType("BRIDGE")

	}

}