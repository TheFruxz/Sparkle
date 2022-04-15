package de.moltenKt.paper.structure.command

import de.moltenKt.jvm.extension.math.maxTo
import de.moltenKt.paper.structure.command.InterchangeAuthorizationType.MOLTEN
import de.moltenKt.paper.structure.command.InterchangeResult.WRONG_USAGE
import de.moltenKt.paper.structure.command.InterchangeUserRestriction.NOT_RESTRICTED
import de.moltenKt.paper.structure.command.completion.InterchangeStructure
import de.moltenKt.paper.structure.command.live.InterchangeAccess

abstract class StructuredInterchange(
	label: String,
	val structure: InterchangeStructure,
	aliases: Set<String> = emptySet(),
	protectedAccess: Boolean = true,
	userRestriction: InterchangeUserRestriction = NOT_RESTRICTED,
	accessProtectionType: InterchangeAuthorizationType = MOLTEN,
	hiddenFromRecommendation: Boolean = false,
	ignoreInputValidation: Boolean = false
) : Interchange(
	label,
	aliases,
	protectedAccess,
	userRestriction,
	accessProtectionType,
	hiddenFromRecommendation,
	structure,
	ignoreInputValidation
) {

	final override val execution: suspend InterchangeAccess.() -> InterchangeResult = {
		val trace = structure.trace(parameters)

		when (trace.waysMatching.size.maxTo(2)) {
			0, 2 -> WRONG_USAGE.also { println(trace.waysMatching.size) }
			else -> {
				val extrapolatedTrace = trace.waysMatching.first()
				val extrapolatedBranch = extrapolatedTrace.branch

				extrapolatedBranch.onExecution?.invoke(this.copy(additionalParameters = parameters.drop(extrapolatedTrace.tracingDepth + 1))) ?: WRONG_USAGE

			}
		}

	}

}