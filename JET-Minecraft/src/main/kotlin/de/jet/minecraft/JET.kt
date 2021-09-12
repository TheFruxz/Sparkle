package de.jet.minecraft

import de.jet.minecraft.app.JetApp
import de.jet.minecraft.runtime.app.LanguageSpeaker

object JET {

	val appInstance: JetApp
		get() = JetApp.instance

	val languageSpeaker: LanguageSpeaker
		get() = appInstance.languageSpeaker
	
}