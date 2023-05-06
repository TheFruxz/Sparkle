package dev.fruxz.sparkle.framework.modularity.component

enum class StartupBehavior {
    /**
     * # Behavior
     * Always on, no matter what
     *
     * # Example Use-case
     * Required for the server/plugin to function
     */
    ALWAYS_ON,

    /**
     * # Behavior
     * Can be toggled on and off, but always autostarts on host-start
     *
     * # Example Use-case
     * A component that is not required for the server/plugin to function, but is heavily recommended
     * or required for host-start processes
     */
    ALWAYS_AUTOSTART,

    /**
     * # Behavior
     * Can be toggled on and off, by default autostarts on host-start, but can be changed
     *
     * # Example Use-case
     * A component that is not required for the server/plugin to function, but is recommended
     */
    DEFAULT_AUTOSTART,

    /**
     * # Behavior
     * Can be toggled on and off, but does not autostart on host-start
     *
     * # Example Use-case
     * A component that is not required for the server/plugin to function and have to be enabled manually each time
     */
    DEFAULT_OFF;

    val defaultIsAutoStart: Boolean
        get() = this != DEFAULT_OFF

}