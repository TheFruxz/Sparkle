package de.moltenKt.paper.app.interchange

import de.moltenKt.paper.extension.display.notification
import de.moltenKt.paper.structure.command.structured.StructuredInterchange
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure
import de.moltenKt.paper.structure.command.completion.component.CompletionAsset

internal class PlaygroundInterchange : StructuredInterchange("playground", buildInterchangeStructure {

	branch {

		addContent("transmissionSound")

		branch {

			addContent(CompletionAsset.TRANSMISSION_LEVEL)

			concludedExecution {

				val level = getInput(1, CompletionAsset.TRANSMISSION_LEVEL)

				"<gradient:gray:yellow>This is the sound of the '<gold>${level.name}</gold>' Transmission!"
					.notification(level, executor)
					.display()

			}

		}

	}

})