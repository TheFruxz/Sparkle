package de.jet.minecraft.app.interchange

import de.jet.library.extension.collection.second
import de.jet.minecraft.app.JetCache
import de.jet.minecraft.extension.display.notification
import de.jet.minecraft.extension.system
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.command.*
import de.jet.minecraft.structure.command.InterchangeResult.SUCCESS
import de.jet.minecraft.tool.display.message.Transmission.Level.APPLIED
import de.jet.minecraft.tool.display.message.Transmission.Level.FAIL

class PreferenceInterchange(vendor: App = system) : Interchange(vendor, "preference", requiresAuthorization = true, completion = buildCompletion {
	next("list", "reset", "info", "set") isRequired true mustMatchOutput true
	next(CompletionVariable.PREFERENCE) isRequired false mustMatchOutput true
	plus("Input") label "<Component>" isRequired false mustMatchOutput false infinite true
}) {

	override var execution = execution {

		when {
			inputLength(1) && parameters.first() == "list" -> {
				""
			}
			inputLength(2) && checkParameter[1] -> {
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