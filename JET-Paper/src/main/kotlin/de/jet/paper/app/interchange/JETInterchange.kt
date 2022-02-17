package de.jet.paper.app.interchange

import de.jet.jvm.extension.container.replace
import de.jet.paper.extension.display.BOLD
import de.jet.paper.extension.display.GOLD
import de.jet.paper.extension.display.GRAY
import de.jet.paper.extension.display.YELLOW
import de.jet.paper.extension.display.message
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.display.ui.buildClickAction
import de.jet.paper.extension.display.ui.buildInteractAction
import de.jet.paper.extension.display.ui.buildPanel
import de.jet.paper.extension.display.ui.item
import de.jet.paper.extension.lang
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeResult
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.InterchangeResult.WRONG_USAGE
import de.jet.paper.structure.command.completion.buildInterchangeStructure
import de.jet.paper.structure.command.completion.component.CompletionAsset
import de.jet.paper.structure.command.completion.component.CompletionComponent
import de.jet.paper.structure.command.completion.component.CompletionComponent.Companion
import de.jet.paper.structure.command.live.InterchangeAccess
import de.jet.paper.tool.display.item.action.ActionCooldown
import de.jet.paper.tool.display.item.action.ActionCooldownType.JET_INFO
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
			content(CompletionComponent.static("version", "website", "repository", "ping"))
		}
		branch {
			content(Companion.static("test"))
			branch {
				content(Companion.asset(CompletionAsset.TRANSMISSION_LEVEL))
			}
		}
	}
) {
	override val execution: InterchangeAccess.() -> InterchangeResult = {

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

			parameters.size == 2 && parameters.first().equals("test", true) -> {
				val levels = Transmission.Level.values()

				when (parameters.last().lowercase()) {

					"demo-panel" -> {

						buildPanel {
							set(5, Material.STONE.item.apply {
								label = "5"
								lore = """
									This is on slot 5!
								""".trimIndent()
							})
							placeInner(5, Material.GOLDEN_AXE.item.apply {
								label = "inner-5"
								lore = """
									This is on inner slot 5!
								""".trimIndent()
							})
							placeInner(6, Material.COBBLED_DEEPSLATE.item.apply {
								label = "interact"
								lore = """
									Click me!
								""".trimIndent()
								putClickAction(buildClickAction(stop = false, cooldown = ActionCooldown(20*5)) {
									whoClicked.sendMessage("clicked item: ${whoClicked.name}")
								})
								putInteractAction(buildInteractAction(stop = false, cooldown = ActionCooldown(20*3, JET_INFO)) {
									player.sendMessage("interacted item: ${player.name}")
								})
							})
						}.display(executor as Player)

					}

				}

				if (levels.any { it.name == parameters.last().uppercase() }) {

					"This is the notification!"
						.notification(valueOf(parameters.last().uppercase()), executor).display()

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