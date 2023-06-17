package dev.fruxz.sparkle.server.component.sandox

import dev.fruxz.sparkle.framework.command.annotations.Label
import dev.fruxz.sparkle.framework.command.context.CommandExecutionContext
import dev.fruxz.sparkle.framework.command.sparkle.BranchContent
import dev.fruxz.sparkle.framework.command.sparkle.Command

@Label("sandbox")
class SandBoxCommand : Command() {

    override fun configure() {

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

    }

}