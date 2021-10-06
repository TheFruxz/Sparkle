package de.jet.minecraft.app.interchange

import de.jet.minecraft.app.JetCache
import de.jet.minecraft.extension.display.notification
import de.jet.minecraft.extension.system
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.command.CompletionVariable
import de.jet.minecraft.structure.command.Interchange
import de.jet.minecraft.structure.command.InterchangeResult.SUCCESS
import de.jet.minecraft.structure.command.buildCompletion
import de.jet.minecraft.structure.command.isRequired
import de.jet.minecraft.structure.command.mustMatchOutput
import de.jet.minecraft.structure.command.next
import de.jet.minecraft.tool.display.message.Transmission
import de.jet.minecraft.tool.display.message.Transmission.Level.APPLIED
import de.jet.minecraft.tool.display.message.Transmission.Level.FAIL

class PreferenceInterchange(vendor: App = system) : Interchange(vendor, "preference", requiresAuthorization = true, completion = buildCompletion {
	next("list", "reset", "info") isRequired true mustMatchOutput true
	next(CompletionVariable.PREFERENCE) isRequired true mustMatchOutput true
}) {

	override var execution = execution {

		when {
			inputLength(1) && parameters.first() == "list" -> {
				""
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
		}

		SUCCESS
	}

}