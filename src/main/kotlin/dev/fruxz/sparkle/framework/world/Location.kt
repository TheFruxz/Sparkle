package dev.fruxz.sparkle.framework.world

import dev.fruxz.ascend.extension.dump
import dev.fruxz.ascend.extension.math.shorter
import dev.fruxz.sparkle.framework.system.world
import dev.fruxz.sparkle.framework.system.worldOrNull
import dev.fruxz.sparkle.framework.world.location.LazyLocation
import dev.fruxz.stacked.extension.dyeAqua
import dev.fruxz.stacked.extension.dyeDarkAqua
import dev.fruxz.stacked.extension.dyeGray
import dev.fruxz.stacked.extension.style
import dev.fruxz.stacked.hover
import dev.fruxz.stacked.plus
import dev.fruxz.stacked.text
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.util.Vector

/**
 * This function creates a copy of [this] [Location],
 * its parameters are filled with [this] data by default,
 * but can be changed on demand, like the copy functions
 * of data-classes.
 * @author Fruxz
 * @since 1.0
 */
fun Location.copy(
    world: World = this.world,
    x: Number = this.x,
    y: Number = this.y,
    z: Number = this.z,
    yaw: Number = this.yaw,
    pitch: Number = this.pitch,
) = Location(
    /* world = */ world,
    /* x = */ x.toDouble(),
    /* y = */ y.toDouble(),
    /* z = */ z.toDouble(),
    /* yaw = */ yaw.toFloat(),
    /* pitch = */ pitch.toFloat()
)

/**
 * This function creates a new lazy location object,
 * with this location data.
 * @author Fruxz
 * @since 1.0
 */
fun Location.lazy(
    world: String = this.world.name,
    x: Double = this.x,
    y: Double = this.y,
    z: Double = this.z,
    yaw: Float = this.yaw,
    pitch: Float = this.pitch
) = LazyLocation(world, x, y, z, yaw, pitch)

// constructor

/**
 * This function / pseudo-constructor creates a new [Location],
 * but only provided with a [worldName] and the coordinates
 * defined as general numbers.
 * [x], [y] and [z] are then converted into [Double]s using [Number.toDouble]
 * and [yaw] and [pitch] are converted into [Float]s using [Number.toFloat]
 * The conversion of the numbers, are strictly tied to the [toDouble] and [toFloat]
 * functions, so they rule the conversion processes.
 * @author Fruxz
 * @since 1.0
 */
fun Location(
    worldName: String,
    x: Number,
    y: Number,
    z: Number,
    yaw: Number = 0F,
    pitch: Number = 0F,
) = Location(world(worldName), x.toDouble(), y.toDouble(), z.toDouble(), yaw.toFloat(), pitch.toFloat())

fun location(
    world: World,
    x: Number,
    y: Number,
    z: Number,
    yaw: Number = 0,
    pitch: Number = 0,
) = Location(world, x.toDouble(), y.toDouble(), z.toDouble(), yaw.toFloat(), pitch.toFloat())

fun location(
    worldName: String,
    x: Number,
    y: Number,
    z: Number,
    yaw: Number = 0,
    pitch: Number = 0,
) = Location(worldName, x, y, z, yaw, pitch)

// location math
// // with vector
/**
 * This function adds the [Vector.x], [Vector.y] and [Vector.z] to
 * the [Location.copy] of [this] location.
 * @return the modified copy of [this]
 * @author Fruxz
 * @since 1.0
 */
operator fun Location.plus(vector: Vector) = this.copy(x = x + vector.x, y = y + vector.y, z = z + vector.z)

/**
 * This function adds the [Vector.x], [Vector.y] and [Vector.z] to [this] location.
 * @author Fruxz
 * @since 1.0
 */
operator fun Location.plusAssign(vector: Vector) = this.add(vector).dump()

/**
 * This function takes the [Vector.x], [Vector.y] and [Vector.z] from
 * the [Location.copy] of [this] location.
 * @return the modified copy of [this]
 * @author Fruxz
 * @since 1.0
 */
operator fun Location.minus(vector: Vector) = this.copy(x = x - vector.x, y = y - vector.y, z = z - vector.z)

/**
 * This function takes the [Vector.x], [Vector.y] and [Vector.z] from [this] location.
 * @author Fruxz
 * @since 1.0
 */
operator fun Location.minusAssign(vector: Vector) = this.subtract(vector).dump()

// // with numbers

/**
 * This function adds the given [x], [y] and [z] values to the current [Location].
 * @param x the x-coordinate to add
 * @param y the y-coordinate to add
 * @param z the z-coordinate to add
 * @return the modified Location-object
 * @author Fruxz
 * @since 1.0
 */
fun Location.add(x: Number = 0, y: Number = 0, z: Number = 0) =
    add(x.toDouble(), y.toDouble(), z.toDouble())

/**
 * This function subtracts the given [x], [y] and [z] values from the current [Location].
 * @param x the x-coordinate to subtract
 * @param y the y-coordinate to subtract
 * @param z the z-coordinate to subtract
 * @return the modified Location-object
 * @author Fruxz
 * @since 1.0
 */
fun Location.subtract(x: Number = 0, y: Number = 0, z: Number = 0) =
    subtract(x.toDouble(), y.toDouble(), z.toDouble())

// // with increase/decrease

/**
 * This function returns a copy of [this] [Location],
 * but [amount] block above (y += [amount]).
 * This function utilizes the [Location.copy] function.
 * @return copy of location, [amount] higher
 * @author Fruxz
 * @since 1.0
 */
fun Location.higher(amount: Int = 1) = copy(y = y + amount)

/**
 * This function returns a copy of [this] [Location],
 * but [amount] block below (y -= [amount]).
 * This function utilizes the [Location.copy] function.
 * @return copy of location, [amount] lower
 * @author Fruxz
 * @since 1.0
 */
fun Location.lower(amount: Int = 1) = copy(y = y - amount)

// location direction math

/**
 * This function computes the required velocity, to reach the [destination]
 * from [this] source position.
 * @author Fruxz
 * @since 1.0
 */
infix fun Location.velocityTo(destination: Location) = toVector() velocityTo destination.toVector()

/**
 * This function computes the required velocity, to reach the [destination]
 * from [this] source position.
 * @author Fruxz
 * @since 1.0
 */
infix fun LazyLocation.velocityTo(destination: LazyLocation) = asVector velocityTo destination.asVector

// location chunks

/**
 * This function computes the blocks, that are around [this]
 * location, inside the defined [radius].
 * @author Fruxz
 * @since 1.0
 */
fun Location.getNearbyBlocks(radius: Int) = buildSet {
    for (x in -radius until radius) {
        for (y in -radius until radius) {
            for (z in -radius until radius) {
                add(world.getBlockAt(blockX + x, blockY + y, blockZ + z))
            }
        }
    }
}

/**
 * This function computes the chunks, that are around [this] location, inside the defined [radius].
 * @author Fruxz
 * @since 1.0
 */
fun Location.getNearbyChunks(radius: Int) = buildSet {
    for (x in -radius until radius) {
        for (z in -radius until radius) {
            add(world.getChunkAt(chunk.x + x, chunk.z + z))
        }
    }
}

// location infix smart functions

/**
 * This functions takes a ([Number] to [Number] to [Number]) [Triple],
 * to use the values as the parameters of the [Location].
 * - the *first* value is the `x-coordinate`
 * - the *second* value is the `y-coordinate`
 * - the *third* value is the `z-coordinate`
 * @param world the world, where the location-data is located at
 * @return the generated Location-object
 * @author Fruxz
 * @since 1.0
 */
fun Pair<Pair<Number, Number>, Number>.asLocation(world: World) =
    Location(world, first.first.toDouble(), first.second.toDouble(), second.toDouble())

/**
 * This functions takes a ([Number] to [Number] to [Number]) [Triple],
 * to use the values as the parameters of the [Location].
 * - the *first* value is the `x-coordinate`
 * - the *second* value is the `y-coordinate`
 * - the *third* value is the `z-coordinate`
 * @param worldName the name of the world, where the location data is located at
 * @return the generated Location-object, or null if the world does not exist
 * @author Fruxz
 * @since 1.0
 */
fun Pair<Pair<Number, Number>, Number>.asLocation(worldName: String) =
    worldOrNull(worldName)?.let { asLocation(it) }

// UX

fun Location.asDisplayable(
    includeRotation: Boolean = true,
): TextComponent {
    return text {
        this + text("[").dyeGray()
        this + text("x: ${x.shorter}").dyeAqua()
        this + text(", ").dyeGray()
        this + text("y: ${y.shorter}").dyeAqua()
        this + text(", ").dyeGray()
        this + text("z: ${z.shorter}").dyeAqua()

        if (includeRotation) {
            this + text(", ").dyeGray()
            this + text("yaw: ${yaw.shorter}").dyeDarkAqua()
            this + text(", ").dyeGray()
            this + text("pitch: ${pitch.shorter}").dyeDarkAqua()
        }

        this + text("]").dyeGray()

        hover {
            text {
                this + text("CLICK").style(NamedTextColor.GREEN, TextDecoration.BOLD)
                this + text(" to teleport to this location").dyeGray()
            }
        }

        clickEvent(ClickEvent.suggestCommand("/teleport @s $x $y $z $yaw $pitch"))
    }
}
