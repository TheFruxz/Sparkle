package de.jet.library.structure.app

import org.jetbrains.annotations.NotNull

interface AppCompanion<T : @NotNull App> {

	var instance: T

}