package de.jet.minecraft.runtime.lang

import de.jet.library.tool.smart.positioning.Address
import java.nio.file.Path

data class LanguageData(val sourceFile: Path, val sourceAddress: Address<Path>, val indexedValue: String)
