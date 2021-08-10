package de.jet.library.tool.data

import de.jet.app.JetCache.registeredPreferenceCache
import de.jet.library.JET
import de.jet.library.extension.debugLog
import de.jet.library.extension.tasky.task
import de.jet.library.tool.smart.Identifiable
import de.jet.library.tool.tasky.TemporalAdvice
import de.jet.library.tool.tasky.TemporalAdvice.Companion
import kotlin.io.path.pathString

class Preference<SHELL : Any>(
	val file: JetFile,
	val path: Identifiable<JetPath>,
	val default: SHELL,
	val useCache: Boolean = false,
	val readAndWrite: Boolean = true,
	var transformer: DataTransformer<SHELL, out Any> = DataTransformer.empty(),
	var async: Boolean = false,
) : Identifiable<Preference<SHELL>> {

	override val id = "${file.file.pathString}:${path.id}"
	val inFilePath = path.id

	@Suppress("UNCHECKED_CAST")
	var content: SHELL
		get() {
			var out: SHELL = default
			task(JET.appInstance, TemporalAdvice.instant(async = async)) {
				val currentCacheValue = registeredPreferenceCache[inFilePath]

				if (!useCache && currentCacheValue != null) {
					out = try {
						currentCacheValue as SHELL
					} catch (e: ClassCastException) {
						debugLog("Reset property $inFilePath to default \n${e.stackTrace}")
						content = default
						default
					}
				} else {
					file.load()
					fun toShellTransformer() = transformer.toShell as Any.() -> SHELL
					val currentFileValue = file.get<SHELL>(inFilePath)?.let { toShellTransformer()(it) }
					val newContent = if (file.loader.contains(inFilePath) && currentFileValue != null) {
						currentFileValue
					} else default

					out = newContent.let {
						if (useCache)
							registeredPreferenceCache[inFilePath] = it
						if (it == default)
							file.set(inFilePath, transformer.toCore(default))
						file.save()
						it
					}

				}
			}
			return out
		}
		set(value) {
			task(JET.appInstance, Companion.instant(async = async)) {
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