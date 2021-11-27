package de.jet.paper

import de.jet.paper.app.JetApp
import de.jet.paper.runtime.app.LanguageSpeaker

object JET {

	val appInstance: JetApp
		get() = JetApp.instance

	val languageSpeaker: LanguageSpeaker
		get() = appInstance.languageSpeaker
	
}