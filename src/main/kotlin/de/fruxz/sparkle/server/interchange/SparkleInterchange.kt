package de.fruxz.sparkle.server.interchange

import de.fruxz.sparkle.framework.extension.asPlayerOrNull
import de.fruxz.sparkle.framework.extension.visual.message
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.infrastructure.command.Interchange.CommandProperties.Companion.aliases
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeUserRestriction.ONLY_PLAYERS
import de.fruxz.sparkle.framework.infrastructure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.structured.StructuredInterchange
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance
import dev.fruxz.stacked.extension.style
import dev.fruxz.stacked.hover
import dev.fruxz.stacked.plus
import dev.fruxz.stacked.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration.BOLD
import net.kyori.adventure.text.format.TextDecoration.ITALIC
import kotlin.time.Duration.Companion.milliseconds

internal class SparkleInterchange : StructuredInterchange(
	label = "sparkle",
	requiredApproval = null,
	commandProperties = aliases("framework"),
	structure = buildInterchangeStructure {

		concludedExecution {

			text {
				this + text("Sparkle") {
					style(NamedTextColor.GOLD, BOLD)
				} + text(" was developed by ") {
					color(NamedTextColor.GRAY)
				} + text("TheFruxz") {
					color(NamedTextColor.YELLOW)
				} + text(", and other contributors of the repository: ") {
					color(NamedTextColor.GRAY)
				} + text(vendor.description.website ?: "FEHLER") {
					style(NamedTextColor.GOLD, BOLD)
				} + Component.newline()

			}.notification(TransmissionAppearance.GENERAL, executor).display()

			text("Sparkle is running & developed with Kotlin (the programming Language) from JetBrains. Check out their work https://jetbrains.com or https://kotlinlang.org")
				.color(NamedTextColor.YELLOW)
				.message(executor).display()

		}

		branch {

			addContent("version")

			concludedExecution {

				text {
					this + text(vendor.label) {
						color(NamedTextColor.GOLD)
					}
					this + text(" is running with the version ") {
						color(NamedTextColor.GRAY)
					}
					this + text {
						this + text(vendor.description.version)
						this + text("@")
						this + text(vendor.description.apiVersion ?: "none")
						color(NamedTextColor.GRAY)
					}
				}.notification(TransmissionAppearance.GENERAL, executor).display()

			}

		}

		branch {

			addContent("website", "repository")

			concludedExecution {

				text {
					this + text("Visit the repository of ") {
						color(NamedTextColor.GRAY)
					}
					this + text(vendor.label) {
						color(NamedTextColor.GOLD)
					}
					this + text(" here: ") {
						color(NamedTextColor.GRAY)
					}
					this + text(vendor.description.website ?: "Please report this bug!") {
						color(NamedTextColor.YELLOW)
					}
				}.hover {
					text {
						this + text("CLICK") {
							style(NamedTextColor.GREEN, BOLD)
						}
						this + text(" to open the repository") {
							style(NamedTextColor.GRAY)
						}
					}
				}.clickEvent(ClickEvent.openUrl(vendor.description.website ?: "bug"))
					.notification(TransmissionAppearance.GENERAL, executor)
					.display()

			}

		}

		branch(ONLY_PLAYERS) {

			addContent("ping")

			concludedExecution {

				text("PONG!") {
					style(NamedTextColor.GOLD, ITALIC, BOLD)
					clickEvent(ClickEvent.suggestCommand("Your Ping is ${executor.asPlayerOrNull?.ping?.milliseconds}"))
				}.notification(TransmissionAppearance.GENERAL, executor).display()

			}

		}

	}
)