package de.moltenKt.paper.extension.paper

import org.bukkit.block.BlockState
import org.bukkit.block.Sign

val BlockState.sign: Sign
    get() = this as Sign
