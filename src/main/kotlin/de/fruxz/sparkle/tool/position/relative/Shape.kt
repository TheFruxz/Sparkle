package de.fruxz.sparkle.tool.position.relative

import de.fruxz.ascend.annotation.NotPerfect
import de.fruxz.sparkle.extension.paper.add
import de.fruxz.sparkle.extension.paper.toSimpleLocation
import de.fruxz.sparkle.tool.position.dependent.DependentComplexShape
import de.fruxz.sparkle.tool.position.dependent.DependentCubicalShape
import de.fruxz.sparkle.tool.position.dependent.DependentCylindricalShape
import de.fruxz.sparkle.tool.position.dependent.DependentLinearShape
import de.fruxz.sparkle.tool.position.dependent.DependentPyramidalShape
import de.fruxz.sparkle.tool.position.dependent.DependentShape
import de.fruxz.sparkle.tool.position.dependent.DependentSphericalShape
import org.bukkit.Axis
import org.bukkit.Location
import kotlin.math.pow
import kotlin.math.sqrt

interface Shape {

    val volume: Double

    val fullHeight: Double

    val fullWidth: Double

    val fullDepth: Double

    fun getSize(axis: Axis): Double = when (axis) {
        Axis.X -> fullWidth
        Axis.Y -> fullHeight
        Axis.Z -> fullDepth
    }

    companion object {

        /**
         * This function creates a new [DependentCubicalShape] from the given parameters.
         * @param fromLocation the x&y&z start-location of the shape
         * @param toLocation the x&y&z end-location of the shape
         * @return The created [DependentCubicalShape]
         * @author Fruxz
         * @since 1.0
         */
        @JvmStatic
        fun cube(fromLocation: Location, toLocation: Location): DependentCubicalShape = DependentCubicalShape(fromLocation, toLocation)

        /**
         * This function creates a new [DependentCubicalShape] from the given parameters.
         * It uses the center and then stretches the shape to the given size.
         * @param center The center of the shape
         * @param size The size of the shape
         * @return The created [DependentCubicalShape]
         * @author Fruxz
         * @since 1.0
         */
        @JvmStatic
        fun cube(center: Location, size: Double) = cube(center.clone().subtract(size / 2, size / 2, size / 2), center.clone().add(size / 2, size / 2, size / 2))

        /**
         * This function creates a new [CubicalShape] from the given parameters.
         * Used for objects, not bound to a specific location.
         * @param width The width of the shape
         * @param height The height of the shape
         * @param depth The depth of the shape
         * @return The created [CubicalShape]
         * @author Fruxz
         * @since 1.0
         */
        @JvmStatic
        fun independentCube(width: Double, depth: Double, height: Double) = CubicalShape.of(
            length = width,
            depth = depth,
            height = height,
        )

        /**
         * This function creates a new [DependentSphericalShape] from the given parameters.
         * @param center The center of the shape
         * @param radius The radius of the shape
         * @return The created [DependentSphericalShape]
         * @author Fruxz
         * @since 1.0
         */
        @JvmStatic
        fun sphere(center: Location, radius: Double) = DependentSphericalShape(center, radius)

        /**
         * This function creates a new [SphereShape] from the given parameters.
         * Used for objects, not bound to a specific location.
         * @param radius The radius of the shape
         * @return The created [SphereShape]
         * @author Fruxz
         * @since 1.0
         */
        @JvmStatic
        fun independentSphere(radius: Double) = SphereShape.of(radius)

        /**
         * This function creates a new [DependentCylindricalShape] from the given parameters.
         * The location is the center of the bottom surface of the shape.
         * @param center The center of the shape
         * @param height The height of the shape
         * @param radius The radius of the shape
         * @param direction The line on which the shapes' height is based
         * @return The created [DependentCylindricalShape]
         * @author Fruxz
         * @since 1.0
         */
        @JvmStatic
        fun cylinder(center: Location, height: Double, radius: Double, direction: Direction = Direction.Y) = DependentCylindricalShape(center.toSimpleLocation(), direction, height, radius)

        /**
         * This function creates a new [CylindricalShape] from the given parameters.
         * Used for objects, not bound to a specific location.
         * @param height The height of the shape
         * @param radius The radius of the shape
         * @param direction The line on which the shapes' height is based
         * @return The created [CylindricalShape]
         * @author Fruxz
         * @since 1.0
         */
        @JvmStatic
        fun independentCylinder(height: Double, radius: Double, direction: Direction = Direction.Y) = CylindricalShape.of(height, radius, direction)

        /**
         * This function creates a new [DependentPyramidalShape] from the given parameters.
         * The location is the peak of the shape.
         * @param peak The peak of the shape
         * @param height The height of the shape
         * @param groundWidth The size of the bottom surface of the shape (x)
         * @param groundDepth The size of the bottom surface of the shape (z)
         * @return The created [DependentPyramidalShape]
         * @author Fruxz
         * @since 1.0
         */
        @JvmStatic
        @NotPerfect
        fun pyramid(peak: Location, height: Double, groundWidth: Double, groundDepth: Double) = DependentPyramidalShape(peak.toSimpleLocation(), height, groundWidth, groundDepth)

        /**
         * This function creates a new [DependentPyramidalShape] from the given parameters.
         * The location is the peak of the shape.
         * @param peak The peak of the shape
         * @param height The height of the shape
         * @param groundSize The size of the bottom surface of the shape (x&z)
         * @return The created [DependentPyramidalShape]
         * @author Fruxz
         * @since 1.0
         */
        @JvmStatic
        @NotPerfect
        fun pyramid(peak: Location, height: Double, groundSize: Double) = pyramid(peak, height, groundSize, groundSize)

        /**
         * This function creates a new [DependentPyramidalShape] from the given parameters.
         * The location is the peak of the shape.
         * @param peak The peak of the shape
         * @param height The height of the shape and the size of the bottom surface of the shape (x&z)
         * @return The created [DependentPyramidalShape]
         * @author Fruxz
         * @since 1.0
         */
        @JvmStatic
        @NotPerfect
        fun pyramid(peak: Location, height: Double) = pyramid(peak, height, height)

        /**
         * This function creates a new [DependentPyramidalShape] from the given parameters.
         * The location is the peak of the shape.
         * @param peak The peak of the shape
         * @param groundWidth The size of the bottom surface of the shape (x)
         * @param groundDepth The size of the bottom surface of the shape (z)
         * @return The created [DependentPyramidalShape]
         * @author Fruxz
         * @since 1.0
         */
        @JvmStatic
        @NotPerfect
        fun pyramidAutoHeight(peak: Location, groundWidth: Double, groundDepth: Double) = sqrt(groundWidth.pow(2.0) + groundDepth.pow(2.0)).let { height ->
            pyramid(peak.add(y = height), height, groundWidth, groundDepth)
        }

        /**
         * This function creates a new [PyramidalShape] from the given parameters.
         * Used for objects, not bound to a specific location.
         * @param height The height of the shape
         * @param groundWidth The size of the bottom surface of the shape (x)
         * @param groundDepth The size of the bottom surface of the shape (z)
         * @return The created [PyramidalShape]
         * @author Fruxz
         * @since 1.0
         */
        @JvmStatic
        @NotPerfect
        fun independentPyramid(height: Double, groundWidth: Double, groundDepth: Double) = PyramidalShape.of(height, groundWidth, groundDepth)

        /**
         * This function creates a new [DependentLinearShape] from the given parameters.
         * This is a line without any volume.
         * @param start The start of the line
         * @param end The end of the line
         * @return The created [DependentLinearShape]
         * @author Fruxz
         * @since 1.0
         */
        @JvmStatic
        fun line(start: Location, end: Location) = DependentLinearShape(start.toSimpleLocation(), end.toSimpleLocation())

        /**
         * This function creates a new [LinearShape] from the given parameters.
         * Used for objects, not bound to a specific location.
         * @param length The length of the line
         * @return The created [LinearShape]
         * @author Fruxz
         * @since 1.0
         */
        @JvmStatic
        fun independentLine(length: Double) = LinearShape.of(length)

        /**
         * This function creates a new [DependentComplexShape] containing all the given [dependentShapes].
         * @param dependentShapes The shapes to add to the complex shape
         * @return The created [DependentComplexShape]
         * @author Fruxz
         * @since 1.0
         */
        @JvmStatic
        fun complex(vararg dependentShapes: DependentShape) = DependentComplexShape(dependentShapes.toList())

    }

    enum class Direction {

        X, Y, Z;

    }

}