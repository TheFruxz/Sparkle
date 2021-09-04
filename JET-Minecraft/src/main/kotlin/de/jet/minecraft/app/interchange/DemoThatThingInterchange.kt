package de.jet.minecraft.app.interchange

import de.jet.library.extension.buildBoolean
import de.jet.library.extension.turnTrue
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.command.CompletionVariable
import de.jet.minecraft.structure.command.CompletionVariable.Companion
import de.jet.minecraft.structure.command.Interchange
import de.jet.minecraft.structure.command.InterchangeResult
import de.jet.minecraft.structure.command.InterchangeResult.SUCCESS
import de.jet.minecraft.structure.command.buildCompletion
import de.jet.minecraft.structure.command.isRequired
import de.jet.minecraft.structure.command.live.InterchangeAccess
import de.jet.minecraft.structure.command.mustMatchOutput
import de.jet.minecraft.structure.command.next

class DemoThatThingInterchange(vendor: App) : Interchange(vendor, "demo", completion = buildCompletion {
	next(CompletionVariable.INT) isRequired true mustMatchOutput false
	next(listOf("test", "YesItIS!", "wowWW!!!")) isRequired false mustMatchOutput true
	next(Companion.PLAYER_NAME) isRequired false mustMatchOutput false
}) {

	override val execution: InterchangeAccess.() -> InterchangeResult = o@{

		if (checkParameter[0]) {

			if (checkParameter[1] || parameters.isNotEmpty()) {

				if (checkParameter[2] || parameters.size < 3) {

					buildBoolean {
						println("state: $property")
						turnTrue()
						println("newstate: $property")
					}.also {
						println("final: $it")
					}

					println("success!")

				} else
					println("failed at 2")

			} else
				println("failed at 1")

		} else
			println("failed at 0")

		return@o SUCCESS
	}

}