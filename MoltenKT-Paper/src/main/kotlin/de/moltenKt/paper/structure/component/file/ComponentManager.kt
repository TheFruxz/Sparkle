package de.moltenKt.paper.structure.component.file

import de.moltenKt.jvm.extension.data.fromJson
import de.moltenKt.jvm.extension.data.toJson
import de.moltenKt.jvm.extension.div
import de.moltenKt.jvm.extension.generateFileAndPath
import de.moltenKt.jvm.extension.tryOrNull
import de.moltenKt.jvm.tool.smart.identification.Identity
import de.moltenKt.paper.structure.component.Component
import de.moltenKt.paper.tool.data.MoltenYamlFile
import de.moltenKt.paper.tool.data.file.MoltenFileSystem
import kotlin.io.path.readText
import kotlin.io.path.writeText

object ComponentManager {

	val path = MoltenFileSystem.homePath / "components.json"

	private var _state: ComponentConfiguration? = null

	var state: ComponentConfiguration
		get() = _state ?: tryOrNull { path.readText().fromJson() } ?: ComponentConfiguration().also {
			path.toFile().generateFileAndPath()
			state = it
		}
		set(value) {
			_state = value
			path.toFile().generateFileAndPath()
			path.writeText(value.toJson())
		}

	fun reloadFromDisk() {
		state = tryOrNull { path.readText().fromJson() } ?: ComponentConfiguration()
	}

	fun isRegistered(identity: Identity<out Component>): Boolean = state.components.any { it.identity == identity.identity }

	fun registerComponent(component: Component, isAutoStart: Boolean = component.behaviour.defaultIsAutoStart, isBlocked: Boolean = false) {
		state = state.apply {
			components += ComponentConfigurationEntry(component.identity, isAutoStart, isBlocked)
		}
	}

	fun getComponent(identity: Identity<out Component>) = state.components.find { it.identity == identity.identity }

	fun editComponent(identity: Identity<out Component>, isAutoStart: Boolean = getComponent(identity)!!.isAutoStart, isBlocked: Boolean = getComponent(identity)!!.isBlocked) {
		state = state.apply {
			components = components.toMutableList().apply {
				removeAll { it.identity == identity.identity }
				add(ComponentConfigurationEntry(identity.identity, isAutoStart, isBlocked))
			}
		}
	}

}