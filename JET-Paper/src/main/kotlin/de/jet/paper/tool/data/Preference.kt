package de.jet.paper.tool.data

import de.jet.jvm.extension.forceCast
import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.paper.app.JetCache
import de.jet.paper.app.JetCache.registeredPreferenceCache
import de.jet.paper.extension.debugLog
import de.jet.paper.extension.tasky.async
import de.jet.paper.extension.tasky.sync
import de.jet.paper.extension.tasky.task
import de.jet.paper.tool.timing.tasky.TemporalAdvice.Companion.instant
import java.util.concurrent.CompletableFuture

/**
 * @param inputType null if automatic
 */
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
	var inputType: InputType? = null,
) : Identifiable<Preference<SHELL>> {

	override val identity = "${file.file}:${path.identity}"
	private val inFilePath = path.identity

	init {

		if (initTriggerSetup && !isSavedInFile) {
			JetCache.tmp_initSetupPreferences.add(this)
		}

		JetCache.registeredPreferences[identityObject] = this

		if (inputType != null) {
			when (default) {
				is String -> inputType = InputType.STRING
				is Int -> inputType = InputType.INT
				is Long -> inputType = InputType.LONG
				is Double -> inputType = InputType.DOUBLE
				is Float -> inputType = InputType.FLOAT
				is Boolean -> inputType = InputType.BOOLEAN
			}
		}

	}

	val isSavedInFile: Boolean
		get() = file.let { jetFile ->
			jetFile.load()
			return@let jetFile.contains(inFilePath)
		}

	@Suppress("UNCHECKED_CAST")
	var content: SHELL
		get() {
			var out: SHELL
			val process = process@{
				val currentCacheValue = registeredPreferenceCache[inFilePath]

				if (useCache && currentCacheValue != null) {
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
					val newContent = if (file.contains(inFilePath) && currentFileValue != null) {
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

				return@process out
			}

			if (async || forceUseOfTasks) {
				val future = CompletableFuture<SHELL>()

				async {
					future.complete(process())
				}

				return future.get()
			} else {
				val future = CompletableFuture<SHELL>()

				sync {
					future.complete(process())
				}

				return future.get()
			}
		}
		set(value) {
			debugLog("Try to save in ($identity) the value: '$value'")
			val process = {
				if (readAndWrite) {
					file.load()
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

	fun editContent(process: SHELL.() -> Unit) {
		content = content.apply(process)
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

	fun reset() {
		content = default
	}

	fun insertFromString(string: String) = inputType?.fromStringConverter()?.let { content = it(string).forceCast() } ?: throw IllegalArgumentException("String not accepted!")

	enum class InputType {

		STRING, INT, DOUBLE, FLOAT, LONG, BOOLEAN;

		/**
		 * Null if failed to transform
		 */
		fun fromStringConverter(): (String) -> Any? = when (this) {
			STRING -> { { it } }
			INT -> { { it.toIntOrNull() } }
			DOUBLE -> { { it.toDoubleOrNull() } }
			FLOAT -> { { it.toFloatOrNull() } }
			LONG -> { { it.toLongOrNull() } }
			BOOLEAN -> { { it.toBooleanStrictOrNull() } }
		}

	}

}