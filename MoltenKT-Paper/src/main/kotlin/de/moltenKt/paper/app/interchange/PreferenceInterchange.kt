package de.moltenKt.paper.app.interchange

import de.moltenKt.jvm.extension.container.second
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.structure.command.Interchange
import de.moltenKt.paper.structure.command.InterchangeResult.SUCCESS
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.execution
import de.moltenKt.paper.tool.display.message.Transmission.Level.APPLIED
import de.moltenKt.paper.tool.display.message.Transmission.Level.FAIL

@de.moltenKt.jvm.annotation.NotWorking
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
				val preference = MoltenCache.registeredPreferences.toList().firstOrNull { it.first.identity == parameters.first() }?.second

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
				val preference = MoltenCache.registeredPreferences.toList().firstOrNull { it.first.identity == parameters.first() }?.second

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