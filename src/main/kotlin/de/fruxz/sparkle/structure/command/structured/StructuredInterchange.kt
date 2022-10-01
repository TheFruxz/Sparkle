package de.fruxz.sparkle.structure.command.structured

import de.fruxz.sparkle.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.structure.command.Interchange
import de.fruxz.sparkle.structure.command.InterchangeAuthorizationType
import de.fruxz.sparkle.structure.command.InterchangeAuthorizationType.MOLTEN
import de.fruxz.sparkle.structure.command.InterchangeResult
import de.fruxz.sparkle.structure.command.InterchangeUserRestriction
import de.fruxz.sparkle.structure.command.InterchangeUserRestriction.NOT_RESTRICTED
import de.fruxz.sparkle.structure.command.completion.InterchangeStructure
import de.fruxz.sparkle.structure.command.live.InterchangeAccess
import kotlin.time.Duration

abstract class StructuredInterchange(
	label: String,
	val structure: InterchangeStructure<InterchangeExecutor>,
	aliases: Set<String> = emptySet(),
	protectedAccess: Boolean = true,
	userRestriction: InterchangeUserRestriction = NOT_RESTRICTED,
	accessProtectionType: InterchangeAuthorizationType = MOLTEN,
	hiddenFromRecommendation: Boolean = false,
	ignoreInputValidation: Boolean = false,
	cooldown: Duration = Duration.ZERO,
) : Interchange(
	label,
	aliases,
	protectedAccess,
	userRestriction,
	accessProtectionType,
	hiddenFromRecommendation,
	structure,
	ignoreInputValidation,
	cooldown = cooldown,
) {

	final override val execution: suspend InterchangeAccess<out InterchangeExecutor>.() -> InterchangeResult = {
		structure.performExecution(this)
	}

}