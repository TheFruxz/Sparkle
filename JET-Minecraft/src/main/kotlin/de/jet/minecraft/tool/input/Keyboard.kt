package de.jet.minecraft.tool.input

import de.jet.library.tool.smart.identification.Identifiable
import de.jet.minecraft.tool.display.item.Item

fun requestKeyboardInput(keyboard: Keyboard.RenderedKeyboard) = Keyboard.KeyboardRequest(
    keyboard, {}, {}
)

object Keyboard {

    object Engine {

        fun renderKeyboard(keyboardMode: KeyboardMode = KeyboardMode.ANY, vararg extensions: KeyboardExtension): RenderedKeyboard {
            return RenderedKeyboard()
        }

    }

    class KeyboardRequest internal constructor(
        private val keyboard: RenderedKeyboard,
        onAnswerReceive: (String) -> Unit,
        onCancelReceive: () -> Unit,
    ) {

        interface RequestResult {
            
        }

        var onCancelReceive = onCancelReceive
            private set

        var onAnswerReceive = onAnswerReceive
            private set

        fun onReceiveAnswer(process: (String) -> Unit) = apply {
            this.onAnswerReceive = process
        }

        fun onReceiveCancel(process: () -> Unit) = apply {
            this.onCancelReceive = process
        }

    }

    enum class KeyboardMode {
        STRING, INT, DOUBLE, MESSAGE, ANY;
    }

    class RenderedKeyboard internal constructor()

    sealed class KeyboardState {

        fun addExtensionButton(button: KeyboardExtensionButton) {

        }

    }

    data class KeyboardExtensionButton(
        val button: Item
    )

    data class KeyboardExtension(
        val extensionName: String,
        val extensionIcon: Item,
        val onLoad: KeyboardExtension.() -> Unit
    ): Identifiable<KeyboardExtension> {
        override val identity = extensionName
    }

}