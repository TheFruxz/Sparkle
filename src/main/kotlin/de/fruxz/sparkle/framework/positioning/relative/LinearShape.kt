package de.fruxz.sparkle.framework.positioning.relative

interface LinearShape : Shape {

    val length: Double

    companion object {

        fun of(length: Double): LinearShape = object : LinearShape {

            override val length: Double = length

            override val volume: Double = .0

            override val fullHeight: Double = length / 3

            override val fullWidth: Double = length / 3

            override val fullDepth: Double = length / 3

        }

    }

}