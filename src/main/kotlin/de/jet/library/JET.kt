package de.jet.library

import de.jet.app.JetApp
import de.jet.library.runtime.app.LanguageSpeaker

object JET {

	val appInstance: JetApp
		get() = JetApp.instance

	val languageSpeaker: LanguageSpeaker
		get() = appInstance.languageSpeaker
	
}