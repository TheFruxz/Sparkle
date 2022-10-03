package de.fruxz.sparkle.framework.infrastructure

import de.fruxz.sparkle.framework.util.identification.KeyedIdentifiable
import de.fruxz.sparkle.framework.util.identification.Labeled

interface Hoster<A, B, C : Hoster<A, B, C>> : KeyedIdentifiable<C>, Labeled {

    fun requestStart(): A

    fun requestStop(): B

}