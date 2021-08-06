package de.jet.library.structure.component

import de.jet.library.structure.app.App
import de.jet.library.structure.smart.Identifiable
import de.jet.library.structure.smart.Logging

abstract class Component(
	override val vendor: App,
) : Identifiable<Component>, Logging {

	override val logSection: String
		get() = "component/$id"

	abstract fun start()

	abstract fun stop()

	var isRunning = false

}