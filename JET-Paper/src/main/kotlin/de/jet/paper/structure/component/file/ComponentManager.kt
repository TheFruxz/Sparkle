package de.jet.paper.structure.component.file

import de.jet.jvm.extension.data.fromJson
import de.jet.jvm.extension.data.toJson
import de.jet.jvm.extension.div
import de.jet.jvm.extension.generateFileAndPath
import de.jet.jvm.extension.tryOrNull
import de.jet.jvm.tool.smart.identification.Identity
import de.jet.paper.structure.component.Component
import de.jet.paper.tool.data.JetYamlFile
import kotlin.io.path.readText
import kotlin.io.path.writeText

object ComponentManager {

	val path = JetYamlFile.rootPath() / "components.json"

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