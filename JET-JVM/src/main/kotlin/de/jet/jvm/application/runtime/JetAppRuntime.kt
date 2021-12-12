package de.jet.jvm.application.runtime

import de.jet.jvm.application.configuration.JetApp
import de.jet.jvm.application.configuration.JetAppConfigController
import de.jet.jvm.application.configuration.JetAppConfigModule
import de.jet.jvm.application.extension.AppExtension
import de.jet.jvm.application.tag.Version
import de.jet.jvm.application.tag.version
import de.jet.jvm.extension.div
import de.jet.jvm.extension.pathAsFileFromRuntime
import de.jet.jvm.tool.timing.calendar.Calendar
import java.io.File
import kotlin.concurrent.thread

class JetAppRuntime(override val identity: String, override val version: Version = 1.0.version) :
	JetApp(identity, version) {

	private val runningExtensions = mutableListOf<AppExtension<*, *, *>>()

	private lateinit var module: JetAppConfigModule

	internal fun init() {
		JetAppConfigController.apply {
			module = JetAppConfigModule.autoGenerateFromApp(this@JetAppRuntime)
			addApp(module)
			(getApp(this@JetAppRuntime)!!.appFileFolderPath + "info.jetRun").pathAsFileFromRuntime().apply {
				if (!exists()) {
					toPath().parent.toFile().mkdirs()
					createNewFile()
					writeText("installed='${Calendar.now().javaDate}'")
				}
			}
		}
	}

	val appFolder: File by lazy {
		return@lazy JetAppConfigController.getApp(this)?.appFileFolderPath.let { folderPath ->
			if (folderPath != null) {
				return@let folderPath.pathAsFileFromRuntime()
			} else {
				return@let JetAppConfigModule.autoGenerateFromApp(this).let {
					JetAppConfigController.addApp(it)
					it.appFileFolderPath.pathAsFileFromRuntime()
				}
			}
		}
	}

	fun getLocalFilePath(localPath: String) =
		appFolder.toPath() / localPath

	fun <RUNTIME, ACCESSOR_OUT, UNIT, T : AppExtension<RUNTIME, ACCESSOR_OUT, UNIT>> attach(
		extension: T,
		createThread: Boolean = false,
		runtime: (RUNTIME) -> ACCESSOR_OUT
	) {
		if (extension.parallelRunAllowed || runningExtensions.all { it.identity != extension.identity }) {
			runningExtensions.add(extension)
			if (createThread) {
				thread {
					extension.runtimeAccessor(runtime)
				}
			} else
				extension.runtimeAccessor(runtime)
		} else
			throw IllegalStateException("The extension '${extension.identity}' is already running and is not allowed to run parallel to itself!")
	}

	fun <RUNTIME, ACCESSOR_OUT, UNIT, T : AppExtension<RUNTIME, ACCESSOR_OUT, UNIT>> attachWith(
		extension: T,
		createThread: Boolean = false,
		runtime: RUNTIME.() -> ACCESSOR_OUT
	) =
		attach(extension, createThread, runtime)

}