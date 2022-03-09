package de.jet.paper.tool.display.ui

import de.jet.jvm.tool.smart.identification.Identifiable
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player

interface UI<T : UI<T>> : Identifiable<T> {

	fun display(receiver: Player)

	fun display(humanEntity: HumanEntity)

	fun display(receiver: Player, specificParameters: Map<String, Any>)

	fun display(humanEntity: HumanEntity, specificParameters: Map<String, Any>)

}