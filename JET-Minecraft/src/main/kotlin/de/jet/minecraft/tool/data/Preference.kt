package de.jet.minecraft.tool.data

import de.jet.library.tool.smart.Identifiable
import de.jet.minecraft.app.JetCache
import de.jet.minecraft.app.JetCache.registeredPreferenceCache
import de.jet.minecraft.extension.debugLog
import de.jet.minecraft.extension.tasky.task
import de.jet.minecraft.tool.timing.tasky.TemporalAdvice.Companion.instant

data class Preference<SHELL : Any>(
	val file: JetFile,
	val path: Identifiable<JetPath>,
	val default: SHELL,
	val useCache: Boolean = false,
	val readAndWrite: Boolean = true,
	var transformer: DataTransformer<SHELL, out Any> = DataTransformer.empty(),
	var async: Boolean = false,
	var forceUseOfTasks: Boolean = false,
	var initTriggerSetup: Boolean = true,
) : Identifiable<Preference<SHELL>> {

	override val identity = "${file.file}:${path.identity}"
	private val inFilePath = path.identity

	init {
		if (initTriggerSetup && !isSavedInFile) {
			JetCache.tmp_initSetupPreferences.add(this)
		}
		JetCache.registeredPreferences[identityObject] = this
	}

	val isSavedInFile: Boolean
		get() = file.let { jetFile ->
			jetFile.load()
			return@let jetFile.loader.contains(inFilePath)
		}

	@Suppress("UNCHECKED_CAST")
	var content: SHELL
		get() {
			var out: SHELL = default
			val process = {
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
					val currentFileValue = file.get<SHELL>(inFilePath)?.let { toShellTransformer()(it).also { core ->
						debugLog("transformed '$it'(shell) from '$core'(core)")
					} }
					val newContent = if (file.loader.contains(inFilePath) && currentFileValue != null) {
						currentFileValue
					} else default

					out = newContent.let {
						if (useCache)
							registeredPreferenceCache[inFilePath] = it
						if (it == default)
							file[inFilePath] = transformer.toCore(default)
						file.save()
						it
					}

				}
			}

			if (async || forceUseOfTasks) {
				task(instant(async = async)) {
					process()
				}
			} else
				process()

			return out
		}
		set(value) {
			debugLog("Try to save in ($identity) the value: '$value'")
			val process = {
				if (readAndWrite) {
					file.load() // TODO: 23.07.2021 SUS? (overriding cache?)
				}

				transformer.toCore(value).let { coreObject ->
					if (useCache)
						registeredPreferenceCache[identity] = coreObject
					file[path.identity] = coreObject
					debugLog("transformed '$value'(shell) to '$coreObject'(core)")
				}

				if (readAndWrite)
					file.save()
			}

			if (async || forceUseOfTasks) {
				task(instant(async = async)) {
					process()
				}
			} else
				process()

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