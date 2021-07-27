package de.jet.library.extension.paper

import de.jet.library.tool.permission.Approval
import org.bukkit.entity.Player

fun Player.hasApproval(approval: Approval) =
	approval.hasApproval(this)