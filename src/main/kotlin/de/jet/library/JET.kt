package de.jet.library

import de.jet.app.JetApp

object JET {

	val pluginInstance: JetApp
		get() = JetApp.instance

	fun demo(): Any? = null

}