package de.jet.library.tool.permission

import de.jet.library.structure.app.App
import de.jet.library.structure.smart.Identifiable
import kotlinx.serialization.Serializable
import org.bukkit.entity.Player

@Serializable
data class Approval(
	override val id: String
) : Identifiable<Approval> {

	fun hasApproval(player: Player) =
		player.hasPermission(id)

	companion object {

		fun fromApp(vendor: Identifiable<App>, subPermission: String) =
			Approval("${vendor.id}.$subPermission")

	}

}