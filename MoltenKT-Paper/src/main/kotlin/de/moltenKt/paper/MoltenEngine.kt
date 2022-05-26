package de.moltenKt.paper

import de.moltenKt.paper.app.MoltenApp
import de.moltenKt.paper.runtime.app.LanguageSpeaker

object MoltenEngine {

	val appInstance: MoltenApp
		get() = MoltenApp.instance

	val languageSpeaker: LanguageSpeaker
		get() = appInstance.languageSpeaker
	
}