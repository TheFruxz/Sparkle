package de.jet.paper.app.interchange

import de.jet.jvm.extension.container.replace
import de.jet.jvm.extension.container.replaceVariables
import de.jet.paper.extension.display.BOLD
import de.jet.paper.extension.display.GOLD
import de.jet.paper.extension.display.GRAY
import de.jet.paper.extension.display.YELLOW
import de.jet.paper.extension.display.message
import de.jet.paper.extension.display.notification
import de.jet.paper.extension.lang
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeResult.SUCCESS
import de.jet.paper.structure.command.InterchangeResult.WRONG_USAGE
import de.jet.paper.structure.command.StructuredInterchange
import de.jet.paper.structure.command.completion.buildInterchangeStructure
import de.jet.paper.structure.command.execution
import de.jet.paper.tool.display.message.Transmission.Level.GENERAL
import de.jet.paper.tool.display.message.Transmission.Level.INFO
import de.jet.unfold.newline
import de.jet.unfold.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style.style
import net.kyori.adventure.text.format.TextDecoration

class JETInterchange : StructuredInterchange("jet", buildInterchangeStructure {

	concludedExecution {

		text {

			text("JET") {
				style(style(NamedTextColor.GOLD, TextDecoration.BOLD))
			}
			text(" was developed by ") {
				color(NamedTextColor.GRAY)
			}
			text("TheFruxz") {
				color(NamedTextColor.YELLOW)
			}
			text(", and other contributors of the repository: ") {
				color(NamedTextColor.GRAY)
			}
			text(vendor.description.website ?: "FEHLER") {
				style(style(NamedTextColor.GOLD, TextDecoration.BOLD))
			}

			newline()

		}.notification(INFO, executor).display()

		text("JET is running & developed with Kotlin (the programming Language) from JetBrains. Check out their work https://jetbrains.com or https://kotlinlang.org")
			.color(NamedTextColor.YELLOW)
			.message(executor).display()

	}

	branch {

		addContent("version")

		concludedExecution {

			lang("interchange.internal.jet.version").replaceVariables(
				"version" to "${vendor.description.version}@${vendor.description.apiVersion}"
			).notification(GENERAL, executor).display()

		}

	}

	branch {

		addContent("website", "repository")

		concludedExecution {

			lang("interchange.internal.jet.host").replaceVariables(
				"website" to vendor.description.website
			).notification(GENERAL, executor).display()

		}

	}

	branch {

		addContent("ping")

		concludedExecution {

			lang("interchange.internal.jet.ping")
				.notification(GENERAL, executor).display()

		}

	}

})