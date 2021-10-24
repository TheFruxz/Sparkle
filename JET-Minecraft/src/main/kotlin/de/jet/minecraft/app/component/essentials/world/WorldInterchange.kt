package de.jet.minecraft.app.component.essentials.world

import de.jet.library.extension.switchResult
import de.jet.library.extension.tag.PromisingData
import de.jet.minecraft.app.component.essentials.world.WorldInterchange.WorldPanelViewProperties.ViewType.*
import de.jet.minecraft.app.component.essentials.world.tree.WorldTree
import de.jet.minecraft.app.component.essentials.world.tree.WorldTree.RenderFolder
import de.jet.minecraft.app.component.essentials.world.tree.WorldTree.RenderObject
import de.jet.minecraft.app.component.essentials.world.tree.WorldTree.RenderWorld
import de.jet.minecraft.extension.display.ui.buildPanel
import de.jet.minecraft.extension.display.ui.item
import de.jet.minecraft.extension.display.ui.skull
import de.jet.minecraft.extension.paper.displayString
import de.jet.minecraft.extension.special.texturedSkull
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.command.Interchange
import de.jet.minecraft.structure.command.InterchangeResult.SUCCESS
import de.jet.minecraft.tool.display.item.Item
import de.jet.minecraft.tool.display.ui.panel.PanelFlag.*
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType.*

class WorldInterchange(vendor: App) : Interchange(vendor, "world", requiresAuthorization = true) {

	data class WorldPanelViewProperties(
		val path: String = "/",
		val viewType: ViewType = ALL,
		val page: Int = 1,
		val searchKey: String? = null,
		val searchFilters: List<Filter> = emptyList(),
	) : PromisingData {

		enum class ViewType {
			ALL, DIRECTORIES, WORLDS;
		}

		enum class Filter {
			NAME, LABEL, VISITOR;
		}

	}

	private fun renderItem(view: WorldPanelViewProperties, renderObject: RenderObject): Item {
		return when (renderObject) {
			is RenderWorld -> texturedSkull(32442).apply {
				val world = Bukkit.getWorld(identity)

				identity = renderObject.identity

				if (world != null) {
					label = "§6${renderObject.displayName}"
					lore = buildString {
						appendLine("§7Identity: §e${renderObject.identity}")
						appendLine("§7Path: §e${renderObject.address.address}")
						appendLine()
						appendLine("§7Status: §aRUNNING")
						appendLine("§7Labels: §6${renderObject.labels.joinToString()}")
						appendLine("§7Archive: §6${renderObject.archived.switchResult("§bArchived", "§aAvailable")}")
						appendLine("§7Now playing: §6${world.playerCount}")
						appendLine("§7Spawn-Point: §6${world.spawnLocation.displayString()}")
						appendLine("§7Difficulty: §6${world.difficulty.name}")
						appendLine("§7Visitors: §6${renderObject.visitors.joinToString()}")
						appendLine("")
						appendLine()
						appendLine("§a§lLEFT-CLICK§7 - teleport to this world")
						appendLine("§a§lMIDDLE-CLICK§7 - unload this world")
						appendLine("§a§lRIGHT-CLICK§7 - edit this world")
					}
				} else {
					label = "§7${renderObject.displayName}"
					lore = buildString {
						appendLine("§7Identity: §e${renderObject.identity}")
						appendLine("§7Path: §e${renderObject.address.address}")
						appendLine()
						appendLine("§6Status: §cOFFLINE")
						appendLine("§7Labels: §6${renderObject.labels.joinToString()}")
						appendLine("§7Archive: §6${renderObject.archived.switchResult("§bArchived", "§aAvailable")}")
						appendLine("§7Visitors: §6${renderObject.visitors.joinToString()}")
						appendLine("")
						appendLine()
						appendLine("§a§lLEFT-CLICK§7 - load this world")
						appendLine("§a§lRIGHT-CLICK§7 - edit this world")
					}
				}

			}
			is RenderFolder -> (if (renderObject.archived) {
				texturedSkull(4465)
			} else
				texturedSkull(4459)).apply {

				label = "§3Directory: ${renderObject.displayName}e"
				identity = renderObject.identity
				lore = buildString {
					appendLine("§7Identity: §e${renderObject.identity}")
					appendLine("§7Path: §e${renderObject.address.address}")
					appendLine()
					appendLine("§7Labels: §6${renderObject.labels.joinToString()}")
					appendLine("§7Status: §6${renderObject.archived.switchResult("§bArchived", "§aAvailable")}")
					appendLine()
					appendLine("§a§lLEFT-CLICK§7 - open this directory")
					appendLine("§a§lRIGHT-CLICK§7 - edit this directory")
				}

				putClickAction {
					when (click) {
						LEFT, SHIFT_LEFT -> {
							displayPanel(whoClicked, view.copy(path = renderObject.address.address))
						}
						RIGHT, SHIFT_RIGHT -> {
							whoClicked.sendMessage("edit")
						}
						else -> {
							whoClicked.sendMessage("nothing")
						}
					}
				}

			}
			else -> throw NullPointerException()
		}

	}

	val panel = buildPanel(lines = 5) {

		set(38, texturedSkull(9885).apply {
			label = "Create"
		})

		set(42, texturedSkull(46454).apply {
			label = "Import"
		})

	}.onReceive {
		assert(it.receiveParameters["view"] != null) { "view-property not exist!" }
		val view = it.receiveParameters["view"] as WorldPanelViewProperties
		val viewContent = WorldTree.renderOverview(view.path).let {
			return@let when (view.viewType) {
				ALL -> it.renderFolders + it.renderWorlds
				DIRECTORIES -> it.renderFolders
				WORLDS -> it.renderWorlds
			}
		}

		panelFlags = setOf(NOT_CLICK_ABLE, NOT_MOVE_ABLE, NOT_DRAG_ABLE)

		innerSlots.forEach { innerSlot ->
			val viewContentSlot = (innerSlot + (innerSlots.last * (view.page - 1)))
			val item = viewContent.getOrNull(viewContentSlot)

			if (item != null) {
				placeInner(innerSlot, renderItem(view, item))
			}

		}

		set(18, skull("MHF_ArrowLeft").blankLabel().putClickAction {
			displayPanel(whoClicked, "view" to view.copy(page = view.page - 1))
		})

		set(26, skull("MHF_ArrowRight").blankLabel().putClickAction {
			displayPanel(whoClicked, "view" to view.copy(page = view.page + 1))
		})

		set(6, Material.PAPER.item.apply {
			label = "Seite ${view.page}"
			size = view.page
		})

		set(40, Material.HOPPER.item.apply {
			label = "Filter"
		})

		this
	}

	fun displayPanel(receiver: HumanEntity, vararg parameters: Pair<String, Any>) {
		panel.display(receiver, parameters.toMap())
	}

	fun displayPanel(receiver: HumanEntity, viewProperties: WorldPanelViewProperties) =
		displayPanel(receiver, "view" to viewProperties)

	override val execution = execution {

		panel.display(executor as Player, mapOf("view" to WorldPanelViewProperties()))

		SUCCESS
	}

}