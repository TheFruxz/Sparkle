package de.jet.jvm.application.runtime

import de.jet.jvm.application.configuration.JetApp
import de.jet.jvm.application.configuration.JetAppConfigController
import de.jet.jvm.application.configuration.JetAppConfigModule
import de.jet.jvm.application.extension.AppExtension
import de.jet.jvm.application.tag.Version
import de.jet.jvm.application.tag.version
import de.jet.jvm.extension.data.json
import de.jet.jvm.extension.div
import de.jet.jvm.extension.pathAsFileFromRuntime
import de.jet.jvm.tool.timing.calendar.Calendar
import java.io.File
import java.nio.file.Path
import kotlin.concurrent.thread
import kotlin.io.path.pathString

/**
 * This class represents a running Jet application and its properties.
 * At this app it is possible, to attach/start extensions and get
 * the application allowed directory to store and read files.
 * @param identity is the identity of the application.
 * @param version is the current version of the application.
 * @author Fruxz
 * @since 1.0
 */
class JetAppRuntime(override val identity: String, override val version: Version = 1.0.version) : JetApp(identity, version) {

	/**
	 * The current registered and running extensions as a list
	 * @author Fruxz
	 * @since 1.0
	 */
	private val runningExtensions = mutableListOf<AppExtension<*, *, *>>()

	/**
	 * The current state of itself as a [JetAppConfigModule]
	 * @author Fruxz
	 * @since 1.0
	 */
	private lateinit var module: JetAppConfigModule

	/**
	 * The initiation function, that creates the workspace files
	 * and the info file.
	 * @author Fruxz
	 * @since 1.0
	 */
	internal fun init() {
		JetAppConfigController.apply {
			module = JetAppConfigModule.autoGenerateFromApp(this@JetAppRuntime)
			addApp(module)
			(getApp(this@JetAppRuntime)!!.appFileFolderPath + RUNNER_FILE_NAME).pathAsFileFromRuntime().apply {
				if (!exists()) {
					toPath().parent.toFile().mkdirs()
					createNewFile()
					writeText(json("""
						{
							"init_date": "${Calendar.now()}",
							"init_app_state": {
								"identity": "$identity",
								"version": "${version.versionNumber}",
								"extensions": "${runningExtensions.joinToString { it.identity }}",
								"genuinFile": "${this.toPath().pathString.replace("\\", "/")}"
							}
						}
					""".trimIndent()).value)
				}
			}
		}
	}

	/**
	 * The folder, where the application is allowed to store files.
	 * @author Fruxz
	 * @since 1.0
	 */
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

	/**
	 * The path, where the [appFolder] is located.
	 * @see appFolder
	 * @author Fruxz
	 * @since 1.0
	 */
	val appFolderPath: Path by lazy {
		appFolder.toPath()
	}

	/**
	 * Get a file with the [localPath] within the application folder [appFolder].
	 * @param localPath the additional path to the file
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getLocalFilePath(localPath: String) =
		appFolder.toPath() / localPath

	/**
	 * This function adds and runs an extension with the current application runtime.
	 * @param extension is the extension, which you want to add and run.
	 * @param createThread is the boolean, which defines if the extension should be run in a new thread. - default: false
	 * @param runtime is the function, which is the execution process of the extension.
	 * @author Fruxz
	 * @since 1.0
	 */
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

	/**
	 * This function adds and runs an extension with the current application runtime.
	 * @param extension is the extension, which you want to add and run.
	 * @param createThread is the boolean, which defines if the extension should be run in a new thread. - default: false
	 * @param runtime is the function, which is the execution process of the extension as a with view.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun <RUNTIME, ACCESSOR_OUT, UNIT, T : AppExtension<RUNTIME, ACCESSOR_OUT, UNIT>> attachWith(
		extension: T,
		createThread: Boolean = false,
		runtime: RUNTIME.() -> ACCESSOR_OUT
	) =
		attach(extension, createThread, runtime)

	companion object {

		const val RUNNER_FILE_NAME = "jet-app-info.json"

	}

}