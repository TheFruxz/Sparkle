package de.fruxz.sparkle.framework.util.positioning.relative

interface CubicalShape : Shape {

    val length: Double

    val depth: Double

    val height: Double

    companion object {

        fun of(length: Double, depth: Double, height: Double): CubicalShape = object : CubicalShape {

            override val length: Double = length

            override val depth: Double = depth

            override val height: Double = height

            override val volume: Double = length * depth * height

            override val fullHeight: Double = height

            override val fullWidth: Double = length

            override val fullDepth: Double = depth

        }

    }

}