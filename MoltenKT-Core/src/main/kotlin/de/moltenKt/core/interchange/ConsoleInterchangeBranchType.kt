package de.moltenKt.core.interchange

import de.moltenKt.core.tool.smart.identification.UUID
import de.moltenKt.core.tree.TreeBranchType

/**
 * This class defines the branch types of the console interchanges.
 * @author Fruxz
 * @since 1.0
 */
class ConsoleInterchangeBranchType(identity: String = UUID.randomString()) : TreeBranchType(identity) {

	companion object {

		/**
		 * The general branch type.
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		val OBJECT = ConsoleInterchangeBranchType("OBJECT")

		/**
		 * The branch type of empty interchanges, that only define a bridge to other interchanges.
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		val BRIDGE = ConsoleInterchangeBranchType("BRIDGE")

	}

}