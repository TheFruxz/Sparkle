package de.jet.minecraft.tool.data

import de.jet.library.extension.collection.toArrayList
import de.jet.minecraft.structure.classes.JSON
import de.jet.minecraft.tool.display.item.Item
import de.jet.minecraft.tool.display.world.SimpleLocation
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.bukkit.Location

data class DataTransformer<SHELL: Any, CORE: Any>(
	val toCore: SHELL.() -> CORE,
	val toShell: CORE.() -> SHELL,
) {

	companion object {

		fun <BOTH : Any> empty() =
			DataTransformer<BOTH, BOTH>({ this }, { this })

		// JSON
		inline fun <reified T : Any> json() =
			DataTransformer<T, String>(
				{ JSON.jsonFormat.encodeToString(this) },
				{ JSON.jsonFormat.decodeFromString(this) },
			)

		fun jsonItem() =
			DataTransformer<Item, String>({ produceJson() }, { Item.produceByJson(this)!! })

		// collections

		inline fun <reified SET> setCollection() =
			DataTransformer<Set<SET>, ArrayList<SET>>(
				{ toArrayList() },
				{ toSet() },
			)

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
