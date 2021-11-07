package de.jet.minecraft.app.component.system

import de.jet.minecraft.app.interchange.SandboxInterchange
import de.jet.minecraft.app.interchange.ServiceInterchange
import de.jet.minecraft.app.interchange.player.ChangeSkinInterchange
import de.jet.minecraft.extension.system
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.component.Component.RunType.AUTOSTART_MUTABLE
import de.jet.minecraft.structure.component.SmartComponent

class JetAssistiveInterchangesComponent(vendor: App = system) : SmartComponent(vendor, AUTOSTART_MUTABLE) {

	override val thisIdentity = "AssistiveInterchanges"

	override fun component() {
		//TODO currently not supported interchange(PreferenceInterchange())
		interchange(ServiceInterchange())
		interchange(SandboxInterchange())
		interchange(ChangeSkinInterchange())
	}

}