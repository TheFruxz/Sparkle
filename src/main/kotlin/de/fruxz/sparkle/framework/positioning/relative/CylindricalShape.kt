package de.fruxz.sparkle.framework.positioning.relative

import de.fruxz.sparkle.framework.positioning.relative.Shape.Direction

interface CylindricalShape : Shape {

    val height: Double

    val radius: Double

    val direction: Direction

    companion object {

        fun of(height: Double, radius: Double, direction: Direction): CylindricalShape = object : CylindricalShape {

            override val height: Double = height

            override val radius: Double = radius

            override val direction: Direction = direction

            override val volume: Double = height * radius * radius * Math.PI

            override val fullHeight: Double = height

            override val fullWidth: Double = radius * 2.0

            override val fullDepth: Double = radius * 2.0

        }

    }

}