package de.moltenKt.paper.tool.position.relative

interface PyramidalShape : Shape{

    val height: Double

    val groundWidth: Double

    val groundDepth: Double

    companion object {

        fun of(height: Double, groundWidth: Double, groundDepth: Double): PyramidalShape = object : PyramidalShape {

            override val height: Double = height

            override val groundWidth: Double = groundWidth

            override val groundDepth: Double = groundDepth

            override val volume: Double = (1.0/3) * height * groundWidth * groundDepth

            override val fullHeight: Double = height

            override val fullWidth: Double = groundWidth

            override val fullDepth: Double = groundDepth

        }

    }

}