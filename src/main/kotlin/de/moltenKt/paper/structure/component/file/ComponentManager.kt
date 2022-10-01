package de.moltenKt.paper.structure.component.file

import de.fruxz.ascend.extension.data.fromJson
import de.fruxz.ascend.extension.data.readJson
import de.fruxz.ascend.extension.data.toJson
import de.fruxz.ascend.extension.data.writeJson
import de.fruxz.ascend.extension.div
import de.fruxz.ascend.extension.generateFileAndPath
import de.fruxz.ascend.extension.tryOrNull
import de.fruxz.ascend.tool.smart.identification.Identity
import de.moltenKt.paper.structure.component.Component
import de.moltenKt.paper.tool.data.file.MoltenPath
import kotlin.io.path.readText
import kotlin.io.path.writeText

object ComponentManager {

	val path = MoltenPath.rootPath() / "components.json"

	private var _state: ComponentConfiguration? = null

	var state: ComponentConfiguration
		get() = _state ?: tryOrNull { path.readJson() } ?: ComponentConfiguration().also {
			path.toFile().generateFileAndPath()
			state = it
		}
		set(value) {
			_state = value
			path.toFile().generateFileAndPath()
			path.writeJson(value)
		}

	fun reloadFromDisk() {
		state = tryOrNull { path.readJson() } ?: ComponentConfiguration()
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