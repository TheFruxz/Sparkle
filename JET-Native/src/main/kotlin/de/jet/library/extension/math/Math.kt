package de.jet.library.extension.math

import de.jet.library.tool.math.ModResult

infix fun Long.divideResult(divide: Long) = ModResult(this, divide)