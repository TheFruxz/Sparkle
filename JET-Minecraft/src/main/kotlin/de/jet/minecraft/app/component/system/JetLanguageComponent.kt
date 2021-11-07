package de.jet.minecraft.app.component.system

import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.command.Interchange
import de.jet.minecraft.structure.command.InterchangeResult.SUCCESS

class JetLanguageComponent(vendor: App) : Interchange(vendor, "language", requiresAuthorization = true) {

	override val execution = execution {



		SUCCESS
	}

}