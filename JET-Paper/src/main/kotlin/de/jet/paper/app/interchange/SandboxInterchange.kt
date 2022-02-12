package de.jet.paper.app.interchange

import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeResult
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.InterchangeResult.WRONG_USAGE
import de.jet.paper.structure.command.completion.buildInterchangeStructure
import de.jet.paper.structure.command.completion.component.CompletionAsset
import de.jet.paper.structure.command.completion.component.CompletionComponent
import de.jet.paper.structure.command.completion.component.CompletionComponent.Companion
import de.jet.paper.structure.command.completion.infiniteSubParameters
import de.jet.paper.structure.command.live.InterchangeAccess

class SandboxInterchange :
	Interchange(
		label = "sandbox",
		protectedAccess = true,
		completion = buildInterchangeStructure {
			branch {
				content(CompletionComponent.static("list", "dropAll", "runAll"))
			}
			branch {
				content(Companion.static("drop", "run", "info"))
				branch {
					infiniteSubParameters()
					content(Companion.asset(CompletionAsset.SANDBOX))
				}
			}
		}
	) {

	override val execution: InterchangeAccess.() -> InterchangeResult = result@{

		when {

			else -> return@result WRONG_USAGE

		}

		return@result SUCCESS

	}

}