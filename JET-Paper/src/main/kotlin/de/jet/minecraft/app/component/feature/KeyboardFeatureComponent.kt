package de.jet.minecraft.app.component.feature

import de.jet.jvm.extension.collection.replaceVariables
import de.jet.jvm.extension.forceCast
import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.minecraft.app.JetCache
import de.jet.minecraft.app.JetFeature
import de.jet.minecraft.extension.display.notification
import de.jet.minecraft.extension.paper.onlinePlayers
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.app.event.EventListener
import de.jet.minecraft.structure.component.Component
import de.jet.minecraft.structure.service.Service
import de.jet.minecraft.tool.display.message.Transmission
import de.jet.minecraft.tool.input.Keyboard
import de.jet.minecraft.tool.timing.tasky.Tasky
import de.jet.minecraft.tool.timing.tasky.TemporalAdvice
import org.bukkit.entity.HumanEntity

class KeyboardFeatureComponent(override val vendor: App) : Component(vendor, RunType.AUTOSTART_MUTABLE) {

    override val thisIdentity = "feature/VisualKeyboard"

    override fun register() {
        JetFeature.VISUAL_KEYBOARD.registerIfNotRegistered()
    }

    override fun start() {

        RequestService(vendor).let { service ->
            vendor.register(service)
            vendor.start(service)
        }

        RequestListener(vendor).let(vendor::add)

        JetFeature.VISUAL_KEYBOARD.isEnabled = true

    }

    override fun stop() {

        RequestService(vendor).let { service ->
            vendor.stop(service)
            vendor.unregister(service)
        }

        RequestListener(vendor).let(vendor::remove)

        JetFeature.VISUAL_KEYBOARD.isEnabled = false

    }

    class RequestService(override val vendor: Identifiable<App>) : Service {
        override val thisIdentity = "KeyboardRequest"
        override val temporalAdvice = TemporalAdvice.ticking(20 * 10, 20 * 10, true)
        override val process: Tasky.() -> Unit = {

            onlinePlayers.forEach { player ->

                val request = getKeyboardRequestByPlayerOrNull(player)

                if (request != null) {

                    when {

                        visualKeyboardRunning -> {
                            TODO("If visual keyboard works, request here:")
                        }

                        else -> {

                            "§7Currently, you have an open request: '§e[title]§7'; Type answer in the chat and use [ENTER]!"
                                .replaceVariables("title" to request.message)
                                .notification(Transmission.Level.ATTENTION, request.holder)
                                .display()

                        }

                    }

                }

            }

        }
    }

    class RequestListener(override val vendor: App) : EventListener {

    }

    companion object {

        const val keyboardFeatureReady = false

        val visualKeyboardRunning: Boolean
            get() = JetFeature.VISUAL_KEYBOARD.isEnabled && keyboardFeatureReady

        fun <T : HumanEntity> getKeyboardRequestByPlayerOrNull(holder: T): Keyboard.KeyboardRequest<T>? =
            JetCache.keyboardRequests.firstOrNull { it.holder.uniqueId == holder.uniqueId }.forceCast()

    }

}