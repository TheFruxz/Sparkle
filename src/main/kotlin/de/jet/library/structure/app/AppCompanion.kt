package de.jet.library.structure.app

import de.jet.library.tool.smart.Identifiable
import org.jetbrains.annotations.NotNull

interface AppCompanion<T : @NotNull App> : Identifiable<App> {

	var instance: T

	override val id: String
		get() = instance.id

}