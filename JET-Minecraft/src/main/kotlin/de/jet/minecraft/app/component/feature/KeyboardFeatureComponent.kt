package de.jet.minecraft.app.component.feature

import de.jet.library.tool.smart.identification.Identifiable
import de.jet.minecraft.app.JetFeature
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.app.event.EventListener
import de.jet.minecraft.structure.component.Component
import de.jet.minecraft.structure.service.Service
import de.jet.minecraft.tool.timing.tasky.Tasky
import de.jet.minecraft.tool.timing.tasky.TemporalAdvice

class KeyboardFeatureComponent(override val vendor: App) : Component(vendor, RunType.AUTOSTART_MUTABLE) {

    override fun register() {
        JetFeature.VISUAL_KEYBOARD.registerIfNotRegistered()
    }

    override fun start() {
        JetFeature.VISUAL_KEYBOARD.isEnabled = true
    }

    override fun stop() {
        JetFeature.VISUAL_KEYBOARD.isEnabled = false
    }

    override val thisIdentity = "feature/VisualKeyboard"

    class RequestService(override val vendor: Identifiable<App>) : Service {
        override val temporalAdvice: TemporalAdvice
            get() = TODO("Not yet implemented")
        override val process: Tasky.() -> Unit
            get() = TODO("Not yet implemented")
        override val thisIdentity: String
            get() = TODO("Not yet implemented")
    }

    class RequestListener(override val vendor: App) : EventListener {

    }

}