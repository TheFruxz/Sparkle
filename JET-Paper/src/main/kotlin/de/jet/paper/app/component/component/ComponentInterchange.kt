package de.jet.paper.app.component.component

import de.jet.paper.structure.command.StructuredInterchange
import de.jet.paper.structure.command.completion.buildInterchangeStructure

internal class ComponentInterchange : StructuredInterchange("component", protectedAccess = true, structure = buildInterchangeStructure {

    branch {
        addContent("list")
    }

    branch {
        addContent("start", "stop", "autostart")
    }

})