package de.jet.paper.app

import de.jet.paper.structure.feature.Feature

object JetFeature {

    val values by lazy { arrayOf(
        VISUAL_KEYBOARD,
    ) }

    val VISUAL_KEYBOARD by lazy {
        Feature(
            JetApp.predictedIdentity,
            "Visual Keyboard",
            "A visual keyboard for the Minecraft client",
        )
    }

}