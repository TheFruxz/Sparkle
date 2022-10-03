package de.fruxz.sparkle.framework.util.positioning.relative

import kotlin.math.pow

interface SphereShape : Shape {

    val radius: Double

    companion object {

        fun of(radius: Double): SphereShape = object : SphereShape {

            override val radius: Double = radius

            override val volume: Double = 4.0 / 3.0 * Math.PI * radius.pow(3)

            override val fullHeight: Double = radius * 2.0

            override val fullWidth: Double = radius * 2.0

            override val fullDepth: Double = radius * 2.0

        }

    }

}