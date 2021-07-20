package de.jet.app

import de.jet.library.structure.app.App
import de.jet.library.structure.app.AppCompanion

class JetApp : App() {

	override val companion = Companion

	override val appIdentity = "JET"
	override val appLabel = "JET"
	override val appCache = JetCache

	override fun login() {
		TODO("Not yet implemented")
	}

	override fun boot() {
		TODO("Not yet implemented")
	}

	override fun logout() {
		TODO("Not yet implemented")
	}

	companion object : AppCompanion<JetApp> {
		override lateinit var instance: JetApp
	}

}