package de.jet.paper.structure.classes

import de.jet.jvm.extension.jetTry
import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.paper.app.JetApp
import de.jet.paper.extension.debugLog
import de.jet.paper.structure.app.App
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder

object JSON {

	val jsonExtensions = mutableListOf<Pair<Identifiable<App>, SerializersModuleBuilder.() -> Unit>>()

	private var isInitialized = false

	private var internalJsonModule = SerializersModule {

	}

	private var internalJsonFormat = Json {
		this.allowStructuredMapKeys = true
		this.prettyPrint = true
		this.useArrayPolymorphism = true
		serializersModule = internalJsonModule
	}

	@Suppress("RecursivePropertyAccessor")
	val jsonModule: SerializersModule
		get() {
			return try {
				internalJsonModule
			} catch (e: Exception) {
				e.printStackTrace()
				jsonModule
			}
		}

	@Suppress("RecursivePropertyAccessor")
	val jsonFormat: Json
		get() {
			firstRun()
			return try {
				internalJsonFormat
			} catch (e: Exception) {
				e.printStackTrace()
				jsonFormat
			}
		}

	fun firstRun() {
        if (isInitialized) return
        isInitialized = true
        debugLog("Initializing JSON:")
		debugLog("JetApp base registration...")
		JetApp.registerSerialization()
		debugLog("JetApp base rebuild...")
        rebuildJsonInstructions()
		debugLog("Initializing JSON finished!")
    }

	fun rebuildJsonInstructions() {
		debugLog("rebuilding json-instructions with ${jsonExtensions.size} objects...")
		internalJsonModule = SerializersModule {
			jsonExtensions.map { it.second }.forEach(this::apply)
		}
		internalJsonFormat = Json {
			serializersModule = internalJsonModule
		}
		debugLog("successfully rebuild ${jsonExtensions.size} json instructions into jsonModule!")
	}

	fun addExtension(vendor: Identifiable<App>, extension: SerializersModuleBuilder.() -> Unit) = jetTry {
		debugLog("Adding JSON-Extension as '${vendor.identity}'!")
		jsonExtensions.add(vendor to extension)
		rebuildJsonInstructions()
		debugLog("Adding succeed!")
	}

	fun removeVendorExtensions(vendor: Identifiable<App>) = jetTry {
		debugLog("Removing JSON-Classes of '${vendor.identity}'...")
		jsonExtensions.removeAll { it.first.identityObject == vendor }
		rebuildJsonInstructions()
		debugLog("Remove succeed!")
	}

}