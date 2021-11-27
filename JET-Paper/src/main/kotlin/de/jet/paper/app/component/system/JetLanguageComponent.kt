package de.jet.paper.app.component.system

import de.jet.paper.structure.app.App
import de.jet.paper.structure.command.Interchange
import de.jet.paper.structure.command.InterchangeResult.SUCCESS

class JetLanguageComponent(vendor: App) : Interchange(vendor, "language", requiresAuthorization = true) {

	override val execution = execution {



		SUCCESS
	}

}