package de.fruxz.sparkle.framework.util.permission

import de.fruxz.ascend.tool.smart.identification.Identifiable
import de.fruxz.sparkle.framework.infrastructure.app.App
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.permissions.Permissible

/**
 * This class represents an approval for an action/access.
 * This is also known as "permissions".
 * @param id the permission, e.g. "sparkle.paper.*"
 * @author Fruxz
 * @since 1.0
 */
@Serializable
@SerialName("permissionApproval")
data class Approval(
	@SerialName("permission") private val id: String
) : Identifiable<Approval> {

	override var identity: String = id
		private set

	/**
	 * This function checks, if the given [permissible] has the approval.
	 * @param permissible the permissible to check
	 * @return true, if the given [permissible] has the approval, otherwise false
	 * @author Fruxz
	 * @since 1.0
	 */
	fun hasApproval(permissible: Permissible) =
		permissible.hasPermission(identity)

	/**
	 * This operator function adds a sub-permission to the current permission.
	 * Like div("use") = "sparkle.paper" -> "sparkle.paper.use"
	 * @param value the sub-permission, that should be added
	 * @author Fruxz
	 * @since 1.0
	 */
	operator fun div(value: Any?) {
		identity += ".$value"
	}

	companion object {

		/**
		 * This function creates a new approval for the given [vendor]
		 * and is based on the [subPermission].
		 * It is structure like this: ***<vendor-identity>.<sub-permission>***
		 * @param vendor the app, which provides the permission
		 * @param subPermission the sub-permission, which should be added
		 * @return the approval
		 * @author Fruxz
		 * @since 1.0
		 */
		@JvmStatic
		fun fromApp(vendor: Identifiable<App>, subPermission: String) =
			Approval("${vendor.identity}.$subPermission")

	}

}

/**
 * This function checks, if this [Permissible] has the
 * given [approval].
 * @param approval the approval, which should be checked
 * @return true, if the given [approval] is granted, otherwise false
 * @see Approval.hasApproval
 * @author Fruxz
 * @since 1.0
 */
fun Permissible.hasApproval(approval: Approval) =
	approval.hasApproval(this)

/**
 * This function checks, if this [Permissible] has the
 * given [approval].
 * @param approval the approval, which should be checked
 * @return true, if the given [approval] is granted, otherwise false
 * @see Approval.hasApproval
 * @author Fruxz
 * @since 1.0
 */
@Deprecated("Use hasApproval(Approval) instead", ReplaceWith("hasApproval(approval)"))
fun Permissible.hasPermission(approval: Approval) =
	hasApproval(approval)