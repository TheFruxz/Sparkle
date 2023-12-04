package dev.fruxz.sparkle.server.component.sandox

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import dev.fruxz.brigadikt.tree.argument
import dev.fruxz.brigadikt.tree.argumentGreedyString
import dev.fruxz.brigadikt.tree.route
import dev.fruxz.sparkle.framework.command.SparkleCommand
import dev.fruxz.sparkle.framework.command.annotations.Label
import dev.fruxz.sparkle.framework.command.argument.ArgumentStore
import dev.fruxz.sparkle.framework.command.buildCommand
import dev.fruxz.sparkle.framework.coroutine.task.doSync
import dev.fruxz.sparkle.framework.ux.messaging.transmission
import dev.fruxz.stacked.plus
import dev.fruxz.stacked.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import kotlin.time.measureTime

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

            route("run") {

                executes {

                    text {
                        this + text("Running sandbox '").color(NamedTextColor.GRAY)
                        this + text(sandbox().label).color(NamedTextColor.YELLOW)
                        this + text("'...").color(NamedTextColor.GRAY)
                    }.transmission(source).display()

                    doSync {

                        val time = measureTime {
                            sandbox().invoke(SandBoxCommandContext(sandbox(), source, SandBoxManager.SandBoxInput.EMPTY))
                        }

                        text {
                            this + text("✔").color(NamedTextColor.GREEN)
                            this + text(" Execution succeed in ").color(NamedTextColor.GRAY)
                            this + text(time.toString()).color(NamedTextColor.DARK_GREEN)
                            this + text("!").color(NamedTextColor.GRAY)
                        }.transmission(source).display()

                    }

                }

                route {
                    val data = argumentGreedyString("data")

                    executes {

                        text {
                            this + text("Running sandbox '").color(NamedTextColor.GRAY)
                            this + text(sandbox().label).color(NamedTextColor.YELLOW)
                            this + text("' with data '").color(NamedTextColor.GRAY)
                            this + text(data()).color(NamedTextColor.AQUA)
                            this + text("'...").color(NamedTextColor.GRAY)
                        }.transmission(source).display()

                        doSync {

                            val time = measureTime {
                                sandbox().invoke(SandBoxCommandContext(sandbox(), source, SandBoxManager.SandBoxInput(data())))
                            }

                            text {
                                this + text("✔").color(NamedTextColor.GREEN)
                                this + text(" Execution succeed in ").color(NamedTextColor.GRAY)
                                this + text(time.toString()).color(NamedTextColor.DARK_GREEN)
                                this + text("!").color(NamedTextColor.GRAY)
                            }.transmission(source).display()

                        }
                    }

                }

            }

            route("drop") {
                executes {
                    SandBoxManager -= sandbox().label

                    text {
                        this + text("Successfully ").color(NamedTextColor.GREEN)
                        this + text("dropped sandbox ").color(NamedTextColor.GRAY)
                        this + text(sandbox().label).color(NamedTextColor.YELLOW)
                        this + text("!").color(NamedTextColor.GRAY)
                    }.transmission(source).display()

                }
            }



        }

    }


}