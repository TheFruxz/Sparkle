package de.fruxz.sparkle.framework.infrastructure.command.structured

import de.fruxz.sparkle.framework.infrastructure.command.Interchange
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeUserRestriction
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeUserRestriction.NOT_RESTRICTED
import de.fruxz.sparkle.framework.infrastructure.command.completion.InterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.live.InterchangeAccess
import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import kotlin.time.Duration

abstract class StructuredInterchange(
	label: String,
	val structure: InterchangeStructure<InterchangeExecutor>,
	aliases: Set<String> = emptySet(),
	protectedAccess: Boolean = true,
	userRestriction: InterchangeUserRestriction = NOT_RESTRICTED,
	ignoreInputValidation: Boolean = false,
	cooldown: Duration = Duration.ZERO,
) : Interchange(
	label = label,
	aliases = aliases,
	requiresApproval = protectedAccess,
	requiredClient = userRestriction,
	completion = structure,
	ignoreInputValidation = ignoreInputValidation,
	cooldown = cooldown,
) {

	final override val execution: suspend InterchangeAccess<out InterchangeExecutor>.() -> InterchangeResult = {
		structure.performExecution(this)
	}

}