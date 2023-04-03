package dev.fruxz.sparkle.framework

import dev.fruxz.sparkle.framework.command.Description
import dev.fruxz.sparkle.framework.command.Label
import dev.fruxz.sparkle.framework.command.Usage
import dev.fruxz.sparkle.framework.command.sparkle.BranchContent
import dev.fruxz.sparkle.framework.command.sparkle.Command

@Label("anothertest")
@Usage("anothertest")
@Description("Another Test Command")
class AnotherTestCommand : Command() {
    override fun configure() {
        execution {
            executor.sendMessage("Hello World!")
        }
        branch {
            content(BranchContent.int(1..10))
            execution {
                executor.sendMessage("Hello World! ${parameters[0]}")
            }
            branch {
                content("test", "lol")
                branch {
                    content("another", "one")
                    execution {
                        executor.sendMessage("Hello advanced World! ${parameters.joinToString()}")
                    }
                }
            }
        }
        branch {
            content(BranchContent.int(5..15))
            execution {
                executor.sendMessage("Another world!")
            }
        }
        branch {
            content("AmongUs", "Among Us")
            configureIgnoreCase()
            configureMultiWord()
            execution {
                executor.sendMessage("Your input: ${this.branchParameters.joinToString()}")
            }
            branch {
                content("is", "was", "are")
                configureIgnoreCase()
                branch {
                    content("a test", "a trap")
                    configureMultiWord()
                    execution {
                            executor.sendMessage("Your advanced input: ${this.branchParameters.joinToString()}")
                    }
                }
            }
        }
        branch {
            content("infinite")
            configureOpenEnd()
            execution {
                executor.sendMessage("Your infinite input: ${this.branchParameters.joinToString()}")
            }
        }
    }
}