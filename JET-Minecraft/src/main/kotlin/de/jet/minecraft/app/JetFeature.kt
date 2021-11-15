package de.jet.minecraft.app

import de.jet.minecraft.structure.feature.Feature

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