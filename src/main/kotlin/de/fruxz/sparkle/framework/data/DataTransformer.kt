package de.fruxz.sparkle.framework.data

import dev.fruxz.ascend.extension.container.toArrayList
import dev.fruxz.ascend.json.fromJsonString
import dev.fruxz.ascend.json.toJsonString

data class DataTransformer<SHELL: Any, CORE: Any>(
	val toCore: SHELL.() -> CORE,
	val toShell: CORE.() -> SHELL,
) {

	companion object {

		@JvmStatic
		fun <BOTH : Any> empty() =
			DataTransformer<BOTH, BOTH>({ this }, { this })

		// JSON
		@JvmStatic
		inline fun <reified T : Any> json() =
			DataTransformer<T, String>(
				{ this.toJsonString() },
				{ this.fromJsonString() },
			)

		// collections

		@JvmStatic
		inline fun <reified SET> setCollection() =
			DataTransformer<Set<SET>, ArrayList<SET>>(
				{ toArrayList() },
				{ toSet() },
			)

		// simple location

	}

}
