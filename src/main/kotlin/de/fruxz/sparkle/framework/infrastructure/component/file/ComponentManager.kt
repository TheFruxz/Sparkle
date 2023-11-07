package de.fruxz.sparkle.framework.infrastructure.component.file

import dev.fruxz.ascend.extension.container.modified
import dev.fruxz.ascend.extension.createFileAndDirectories
import dev.fruxz.ascend.extension.tryOrNull
import dev.fruxz.ascend.json.readJson
import dev.fruxz.ascend.json.writeJson
import dev.fruxz.ascend.tool.smart.identification.Identity
import de.fruxz.sparkle.framework.data.file.SparklePath
import de.fruxz.sparkle.framework.infrastructure.component.Component
import kotlin.io.path.div

object ComponentManager {

	val path = SparklePath.rootPath() / "components.json"

	private var _state: ComponentConfiguration? = null

	var state: ComponentConfiguration
		get() = _state ?: tryOrNull { path.readJson() } ?: ComponentConfiguration().also {
			path.createFileAndDirectories()
			state = it
		}
		set(value) {
			_state = value
			path.createFileAndDirectories()
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
			components = components.modified {
				removeAll { it.identity == identity.identity }
				add(ComponentConfigurationEntry(identity.identity, isAutoStart, isBlocked))
			}
		}
	}

}