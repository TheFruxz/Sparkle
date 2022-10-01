package de.moltenKt.paper.structure.command.structured

import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.paper.structure.command.Interchange
import de.moltenKt.paper.structure.command.InterchangeAuthorizationType
import de.moltenKt.paper.structure.command.InterchangeAuthorizationType.MOLTEN
import de.moltenKt.paper.structure.command.InterchangeResult
import de.moltenKt.paper.structure.command.InterchangeUserRestriction.ONLY_CONSOLE
import de.moltenKt.paper.structure.command.completion.InterchangeStructure
import de.moltenKt.paper.structure.command.live.InterchangeAccess
import org.bukkit.command.ConsoleCommandSender
import kotlin.time.Duration

abstract class StructuredConsoleInterchange(
	label: String,
	val structure: InterchangeStructure<ConsoleCommandSender>,
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