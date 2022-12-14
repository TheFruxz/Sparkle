package de.fruxz.sparkle.framework.infrastructure.command.structured

import de.fruxz.sparkle.framework.context.AppComposable
import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.infrastructure.command.Interchange
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeUserRestriction.ONLY_CONSOLE
import de.fruxz.sparkle.framework.infrastructure.command.completion.InterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.live.InterchangeAccess
import de.fruxz.sparkle.framework.infrastructure.command.live.InterchangeReaction
import de.fruxz.sparkle.framework.permission.Approval
import org.bukkit.command.ConsoleCommandSender
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

abstract class StructuredConsoleInterchange(
	label: String,
	val structure: InterchangeStructure<ConsoleCommandSender>,
	requiredApproval: AppComposable<Approval?>? = defaultApproval(label),
	cooldown: Duration = Duration.ZERO,
	ignoreInputValidation: Boolean = false,
	commandProperties: CommandProperties = CommandProperties(),
	wrongUsageReaction: InterchangeReaction = Companion.wrongUsageReaction,
	wrongClientReaction: InterchangeReaction = Companion.wrongClientReaction,
	wrongApprovalReaction: InterchangeReaction = Companion.wrongApprovalReaction,
	unexpectedIssueReaction: InterchangeReaction = Companion.unexpectedIssueReaction,
	cooldownReaction: InterchangeReaction = Companion.cooldownReaction,
	executionContext: AppComposable<CoroutineContext> = AppComposable { it.asyncDispatcher },
) : Interchange(
	label = label,
	requiredApproval = requiredApproval,
	requiredClient = ONLY_CONSOLE,
	completion = structure,
	cooldown = cooldown,
	ignoreInputValidation = ignoreInputValidation,
	commandProperties = commandProperties,
	wrongUsageReaction = wrongUsageReaction,
	wrongClientReaction = wrongClientReaction,
	wrongApprovalReaction = wrongApprovalReaction,
	unexpectedIssueReaction = unexpectedIssueReaction,
	cooldownReaction = cooldownReaction,
	executionContext = executionContext,
) {

	final override val execution: suspend InterchangeAccess<out InterchangeExecutor>.() -> InterchangeResult = {
		structure.performExecution(this)
	}

}