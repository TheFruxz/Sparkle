package de.moltenKt.paper.app.interchange

import de.moltenKt.jvm.extension.container.replaceVariables
import de.moltenKt.paper.extension.display.message
import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.extension.lang
import de.moltenKt.paper.structure.command.StructuredInterchange
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.tool.display.message.Transmission.Level.GENERAL
import de.moltenKt.paper.tool.display.message.Transmission.Level.INFO
import de.moltenKt.unfold.newline
import de.moltenKt.unfold.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.Style.style
import net.kyori.adventure.text.format.TextDecoration.BOLD

class MoltenKtInterchange : StructuredInterchange("moltenkt", protectedAccess = false, structure = buildInterchangeStructure {

	concludedExecution {

		text {

			text("MoltenKT") {
				style(style(NamedTextColor.GOLD, BOLD))
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
				style(style(NamedTextColor.GOLD, BOLD))
			}

			newline()

		}.notification(INFO, executor).display()

		text("MoltenKT is running & developed with Kotlin (the programming Language) from JetBrains. Check out their work https://jetbrains.com or https://kotlinlang.org")
			.color(NamedTextColor.YELLOW)
			.message(executor).display()

	}

	branch {

		addContent("version")

		concludedExecution {

			lang("interchange.internal.molten.version").replaceVariables(
				"version" to "${vendor.description.version}@${vendor.description.apiVersion}"
			).notification(GENERAL, executor).display()

		}

	}

	branch {

		addContent("website", "repository")

		concludedExecution {

			lang("interchange.internal.molten.host").replaceVariables(
				"website" to vendor.description.website
			).notification(GENERAL, executor).display()

		}

	}

	branch {

		addContent("ping")

		concludedExecution {

			lang("interchange.internal.molten.ping")
				.notification(GENERAL, executor).display()

		}

	}

})