package de.jet.paper.app.interchange

import de.jet.jvm.annotation.NotWorking
import de.jet.jvm.extension.container.second
import de.jet.paper.app.JetCache
import de.jet.paper.extension.display.notification
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.completion.buildInterchangeStructure
import de.jet.paper.structure.command.completion.component.CompletionComponent
import de.jet.paper.structure.command.completion.component.CompletionComponent.Companion
import de.jet.paper.structure.command.execution
import de.jet.paper.tool.display.message.Transmission.Level.APPLIED
import de.jet.paper.tool.display.message.Transmission.Level.FAIL

@NotWorking
class PreferenceInterchange : Interchange(
	label = "preference",
	protectedAccess = true,
	completion = buildInterchangeStructure {
		branch {
			addContent("list")
		}
		branch {
			addContent("reset", "info", "set")
			branch {
				TODO()
			}
		}
	}

) {

	override var execution = execution {

		when {
			inputLength(1) && parameters.first() == "list" -> {
				TODO()
			}
			inputLength(2) -> {
				val preference = JetCache.registeredPreferences.toList().firstOrNull { it.first.identity == parameters.first() }?.second

				if (preference != null) {
					when (parameters.first()) {
						"reset" -> {

							preference.reset()

							"Successfully reset the preference [preference] to its default value!"
								.notification(APPLIED, executor).display()

						}
						"info" -> {

						}
					}
				} else
					"No preference with the name [preference] currently registered!"
						.notification(FAIL, executor).display()
			}
			inputLength >= 3 && parameters.second == "set" -> {
				val preference = JetCache.registeredPreferences.toList().firstOrNull { it.first.identity == parameters.first() }?.second

				if (preference != null) {
					preference.insertFromString(parameters.drop(2).joinToString(" "))
				} else
					"No preference with the name [preference] currently registered!"
						.notification(FAIL, executor).display()

			}
		}

		SUCCESS
	}

}