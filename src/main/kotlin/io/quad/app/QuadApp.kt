package io.quad.app

import io.quad.library.structure.app.App
import io.quad.library.structure.app.AppCache
import io.quad.library.structure.app.AppCompanion

class QuadApp : App() {

	override val companion = Companion

	override val appIdentity = "QUAD"
	override val appBrand = "QUAD"
	override val appCache = QuadCache

	companion object : AppCompanion<QuadApp> {
		override lateinit var instance: QuadApp
	}

}