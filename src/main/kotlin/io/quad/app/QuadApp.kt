package io.quad.app

import io.quad.library.structure.app.App
import io.quad.library.structure.app.AppCache
import io.quad.library.structure.app.AppCompanion

class QuadApp : App() {

	override val companion = Companion

	override val appIdentity = "QUAD"
	override val appLabel = "QUAD"
	override val appCache = QuadCache

	override fun login() {
		TODO("Not yet implemented")
	}

	override fun boot() {
		TODO("Not yet implemented")
	}

	override fun logout() {
		TODO("Not yet implemented")
	}

	companion object : AppCompanion<QuadApp> {
		override lateinit var instance: QuadApp
	}

}