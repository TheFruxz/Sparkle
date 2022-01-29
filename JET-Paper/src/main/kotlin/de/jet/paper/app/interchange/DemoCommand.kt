package de.jet.paper.app.interchange

import de.jet.paper.structure.command.completion.buildCompletion
import de.jet.paper.structure.command.completion.component.CompletionAsset
import de.jet.paper.structure.command.completion.component.CompletionComponent
import de.jet.paper.structure.command.completion.component.CompletionComponent.Companion
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class DemoCommand : CommandExecutor {

	val completion = buildCompletion {
		branch {
			addContent(CompletionComponent.asset(CompletionAsset.ENTITY_TYPE))
			addContent(Companion.asset(CompletionAsset.ONLINE_PLAYER_UUID))
			branch {
				addContent(Companion.asset(CompletionAsset.INTERCHANGE))
			}
		}
		branch {
			addContent(CompletionComponent.asset(CompletionAsset.WORLD_NAME))
			branch {
				addContent(CompletionComponent.asset(CompletionAsset.ENTITY_TYPE))
			}
		}
		branch {
			addContent(Companion.asset(CompletionAsset.LONG))
			branch {
				addContent(Companion.asset(CompletionAsset.DOUBLE))
				branch {
					addContent(Companion.asset(CompletionAsset.ONLINE_PLAYER_NAME))
				}
			}
		}
	}

	override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
		TODO("Not yet implemented")
	}

	val tabCompleter = TabCompleter { _, _, _, args ->
		println("args: (${args.size})${args.toList()}")
		completion.computeCompletion(args.toList())
	}

}