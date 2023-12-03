package dev.fruxz.sparkle.server.component.sandox

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import dev.fruxz.brigadikt.tree.argument
import dev.fruxz.brigadikt.tree.route
import dev.fruxz.sparkle.framework.command.SparkleCommand
import dev.fruxz.sparkle.framework.command.annotations.Label
import dev.fruxz.sparkle.framework.command.argument.ArgumentStore
import dev.fruxz.sparkle.framework.command.buildCommand
import dev.fruxz.sparkle.framework.coroutine.task.doSync
import org.bukkit.command.CommandSender

@Label("sandbox")
class SandBoxCommand : SparkleCommand() {

    /*override fun configure() {

        branch(BranchContent.sandbox()) {
            // TODO val input by argument("input", "The input to echo")?
            branch("run") {
                configureOpenEnd() // TODO if RUN is not specified (not enough arguments) then show help (which is currently not implemented)
                execution {
                    val sandbox = getReversed(BranchContent.sandbox(), 1)

                    println("sandbox: ${sandbox.label}")

                    sandbox(CommandExecutionContext(performer, command, label, parameters.drop(2)))

                }
            }
        }

    }*/
    override val command: LiteralArgumentBuilder<CommandSender> = buildCommand("sandbox") {
        route {
            val sandbox = argument("sandbox", ArgumentStore.SANDBOX)

            executes {

                doSync {
                    sandbox().invoke(this) // TODO retrieve argument and /<> run in next release
                }

            }

        }
    }


}