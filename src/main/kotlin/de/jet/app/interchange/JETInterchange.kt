package de.jet.app.interchange

import de.jet.library.extension.collection.replace
import de.jet.library.extension.display.BOLD
import de.jet.library.extension.display.GOLD
import de.jet.library.extension.display.GRAY
import de.jet.library.extension.display.YELLOW
import de.jet.library.extension.display.notification
import de.jet.library.extension.display.ui.buildClickAction
import de.jet.library.extension.display.ui.buildInteractAction
import de.jet.library.extension.display.ui.buildPanel
import de.jet.library.extension.display.ui.item
import de.jet.library.extension.lang
import de.jet.library.structure.app.App
import de.jet.library.structure.command.Interchange
import de.jet.library.structure.command.InterchangeResult
import de.jet.library.structure.command.InterchangeResult.SUCCESS
import de.jet.library.structure.command.InterchangeResult.WRONG_USAGE
import de.jet.library.structure.command.buildCompletion
import de.jet.library.structure.command.isRequired
import de.jet.library.structure.command.live.InterchangeAccess
import de.jet.library.structure.command.next
import de.jet.library.structure.command.plus
import de.jet.library.tool.display.message.Transmission.Level.GENERAL
import org.bukkit.Material
import org.bukkit.entity.Player

class JETInterchange(vendor: App) : Interchange(
	vendor = vendor,
	label = "jet",
	requiresAuthorization = false,
	completion = buildCompletion {
		next("version") + "website" + "repository" + "ping" + "test" isRequired false
		next("demo-panel")
	}
) {
	override val execution: InterchangeAccess.() -> InterchangeResult = {

		when  {

			parameters.isEmpty() -> {

				"${GOLD}JET ${GRAY}was developed by$YELLOW TheFruxz$GRAY,$YELLOW JanLuca$GRAY, and other contributors of the repository:$GOLD$BOLD ${vendor.description.website}"
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

					else -> WRONG_USAGE

				}

			}

			parameters.size == 2 && parameters.first().equals("test", true) -> {

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
								putClickAction(buildClickAction {
									whoClicked.sendMessage("clicked item: ${whoClicked.name}")
								})
								putInteractAction(buildInteractAction {
									player.sendMessage("interacted item: ${player.name}")
								})
							})
						}.display(executor as Player)

					}

				}

			}

			else -> WRONG_USAGE

		}

		SUCCESS

	}
}