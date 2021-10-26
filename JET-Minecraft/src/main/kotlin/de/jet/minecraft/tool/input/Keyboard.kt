package de.jet.minecraft.tool.input

import de.jet.library.tool.smart.identification.Identifiable

object Keyboard {

    object KeyboardRenderer

    sealed class KeyboardState

    data class KeyboardExtension(
        val extensionName: String
    ): Identifiable<KeyboardExtension> {
        override val identity = extensionName
    }

}