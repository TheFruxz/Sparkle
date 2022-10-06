package de.fruxz.sparkle.framework.infrastructure.command.structured

import de.fruxz.sparkle.framework.infrastructure.command.Interchange
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeUserRestriction.ONLY_PLAYERS
import de.fruxz.sparkle.framework.infrastructure.command.completion.InterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.live.InterchangeAccess
import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import org.bukkit.entity.Player
import kotlin.time.Duration

abstract class StructuredPlayerInterchange(
	label: String,
	val structure: InterchangeStructure<Player>,
	aliases: Set<String> = emptySet(),
	protectedAccess: Boolean = true,
	ignoreInputValidation: Boolean = false,
	cooldown: Duration = Duration.ZERO,
) : Interchange(
	label = label,
	aliases = aliases,
	requiresApproval = protectedAccess,
	requiredClient = ONLY_PLAYERS,
	completion = structure,
	ignoreInputValidation = ignoreInputValidation,
	cooldown = cooldown,
) {

	final override val execution: suspend InterchangeAccess<out InterchangeExecutor>.() -> InterchangeResult = {
		structure.performExecution(this)
	}

}