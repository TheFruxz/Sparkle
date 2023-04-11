package dev.fruxz.sparkle.framework

import dev.fruxz.sparkle.framework.command.Aliases
import dev.fruxz.sparkle.framework.command.Description
import dev.fruxz.sparkle.framework.command.Label
import dev.fruxz.sparkle.framework.command.Usage
import dev.fruxz.sparkle.framework.command.sparkle.BranchContent
import dev.fruxz.sparkle.framework.command.sparkle.Command
import org.bukkit.entity.Player

@Label("anothertest")
@Usage("anothertest")
@Description("Another Test Command")
@Aliases(["atc"])
class AnotherTestCommand : Command() {
    override fun configure() {
        execution {
            reply("Hello World!")
        }
        branch {
            content("produce-message")
            branch {
                configureMultiWord()
                configureIgnoreContent()
                content("message")
                branch {
                    content("print")
                    execution {
                        reply(parameters[parameters.lastIndex-1])
                    }
                }
            }
        }
        branch {
            content("spawn")
            branch {
                content(BranchContent.entityType())
                execution {
                    reply("Entity type: ${parameters[currentDepth]}")
                    (executor as? Player)?.world?.spawnEntity(executor.location, translate(BranchContent.entityType(), parameters[currentDepth]))
                }
            }
        }
        branch {
            content(BranchContent.int(1..10))
            execution {
                reply("Hello World! ${parameters[0]}")
            }
            branch {
                content("test", "lol")
                branch {
                    content("another", "one")
                    execution {
                        reply("Hello advanced World! ${parameters.joinToString()}")
                    }
                }
            }
        }
        branch {
            content(BranchContent.int(5..15))
            execution {
                reply("Another world!")
            }
        }
        branch {
            content("AmongUs", "Among Us")
            configureIgnoreCase()
            configureMultiWord()
            execution {
                reply("Your input: ${this.branchParameters}")
            }
            branch {
                content("is", "was", "are")
                configureIgnoreCase()
                branch {
                    content("a test", "a trap")
                    configureMultiWord()
                    execution {
                        reply("""
                            <white>The complete advanced input: '<yellow>${this.parameters}</yellow>'
                            <white>The advanced branch input: '<gold>${this.branchParameters}</gold>'
                        """.trimIndent())
                    }
                }
            }
        }
        branch {
            content("infinite")
            configureOpenEnd()
            configureMultiWord(true)
            execution {
                reply("Your infinite input: ${this.branchParameters}")
            }
        }
    }
}