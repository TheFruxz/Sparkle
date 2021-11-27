package de.jet.paper.app.component.system

import de.jet.paper.app.interchange.SandboxInterchange
import de.jet.paper.app.interchange.ServiceInterchange
import de.jet.paper.app.interchange.player.ChangeSkinInterchange
import de.jet.paper.extension.system
import de.jet.paper.structure.app.App
import de.jet.paper.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.paper.structure.component.SmartComponent

class JetAssistiveInterchangesComponent(vendor: App = system) : SmartComponent(vendor, AUTOSTART_MUTABLE) {

	override val thisIdentity = "AssistiveInterchanges"

	override fun component() {
		//TODO currently not supported interchange(PreferenceInterchange())
		interchange(ServiceInterchange())
		interchange(SandboxInterchange())
		interchange(ChangeSkinInterchange())
	}

}