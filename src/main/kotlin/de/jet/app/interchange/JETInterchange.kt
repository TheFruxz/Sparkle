package de.jet.app.interchange

import de.jet.library.extension.collection.mapToString
import de.jet.library.extension.collection.replace
import de.jet.library.extension.display.BOLD
import de.jet.library.extension.display.GOLD
import de.jet.library.extension.display.GRAY
import de.jet.library.extension.display.LIGHT_GRAY
import de.jet.library.extension.display.YELLOW
import de.jet.library.extension.display.notification
import de.jet.library.extension.lang
import de.jet.library.structure.app.App
import de.jet.library.structure.command.CompletionVariable
import de.jet.library.structure.command.Interchange
import de.jet.library.structure.command.InterchangeResult
import de.jet.library.structure.command.InterchangeResult.SUCCESS
import de.jet.library.structure.command.buildCompletion
import de.jet.library.structure.command.isRequired
import de.jet.library.structure.command.live.InterchangeAccess
import de.jet.library.structure.command.next
import de.jet.library.structure.command.plus
import de.jet.library.tool.display.message.Transmission.Level.GENERAL

class JETInterchange(vendor: App) : Interchange(
	vendor = vendor,
	label = "jet",
	requiresAuthorization = false,
	completion = buildCompletion {
		next("version") + "website" + "repository" + "ping" isRequired false
	}
) {
	override val execution: InterchangeAccess.() -> InterchangeResult = {

		when  {
			parameters.isEmpty() -> {

				"${GOLD}JET ${LIGHT_GRAY}was developed by$YELLOW TheFruxz$LIGHT_GRAY,$YELLOW JanLuca$LIGHT_GRAY, and other contributors of the repository:$GOLD$BOLD ${vendor.description.website}"
					.notification(GENERAL, executor).display()

			}
			parameters.size == 1 -> {

				when (parameters.first().lowercase()) {

					"version" -> {

						lang("interchange.internal.jet.version")
							.replace("[version]" to "${vendor.description.version}@${vendor.description.apiVersion}")
							.notification(GENERAL, executor).display()

					}

					"website", "repository" -> {

						lang("interchange.internal.jet.host")
							.replace("[website]" to vendor.description.website)
							.notification(GENERAL, executor).display()

					}

					"ping" -> {

						lang("interchange.internal.jet.ping")
							.notification(GENERAL, executor).display()

					}

				}

			}
		}

		SUCCESS

	}
}