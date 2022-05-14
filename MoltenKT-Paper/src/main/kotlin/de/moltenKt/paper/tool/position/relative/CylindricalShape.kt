package de.moltenKt.paper.tool.position.relative

interface CylindricalShape : Shape {

    val height: Double

    val radius: Double

    val direction: Shape.Direction

    companion object {

        fun of(height: Double, radius: Double, direction: Shape.Direction): CylindricalShape = object : CylindricalShape {

            override val height: Double = height

            override val radius: Double = radius

            override val direction: Shape.Direction = direction

            override val volume: Double = height * radius * radius * Math.PI

            override val fullHeight: Double = height

            override val fullWidth: Double = radius * 2.0

            override val fullDepth: Double = radius * 2.0

        }

    }

}