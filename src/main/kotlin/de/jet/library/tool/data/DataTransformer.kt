package de.jet.library.tool.data

import de.jet.library.tool.display.world.SimpleLocation
import org.bukkit.Location

data class DataTransformer<SHELL: Any, CORE: Any>(
	val toCore: SHELL.() -> CORE,
	val toShell: CORE.() -> SHELL,
) {

	companion object {

		fun <BOTH : Any> empty() =
			DataTransformer<BOTH, BOTH>({ this }, { this })

		// colors

		fun simpleColorCode() =
			DataTransformer<String, String>({ replace("§", "COLOR>") }, { replace("COLOR>", "§") })

		// simple location

		fun simpleLocationBukkit() =
			DataTransformer<Location, SimpleLocation>({ SimpleLocation.ofBukkit(this) }, { bukkit })

		fun simpleLocationListBukkit() =
			DataTransformer<List<Location>, List<SimpleLocation>>({ map { SimpleLocation.ofBukkit(it) }}, { map { it.bukkit } })

		fun simpleLocationArrayBukkit() =
			DataTransformer<Array<Location>, Array<SimpleLocation>>({ map { SimpleLocation.ofBukkit(it) }.toTypedArray()}, { map { it.bukkit }.toTypedArray() })

	}

}
