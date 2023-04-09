package dev.fruxz.sparkle.framework

import dev.fruxz.sparkle.framework.command.Aliases
import dev.fruxz.sparkle.framework.command.Description
import dev.fruxz.sparkle.framework.command.Label
import dev.fruxz.sparkle.framework.command.Usage
import dev.fruxz.sparkle.framework.command.sparkle.BranchContent
import dev.fruxz.sparkle.framework.command.sparkle.Command

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