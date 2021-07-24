package de.jet.library.tool.data

import de.jet.app.JetCache.registeredPreferenceCache
import de.jet.library.extension.debugLog
import de.jet.library.structure.smart.Identifiable
import kotlin.io.path.pathString

class Preference<SHELL : Any>(
	val file: JetFile,
	val path: Identifiable<JetPath>,
	val default: SHELL,
	val useCache: Boolean = false,
	val readAndWrite: Boolean = true,
	var transformer: DataTransformer<SHELL, out Any> = DataTransformer.empty(),
) : Identifiable<Preference<SHELL>> {

	override val id = "${file.file.pathString}:${path.id}"

	@Suppress("UNCHECKED_CAST")
	var content: SHELL
		get() {
			val currentCacheValue = registeredPreferenceCache[id]

			if (!useCache && currentCacheValue != null) {
				return try {
					currentCacheValue as SHELL
				} catch (e: ClassCastException) {
					debugLog("Reset property $id to default \n${e.stackTrace}")
					content = default
					default
				}
			} else {
				fun toShellTransformer() = transformer.toShell as Any.() -> SHELL
				val currentFileValue = file.get<SHELL>(id)?.let { toShellTransformer()(it) }
				val newContent = if (file.loader.contains(id) && currentFileValue != null) {
					currentFileValue
				} else default

				return newContent.let {
					if (useCache)
						registeredPreferenceCache[id] = it
					if (it == default)
						file.set(id, transformer.toCore(default))
					it
				}

			}

		}
		set(value) {

			if (readAndWrite) {
				file.load() // TODO: 23.07.2021 SUS? (overriding cache?)
			}

			transformer.toCore(value).let { coreObject ->
				if (useCache)
					registeredPreferenceCache[id] = coreObject
				file.set(path.id, coreObject)
			}

			if (readAndWrite)
				file.save()

		}

	fun <CORE : Any> transformer(
		toCore: (SHELL.() -> CORE),
		toShell: (CORE.() -> SHELL),
	) = apply {
		transformer = DataTransformer(toCore, toShell)
	}

	fun <CORE : Any> transformer(
		transformer: DataTransformer<SHELL, CORE>
	) = transformer(toCore = transformer.toCore, toShell = transformer.toShell)

}