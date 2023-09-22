package dev.fruxz.sparkle.framework.ux.canvas

enum class CanvasFlag {

    NO_GRAB,
    NO_MOVE,
    NO_DRAG,
    NO_SWAP,
    NO_CLOSE,
    NO_OPEN,
    NO_UPDATE;

    companion object {

        val defaultProtection = setOf(NO_GRAB, NO_DRAG, NO_SWAP, NO_MOVE)

    }

}