package de.jet.library.tool.permission

import de.jet.library.structure.app.App
import de.jet.library.tool.smart.Identifiable
import kotlinx.serialization.Serializable
import org.bukkit.permissions.Permissible

@Serializable
data class Approval(
	private val id: String
) : Identifiable<Approval> {

	override var identity: String = id
		private set

	fun hasApproval(permissible: Permissible) =
		permissible.hasPermission(identity)

	operator fun div(value: Any?) {
		identity += ".$value"
	}

	companion object {

		fun fromApp(vendor: Identifiable<App>, subPermission: String) =
			Approval("${vendor.identity}.$subPermission")

	}

}