package de.jet.minecraft.tool.data

import de.jet.library.extension.collection.toArrayList
import de.jet.library.extension.data.fromJson
import de.jet.library.extension.data.toJson
import de.jet.library.extension.tag.PromisingData
import de.jet.minecraft.app.component.essentials.world.tree.WorldRenderer.RenderFolder
import de.jet.minecraft.app.component.essentials.world.tree.WorldRenderer.RenderObject
import de.jet.minecraft.app.component.essentials.world.tree.WorldRenderer.RenderWorld
import de.jet.minecraft.app.component.essentials.world.tree.WorldRenderer.WorldStructure
import de.jet.minecraft.tool.display.item.Item
import de.jet.minecraft.tool.display.world.SimpleLocation
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.bukkit.Location

data class DataTransformer<SHELL: Any, CORE: Any>(
	val toCore: SHELL.() -> CORE,
	val toShell: CORE.() -> SHELL,
) {

	companion object {

		fun <BOTH : Any> empty() =
			DataTransformer<BOTH, BOTH>({ this }, { this })

		// JSON

		inline fun <reified T : PromisingData> jsonObject() =
			DataTransformer<T, String>({ toJson() }, { fromJson() })

		fun jsonRenderObject(): DataTransformer<WorldStructure, String> {
			val module = SerializersModule {
				polymorphic(RenderObject::class) {
					subclass(RenderWorld::class)
					subclass(RenderFolder::class)
				}
			}
			val format = Json { serializersModule = module }

			return DataTransformer({
				format.encodeToString(this)
			}, {
				format.decodeFromString(this)
			})

			// TODO: 27.10.2021 For this whole process of own classes and interfaces in json, we need a system for that, that you only register a class/interface via
			// a global function or something like that. interface Data : JsonRegistered, or similar also possible!
		}

		fun jsonItem() =
			DataTransformer<Item, String>({ produceJson()}, { Item.produceByJson(this)!! })

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
