package de.jet.library.structure.command

import de.jet.app.JetCache
import de.jet.library.structure.app.App

data class CompletionVariable(
	val vendor: App,
	val label: String,
	val refreshing: Boolean,
	val generator: CompletionVariable.() -> Collection<String>,
) {

	val storagePath = "${vendor.appIdentity}:$label"

	fun computeContent() =
		if (!refreshing && JetCache.registeredCompletionVariables.containsKey(storagePath)) {
			JetCache.registeredCompletionVariables[storagePath]!!
		} else {
			generator(this).toSet().apply {

				if (!refreshing)
					JetCache.registeredCompletionVariables[storagePath] = this

			}
		}

}