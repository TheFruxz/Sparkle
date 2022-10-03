package de.fruxz.sparkle.server.component.marking

import de.fruxz.sparkle.framework.infrastructure.command.completion.buildInterchangeStructure
import de.fruxz.sparkle.framework.infrastructure.command.completion.ignoreCase
import de.fruxz.sparkle.framework.infrastructure.command.structured.StructuredPlayerInterchange
import de.fruxz.sparkle.framework.util.extension.visual.notification
import de.fruxz.sparkle.framework.util.extension.visual.ui.addItems
import de.fruxz.sparkle.framework.util.extension.coroutines.asSync
import de.fruxz.sparkle.framework.util.visual.message.Transmission.Level.APPLIED
import de.fruxz.stacked.extension.dyeGold
import de.fruxz.stacked.extension.dyeGray
import de.fruxz.stacked.plus
import de.fruxz.stacked.text

internal class MarkingInterchange : StructuredPlayerInterchange(
	label = "markings",
	protectedAccess = true,
	structure = buildInterchangeStructure {

		branch {

			addContent("giveItem")
			ignoreCase()

			concludedExecution {

				asSync { executor.inventory.addItems(MarkingComponent.markingItem) }

				text {
					this + text("The ").dyeGray()
					this + text("Marking Tool").dyeGold()
					this + text(" has been added to your inventory.").dyeGray()
				}.notification(APPLIED, executor).display()

			}

		}

	}
)