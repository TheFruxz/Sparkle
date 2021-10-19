package de.jet.minecraft.app.component.essentials

import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.command.Interchange
import de.jet.minecraft.structure.command.InterchangeResult.SUCCESS

class WorldInterchange(vendor: App) : Interchange(vendor, "world", requiresAuthorization = true) {

	override val execution = execution {
		SUCCESS
	}

}