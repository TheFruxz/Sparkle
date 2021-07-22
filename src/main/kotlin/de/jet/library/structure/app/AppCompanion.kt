package de.jet.library.structure.app

import de.jet.library.structure.smart.Identifiable
import org.jetbrains.annotations.NotNull

interface AppCompanion<T : @NotNull App> : Identifiable<App> {

	var instance: T

	override val id: String
		get() = instance.id

}