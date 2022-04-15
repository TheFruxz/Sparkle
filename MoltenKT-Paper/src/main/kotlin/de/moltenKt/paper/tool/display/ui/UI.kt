package de.moltenKt.paper.tool.display.ui

import de.moltenKt.jvm.tool.smart.identification.Identifiable
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player

/**
 * This interface represents a displayable user interface, which can
 * be displayed to a player and identified by a unique id.
 * @author Fruxz
 * @since 1.0
 */
interface UI<T : UI<T>> : Identifiable<T> {

	/**
	 * This function displays the user interface to the given
	 * [receiver] [Player], specified by the parameter.
	 * @param receiver The [Player] to display the user interface to.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun display(receiver: Player)

	/**
	 * This function displays the user interface to the given
	 * [humanEntity] [HumanEntity], specified by the parameter.
	 * @param humanEntity The [HumanEntity] to display the user interface to.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun display(humanEntity: HumanEntity)

	/**
	 * This function displays the user interface to the given
	 * [receiver] [Player], specified by the parameter.
	 * @param receiver The [Player] to display the user interface to.
	 * @param specificParameters Additional parameters, used to generate a player-specific user interface.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun display(receiver: Player, specificParameters: Map<String, Any>)

	/**
	 * This function displays the user interface to the given
	 * [humanEntity] [HumanEntity], specified by the parameter.
	 * @param humanEntity The [HumanEntity] to display the user interface to.
	 * @param specificParameters Additional parameters, used to generate a player-specific user interface.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun display(humanEntity: HumanEntity, specificParameters: Map<String, Any>)

}