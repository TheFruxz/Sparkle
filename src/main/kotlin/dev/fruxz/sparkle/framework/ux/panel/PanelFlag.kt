package dev.fruxz.sparkle.framework.ux.panel

enum class PanelFlag {

    NO_MOVE,
    NO_CLICK,
    NO_REFRESH,
    NO_OPEN,

    /**
     * During a refresh, instead of merging the changes into the current inventory, it will open a newly generated one
     */
    HARD_REFRESH,

    /**
     * Instead of processing each panel action one by one, process them all at once in an async context.
     */
    PARALLEL_ACTION_PROCESSING,

    /**
     * If actions should be possible to track, even if not in the inventory provided by the panel (e.g., player inventory)
     */
    DETECT_OUTSIDE_CLICKS,

    /**
     * If a slot is clicked with an action, it will not automatically cancel the event, to perform the action.
     */
    ACTION_NO_CANCEL;

    companion object {

        val defaultProtection = setOf(NO_MOVE, NO_CLICK)

    }

}