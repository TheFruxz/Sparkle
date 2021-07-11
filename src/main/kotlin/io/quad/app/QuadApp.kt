package io.quad.app

import io.quad.library.structure.app.App
import io.quad.library.structure.app.AppCompanion

class QuadApp : App() {

	override val companion = Companion

	companion object : AppCompanion<QuadApp> {
		override lateinit var instance: QuadApp
	}

}