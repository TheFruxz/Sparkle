package de.jet.library.extension.display.ui

import de.jet.library.tool.display.ui.inventory.Container
import de.jet.library.tool.display.ui.panel.Panel

fun buildContainer(lines: Int = 3, action: Container.() -> Unit) = Container(size = lines*9).apply(action)

fun buildPanel(lines: Int = 3, action: Panel.() -> Unit) = Panel(lines = lines).apply(action)