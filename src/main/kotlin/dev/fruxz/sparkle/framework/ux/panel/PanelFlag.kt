package dev.fruxz.sparkle.framework.ux.panel

enum class PanelFlag {

    NO_GRAB,
    NO_DRAG,
    NO_MOVE,
    NO_SWAP,
    NO_OPEN,
    NO_REFRESH,
    NO_CLOSE,

    /**
     * Instead of processing each panel action one by one, process them all at once in an async context.
     */
    PARALLEL_ACTION_PROCESSING,

    /**
     * If actions should be possible to track, even if not in the inventory provided by the panel. (e.g. player inventory)
     */
    DETECT_OUTSIDE_CLICKS;

    companion object {

        val defaultProtection = setOf(NO_GRAB, NO_DRAG, NO_MOVE, NO_SWAP)

    }

}