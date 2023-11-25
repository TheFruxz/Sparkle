package dev.fruxz.sparkle.server.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import dev.fruxz.brigadikt.tree.argumentInteger
import dev.fruxz.brigadikt.tree.argumentWord
import dev.fruxz.brigadikt.tree.route
import dev.fruxz.sparkle.framework.command.SparkleCommand
import dev.fruxz.sparkle.framework.command.annotations.Label
import dev.fruxz.sparkle.framework.command.buildCommand
import org.bukkit.command.CommandSender

@Label("demo")
class DemoCommand : SparkleCommand() {
    override val command: LiteralArgumentBuilder<CommandSender> = buildCommand("demo") {

        executes {
            source.sendMessage("Hello!")
        }

        route {
            val name = argumentWord("name")

            executes {
                source.sendMessage("Hello, ${name()}!")
            }

            route {
                val demo = argumentInteger("demo", 0, 10)
                executes {
                    source.sendMessage("Hello, ${name()}! You are demo number ${demo()}")
                }
            }

        }
    }
}