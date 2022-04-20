package de.moltenKt.paper

import de.moltenKt.paper.app.MoltenApp
import de.moltenKt.paper.runtime.app.LanguageSpeaker

public object MoltenEngine {

	public val appInstance: MoltenApp
		get() = MoltenApp.instance

	public val languageSpeaker: LanguageSpeaker
		get() = appInstance.languageSpeaker
	
}