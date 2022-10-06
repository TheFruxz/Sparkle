package de.fruxz.sparkle.framework.infrastructure.command.structured

import de.fruxz.sparkle.framework.infrastructure.command.Interchange
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeAuthorizationType
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeAuthorizationType.SPARKLE
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeUserRestriction.ONLY_CONSOLE
import de.fruxz.sparkle.framework.infrastructure.command.completion.InterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.live.InterchangeAccess
import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import org.bukkit.command.ConsoleCommandSender
import kotlin.time.Duration

abstract class StructuredConsoleInterchange(
	label: String,
	val structure: InterchangeStructure<ConsoleCommandSender>,
	aliases: Set<String> = emptySet(),
	protectedAccess: Boolean = true,
	accessProtectionType: InterchangeAuthorizationType = SPARKLE,
	hiddenFromRecommendation: Boolean = false,
	ignoreInputValidation: Boolean = false,
	cooldown: Duration = Duration.ZERO,
) : Interchange(
	label,
	aliases,
	protectedAccess,
	ONLY_CONSOLE,
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