package de.jet.library.tool.data

data class DataTransformer<SHELL: Any, CORE: Any>(
	val toCore: SHELL.() -> CORE,
	val toShell: CORE.() -> SHELL,
) {

	companion object {

		fun <BOTH : Any> empty() =
			DataTransformer<BOTH, BOTH>({ this }, { this })

		fun simpleColorCode() =
			DataTransformer<String, String>({ replace("§", "COLOR>") }, { replace("COLOR>", "§") })

	}

}
