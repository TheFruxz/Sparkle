package de.moltenKt.paper.app.component.app

import de.moltenKt.paper.structure.command.StructuredInterchange
import de.moltenKt.paper.structure.command.completion.buildInterchangeStructure

internal class AppInterchange : StructuredInterchange("app", buildInterchangeStructure {

    /*
    /app list
    /app stop <app|app#component>
    /app start <app|app#component>
    /app restart <app|app#component>
    /app cache <app> info|clear (<level>)
    /app info <app|app#component>
    */

    // TODO: 28.03.22 WIP

})