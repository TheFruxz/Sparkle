package dev.fruxz.sparkle.server.command

import dev.fruxz.sparkle.framework.command.annotations.Label
import dev.fruxz.sparkle.framework.command.sparkle.Command
import dev.fruxz.sparkle.framework.modularity.component.ComponentManager
import dev.fruxz.sparkle.server.component.TestComponent

@Label("demo")
class DemoCmmand : Command() {

    override fun configure() {

        branch {
            content("test")
            execution {
                reply("Test")
            }
        }

        branch {
            content("disable")
            execution {
                reply("<b><i>DISABLING COMPONENT!")

                ComponentManager.unregister<TestComponent>()
            }
        }

    }

}