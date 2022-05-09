package de.moltenKt.paper.tool.data

import de.moltenKt.core.extension.forceCast
import de.moltenKt.core.extension.tryOrNull
import de.moltenKt.core.tool.smart.identification.Identifiable
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.app.MoltenCache.registeredPreferenceCache
import de.moltenKt.paper.extension.debugLog
import de.moltenKt.paper.extension.tasky.async
import de.moltenKt.paper.extension.tasky.sync
import de.moltenKt.paper.extension.tasky.task
import de.moltenKt.paper.tool.timing.tasky.TemporalAdvice.Companion.instant
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit.SECONDS
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * @param inputType null if automatic
 */
data class Preference<SHELL : Any>(
	val file: MoltenFile,
	val path: Identifiable<MoltenPath>,
	val default: SHELL,
	val useCache: Boolean = false,
	val readAndWrite: Boolean = true,
	var transformer: DataTransformer<SHELL, out Any> = DataTransformer.empty(),
	var async: Boolean = false,
	var forceUseOfTasks: Boolean = false,
	var initTriggerSetup: Boolean = true,
	var inputType: InputType? = null,
	val timeOut: Duration = 4.seconds
) : Identifiable<Preference<SHELL>> {

	override val identity = "${file.file}:${path.identity}"
	private val inFilePath = path.identity

	init {

		if (initTriggerSetup && !isSavedInFile) {
			MoltenCache.tmp_initSetupPreferences += this
		}

		MoltenCache.registeredPreferences += identityObject to this

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
		get() = file.let { moltenFile ->
			moltenFile.load()
			return@let moltenFile.contains(inFilePath)
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
					val currentFileValue = file.get<SHELL>(inFilePath)?.let {
						toShellTransformer()(it).also { core ->
							debugLog("transformed '$it'(shell) from '$core'(core)")
						}
					}
					val newContent = if (file.contains(inFilePath) && currentFileValue != null) {
						currentFileValue
					} else default

					out = newContent.let {
						if (useCache)
							registeredPreferenceCache += inFilePath to it
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

				async { future.complete(process()) }

				return tryOrNull { future.get(timeOut.inWholeSeconds, SECONDS) } ?: default.also {
					debugLog("Preference access (async) failed for $identity with default $default")
				}

			} else {
				val future = CompletableFuture<SHELL>()

				sync { future.complete(process()) }

				return tryOrNull { future.get(timeOut.inWholeSeconds, SECONDS) } ?: default.also {
					debugLog("Preference access (sync) failed for $identity with default $default")
				}
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
						registeredPreferenceCache += identity to coreObject
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

	fun insertFromString(string: String) = inputType?.fromStringConverter()?.let { content = it(string).forceCast() }
		?: throw IllegalArgumentException("String not accepted!")

	enum class InputType {

		STRING, INT, DOUBLE, FLOAT, LONG, BOOLEAN;

		/**
		 * Null if failed to transform
		 */
		fun fromStringConverter(): (String) -> Any? = when (this) {
			STRING -> {
				{ it }
			}
			INT -> {
				{ it.toIntOrNull() }
			}
			DOUBLE -> {
				{ it.toDoubleOrNull() }
			}
			FLOAT -> {
				{ it.toFloatOrNull() }
			}
			LONG -> {
				{ it.toLongOrNull() }
			}
			BOOLEAN -> {
				{ it.toBooleanStrictOrNull() }
			}
		}

	}

}