package de.moltenKt.paper.structure.command.structured

import de.moltenKt.core.extension.forceCast
import de.moltenKt.core.extension.math.maxTo
import de.moltenKt.paper.extension.interchange.InterchangeExecutor
import de.moltenKt.paper.structure.command.Interchange
import de.moltenKt.paper.structure.command.InterchangeAuthorizationType
import de.moltenKt.paper.structure.command.InterchangeAuthorizationType.MOLTEN
import de.moltenKt.paper.structure.command.InterchangeResult
import de.moltenKt.paper.structure.command.InterchangeResult.WRONG_USAGE
import de.moltenKt.paper.structure.command.InterchangeUserRestriction.ONLY_CONSOLE
import de.moltenKt.paper.structure.command.completion.InterchangeStructure
import de.moltenKt.paper.structure.command.live.InterchangeAccess
import org.bukkit.command.ConsoleCommandSender
import kotlin.time.Duration
import kotlin.time.Duration.Companion

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
		val trace = structure.trace(parameters, executor)

		when (trace.waysMatching.size.maxTo(2)) {
			0, 2 -> WRONG_USAGE
			else -> {
				val extrapolatedTrace = trace.waysMatching.first()
				val extrapolatedBranch = extrapolatedTrace.branch

				extrapolatedBranch.onExecution?.invoke(this.copy(additionalParameters = parameters.drop(extrapolatedTrace.tracingDepth + 1)).forceCast()) ?: WRONG_USAGE

			}
		}

	}

}