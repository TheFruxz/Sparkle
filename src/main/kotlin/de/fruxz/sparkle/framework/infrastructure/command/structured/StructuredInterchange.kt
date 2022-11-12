package de.fruxz.sparkle.framework.infrastructure.command.structured

import de.fruxz.sparkle.framework.context.AppComposable
import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.infrastructure.command.Interchange
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeResult
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeUserRestriction
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeUserRestriction.NOT_RESTRICTED
import de.fruxz.sparkle.framework.infrastructure.command.completion.InterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.live.InterchangeAccess
import de.fruxz.sparkle.framework.infrastructure.command.live.InterchangeReaction
import de.fruxz.sparkle.framework.permission.Approval
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

abstract class StructuredInterchange(
	label: String,
	val structure: InterchangeStructure<InterchangeExecutor>,
	requiredApproval: AppComposable<Approval?>? = defaultApproval(label),
	requiredClient: InterchangeUserRestriction = NOT_RESTRICTED,
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
	requiredClient = requiredClient,
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