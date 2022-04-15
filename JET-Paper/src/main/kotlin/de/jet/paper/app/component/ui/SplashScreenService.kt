package de.jet.paper.app.component.ui

import de.jet.jvm.tool.smart.identification.Identifiable
import de.jet.paper.app.JetCache
import de.jet.paper.extension.display.ui.buildPanel
import de.jet.paper.extension.display.ui.item
import de.jet.paper.extension.display.ui.set
import de.jet.paper.extension.get
import de.jet.paper.extension.lang
import de.jet.paper.extension.system
import de.jet.paper.extension.tasky.sync
import de.jet.paper.structure.app.App
import de.jet.paper.structure.service.Service
import de.jet.paper.tool.annotation.Prototype
import de.jet.paper.tool.display.ui.panel.Panel.Companion.panelIdentity
import de.jet.paper.tool.display.ui.panel.PanelFlag
import de.jet.paper.tool.display.ui.panel.PanelFlag.NO_INTERACT
import de.jet.paper.tool.timing.tasky.Tasky
import de.jet.paper.tool.timing.tasky.TemporalAdvice
import de.jet.unfold.text
import org.bukkit.Material
import org.bukkit.Material.GRAY_WOOL
import kotlin.time.Duration.Companion.seconds

internal class SplashScreenService(override val vendor: Identifiable<App> = system) : Service {

	override val temporalAdvice = TemporalAdvice.ticking(.5.seconds)

	override val thisIdentity = "SplashScreen"

	override val process: Tasky.() -> Unit = {

		JetCache.splashScreens.toList().forEach { (player, job) ->

			if (!job.isCompleted) {

				val targetInventory = player.openInventory.topInventory.takeIf { it.panelIdentity?.identity == splashScreenPanel.identity }
					?: splashScreenPanel.produceState().also { player.openInventory(it) }

				for ((index, slot) in (21..23).withIndex()) {

					val material = if ((attempt % 4).toInt() == index || (index == 1 && attempt % 4 == 3L)) Material.LIGHT_GRAY_WOOL else GRAY_WOOL

					sync(TemporalAdvice.delayed(.2.seconds)) {
						targetInventory[slot] = material.item {
							label = text(lang["splashscreen.loading"])
						}
					}

				}

			} else
				JetCache.splashScreens.remove(player)

		}

	}

	companion object {

		val splashScreenPanel = buildPanel(5) {

			label = text(lang["splashscreen.loading"])

			icon = Material.CLOCK.item

			@OptIn(Prototype::class)
			useSplashScreen(false)

			placeInner(9..11, GRAY_WOOL.item.blankLabel())

			addPanelFlags(PanelFlag.NO_GRAB, NO_INTERACT)

			complete()

		}

	}

}