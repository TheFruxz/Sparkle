package dev.fruxz.sparkle.framework.ux.panel

fun buildPanel(process: MutablePanel.() -> Unit) : Panel =
    MutablePanel().apply(process).toPanel()