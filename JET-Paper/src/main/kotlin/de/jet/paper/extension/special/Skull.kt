package de.jet.paper.extension.special

import de.jet.paper.tool.display.skull.SkullLibrary

/**
 * The skull with the id [identity] from your own skull database (CSV-Database), set in the
 * configuration file
 */
fun texturedSkull(identity: Int) = SkullLibrary.getSkull(identity)