package de.jet.minecraft.app.component.feature

import de.jet.minecraft.app.JetFeature
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.component.Component

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

}