package de.jet.minecraft.app.interchange

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
	next(CompletionVariable.INT) isRequired true mustMatchOutput true
	next(listOf("test", "YesItIS!", "wowWW!!!")) isRequired false mustMatchOutput true
	next(Companion.PLAYER_NAME) isRequired false mustMatchOutput false
}) {

	override val execution: InterchangeAccess.() -> InterchangeResult = o@{

		if (checkParameter[0]) {

			if (checkParameter[1]) {

				if (checkParameter[2]) {

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