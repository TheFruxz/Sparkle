package dev.fruxz.sparkle.framework.ux.panel

import dev.fruxz.sparkle.framework.ux.effect.sound.SoundEffect

data class PanelSound(
    val onOpen: SoundEffect? = null,
    val onClose: SoundEffect? = null,
)
