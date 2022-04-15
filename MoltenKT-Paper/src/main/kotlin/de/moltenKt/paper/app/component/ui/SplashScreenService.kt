package de.moltenKt.paper.app.component.ui

import de.moltenKt.jvm.tool.smart.identification.Identifiable
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.extension.display.ui.buildPanel
import de.moltenKt.paper.extension.display.ui.item
import de.moltenKt.paper.extension.display.ui.set
import de.moltenKt.paper.extension.get
import de.moltenKt.paper.extension.lang
import de.moltenKt.paper.extension.system
import de.moltenKt.paper.extension.tasky.sync
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.structure.service.Service
import de.moltenKt.paper.tool.annotation.Prototype
import de.moltenKt.paper.tool.display.ui.panel.Panel.Companion.panelIdentity
import de.moltenKt.paper.tool.display.ui.panel.PanelFlag
import de.moltenKt.paper.tool.display.ui.panel.PanelFlag.NO_INTERACT
import de.moltenKt.paper.tool.timing.tasky.Tasky
import de.moltenKt.paper.tool.timing.tasky.TemporalAdvice
import de.moltenKt.unfold.text
import org.bukkit.Material
import org.bukkit.Material.GRAY_WOOL
import kotlin.time.Duration.Companion.seconds

internal class SplashScreenService(override val vendor: Identifiable<App> = system) : Service {

	override val temporalAdvice = TemporalAdvice.ticking(.5.seconds)

	override val thisIdentity = "SplashScreen"

	override val process: Tasky.() -> Unit = {

		MoltenCache.splashScreens.toList().forEach { (player, job) ->

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
				MoltenCache.splashScreens.remove(player)

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