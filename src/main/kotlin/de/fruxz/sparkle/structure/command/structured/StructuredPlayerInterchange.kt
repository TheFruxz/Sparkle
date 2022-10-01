package de.fruxz.sparkle.structure.command.structured

import de.fruxz.sparkle.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.structure.command.Interchange
import de.fruxz.sparkle.structure.command.InterchangeAuthorizationType
import de.fruxz.sparkle.structure.command.InterchangeAuthorizationType.MOLTEN
import de.fruxz.sparkle.structure.command.InterchangeResult
import de.fruxz.sparkle.structure.command.InterchangeUserRestriction.ONLY_PLAYERS
import de.fruxz.sparkle.structure.command.completion.InterchangeStructure
import de.fruxz.sparkle.structure.command.live.InterchangeAccess
import org.bukkit.entity.Player
import kotlin.time.Duration

abstract class StructuredPlayerInterchange(
	label: String,
	val structure: InterchangeStructure<Player>,
	aliases: Set<String> = emptySet(),
	protectedAccess: Boolean = true,
	accessProtectionType: InterchangeAuthorizationType = MOLTEN,
	hiddenFromRecommendation: Boolean = false,
	ignoreInputValidation: Boolean = false,
	cooldown: Duration = Duration.ZERO,
) : Interchange(
	label,
	aliases,
	protectedAccess,
	ONLY_PLAYERS,
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