package de.jet.paper.app.interchange

import de.jet.jvm.extension.container.replace
import de.jet.paper.extension.display.BOLD
import de.jet.paper.extension.display.GOLD
import de.jet.paper.extension.display.GRAY
import de.jet.paper.extension.display.YELLOW
import de.jet.paper.extension.display.message
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.display.ui.buildPanel
import de.jet.paper.extension.display.ui.item
import de.jet.paper.extension.lang
import de.jet.paper.extension.paper.player
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.InterchangeResult.WRONG_USAGE
import de.jet.paper.structure.command.completion.buildInterchangeStructure
import de.jet.paper.structure.command.completion.component.CompletionAsset
import de.jet.paper.structure.command.execution
import de.jet.paper.tool.display.message.Transmission
import de.jet.paper.tool.display.message.Transmission.Level.GENERAL
import de.jet.paper.tool.display.message.Transmission.Level.valueOf
import org.bukkit.Material
import org.bukkit.entity.Player

class JETInterchange : Interchange(
	label = "jet",
	protectedAccess = false,
	completion = buildInterchangeStructure {
		branch {
			addContent("version", "website", "repository", "ping")
		}
	}
) {
	override val execution = execution {

		var success = true

		when {

			parameters.isEmpty() -> {

				"${GOLD}JET ${GRAY}was developed by$YELLOW TheFruxz$GRAY, and other contributors of the repository:$GOLD$BOLD ${vendor.description.website}"
					.notification(GENERAL, executor).display()

				"${YELLOW}JET is running & developed with Kotlin (the programming Language) from JetBrains. Check out their work https://jetbrains.com or https://kotlinlang.org"
					.message(executor).display()

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

					else -> {
						success = false
					}

				}

			}

			else -> {

				success = false

			}

		}

		if (success)
			SUCCESS
		else
			WRONG_USAGE

	}
}