package de.fruxz.sparkle.server.interchange

import de.fruxz.sparkle.framework.extension.visual.message
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.infrastructure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.structured.StructuredInterchange
import de.fruxz.sparkle.framework.visual.message.Transmission.Level.GENERAL
import de.fruxz.stacked.extension.style
import de.fruxz.stacked.hover
import de.fruxz.stacked.plus
import de.fruxz.stacked.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration.BOLD
import net.kyori.adventure.text.format.TextDecoration.ITALIC

internal class SparkleInterchange : StructuredInterchange("sparkle", protectedAccess = false, aliases = setOf("framework"), structure = buildInterchangeStructure {

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

		}.notification(GENERAL, executor).display()

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
			}.notification(GENERAL, executor).display()

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
				.notification(GENERAL, executor)
				.display()

		}

	}

	branch {

		addContent("ping")

		concludedExecution {

			Component.text("PONG!")
				.style(NamedTextColor.GOLD, ITALIC, BOLD)
				.notification(GENERAL, executor).display()

		}

	}

})