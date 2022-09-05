package de.moltenKt.paper.extension.paper

import com.destroystokyo.paper.ParticleBuilder
import de.moltenKt.core.extension.dump
import de.moltenKt.paper.tool.display.world.ChunkLocation
import de.moltenKt.paper.tool.display.world.SimpleLocation
import de.moltenKt.paper.tool.position.dependent.DependentCubicalShape
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector
import kotlin.math.roundToInt

/**
 * This function creates a new [DependentCubicalShape] by using
 * [this] location as the [DependentCubicalShape.first] and the
 * [location] as the [DependentCubicalShape.second].
 * @param location The location to use as the second location of the [DependentCubicalShape]
 * @return A new [DependentCubicalShape] with [this] location as the first location and the [location] as the second location
 * @author Fruxz
 * @since 1.0
 */
operator fun Location.plus(location: Location) = DependentCubicalShape(this, location)

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

fun Location.displayString(
	withNames: Boolean = true,
	withRotation: Boolean = false,
	displayX: Char = 'x',
	displayY: Char = 'y',
	displayZ: Char = 'z',
	displayYaw: String = "yaw",
	displayPitch: String = "pitch"
) =
	with(this) {
		if (withNames) {
			"$displayX: $x $displayY: $y $displayZ: $z" + if (withRotation) "$displayYaw: ${yaw.roundToInt()} $displayPitch: ${pitch.roundToInt()}" else ""
		} else
			"$x $y $z" + if (withRotation) "${yaw.roundToInt()} ${pitch.roundToInt()}" else ""
	}

/**
 * This function returns, if the [location]'s [Location.x], [Location.y] and
 * [Location.z] is contained inside [this] [BoundingBox].
 * @author Fruxz
 * @since 1.0
 */
operator fun BoundingBox.contains(location: Location) = this.contains(location.toVector())

/**
 * This function returns, if the [Location.x], [Location.y] and [Location.z]
 * of the [Entity] is contained inside [this] [BoundingBox].
 * @author Fruxz
 * @since 1.0
 */
operator fun BoundingBox.contains(entity: Entity) = entity.location in this

/**
 * This function returns, if the [Location.x], [Location.y] and [Location.z]
 * of the [Block] is contained inside [this] [BoundingBox].
 * @author Fruxz
 * @since 1.0
 */
operator fun BoundingBox.contains(block: Block) = block.location in this

/**
 * This function computes the required velocity, to reach the [destination]
 * from [this] source position.
 * @author Fruxz
 * @since 1.0
 */
infix fun Vector.velocityTo(destination: Vector) = destination.subtract(this)

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
infix fun SimpleLocation.velocityTo(destination: SimpleLocation) = toVector() velocityTo destination.toVector()

/**
 * This computational value computes the exact representation
 * of blocks bounding box.
 * @author Fruxz
 * @since 1.0
 */
val Block.safeBoundingBox: BoundingBox
	get() = BoundingBox.of(location, location.clone().add(Vector(1.0, 1.0, 1.0)))

/**
 * This function sets the location and offset of the [ParticleBuilder] to the data
 * of the provided [BoundingBox].
 * @author Fruxz
 * @since 1.0
 */
fun ParticleBuilder.location(world: World, boundingBox: BoundingBox) =
	location(world, boundingBox.centerX, boundingBox.centerY, boundingBox.centerZ)
		.offset(boundingBox.widthX / 2, boundingBox.height / 2, boundingBox.widthZ / 2)

/**
 * This function sets the location and offset of the [ParticleBuilder] to the data
 * of the provided [Entity]. (Entity bounding box)
 * @author Fruxz
 * @since 1.0
 */
fun ParticleBuilder.location(entity: Entity) = with(entity.boundingBox) {
	this@location.location(entity.world, centerX, centerY, centerZ)
		.offset(widthX/2, height/2, widthZ/2)
}

/**
 * This function sets the location and offset of the [ParticleBuilder] to the data
 * of the provided [Block]. (Block size)
 * @author Fruxz
 * @since 1.0
 */
fun ParticleBuilder.location(block: Block) = with(block.boundingBox) {
	this@location.location(block.world, centerX, centerY, centerZ)
		.offset(widthX/2, height/2, widthZ/2)
}

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

/**
 * This function creates a new simple location object,
 * with this location data.
 * @author Fruxz
 * @since 1.0
 */
fun Location.toSimpleLocation() = with(this) { SimpleLocation(world.name, x, y, z) }

/**
 * This computational value represents the [Entity.getLocation] of the entity
 * as a new [SimpleLocation] object.
 * @author Fruxz
 * @since 1.0
 */
val Entity.simpleLocation: SimpleLocation
	get() = location.toSimpleLocation()

/**
 * This computational value represents the [Block.getLocation] of the block
 * as a new [SimpleLocation] object.
 * @author Fruxz
 * @since 1.0
 */
val Block.simpleLocation: SimpleLocation
	get() = location.toSimpleLocation()

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
	pitch: Number = this.pitch
) = Location(world, x.toDouble(), y.toDouble(), z.toDouble(), yaw.toFloat(), pitch.toFloat())

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

/**
 * This function creates a copy of [this] [Vector],
 * its parameters are filled with [this] data by default,
 * but can be changed on demand, like the copy functions
 * of data-classes.
 * @author Fruxz
 * @since 1.0
 */
fun Vector.copy(
	x: Number = this.x,
	y: Number = this.y,
	z: Number = this.z,
) = Vector(x.toDouble(), y.toDouble(), z.toDouble())

/**
 * This function creates a copy of [this] [BoundingBox],
 * its parameters are filled with [this] data by default,
 * but can be changed on demand, like the copy functions
 * of data-classes.
 * @author Fruxz
 * @since 1.0
 */
fun BoundingBox.copy(
	fromVector: Vector = this.min,
	toVector: Vector = this.max,
) = BoundingBox.of(fromVector, toVector)

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

/**
 * This computational value returns a new [ChunkLocation] object,
 * that contains the x, y and the world key of [this] [Chunk].
 * @author Fruxz
 * @since 1.0
 */
val Chunk.location: ChunkLocation
	get() = ChunkLocation(world.key, x, z)

/**
 * This function utilizes the [World.getChunkAt] function, to get the
 * chunk at the provided location, provided by the [location] parameter.
 * @see World.getChunkAt
 * @author Fruxz
 * @since 1.0
 */
fun World.getChunkAt(location: ChunkLocation) = getChunkAt(location.x, location.z)

/**
 * This function utilizes the [World.getChunkAtAsync] function, to get the
 * chunk at the provided location, provided by the [location] parameter.
 * @see World.getChunkAtAsync
 * @author Fruxz
 * @since 1.0
 */
fun World.getChunkAtAsync(location: ChunkLocation) = getChunkAtAsync(location.x, location.z)

/**
 * This function returns, if the provided [location]s chunk is the
 * same as the chunk-location of [this] [Chunk].
 * @return true if the chunks are the same, false if not
 * @author Fruxz
 * @since 1.0
 */
operator fun Chunk.contains(location: Location) = this.location == location.chunk.location

/**
 * This function returns, if the [Location] of the [entity]'s [Chunk]
 * is the same as [this] [Chunk]s location.
 * @return true, if the [Location] of the [entity]'s [Chunk] is the same as [this] [Chunk]s location
 * @author Fruxz
 * @since 1.0
 */
operator fun Chunk.contains(entity: Entity) = entity.location in this

/**
 * This function returns, if the [Location] of the [block]'s [Chunk]
 * is the same as [this] [Chunk]s location.
 * @return true if the [Location] of the [block]'s [Chunk] is the same as [this] location
 * @author Fruxz
 * @since 1.0
 */
operator fun Chunk.contains(block: Block) = block.location in this

/**
 * This function creates a copy of [this] location, using the
 * [Location.copy] function, but modified with the different
 * modifiers.
 * The [Location.x], [Location.y], [Location.z], [Location.yaw] and [Location.pitch]
 * gets modified during the copy, by adding the parameters (default all 0) to the
 * current state of [this] location.
 * The [x], [y] and [z] are getting converted into doubles using the [Number.toDouble]
 * and the [yaw] and [pitch] are getting converted into floats using the [Number.toFloat].
 * @author Fruxz
 * @since 1.0
 */
fun Location.modify(
	x: Number = 0,
	y: Number = 0,
	z: Number = 0,
	yaw: Number = 0,
	pitch: Number = 0,
) =
	copy(
		x = this.x + x.toDouble(),
		y = this.y + y.toDouble(),
		z = this.z + z.toDouble(),
		yaw = this.yaw + yaw.toFloat(),
		pitch = this.pitch + pitch.toFloat(),
	)

val World.generatorCode: Long
	get() = seed

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

val Chunk.positionX: IntRange
	get() = x * 16 until (x + 1) * 16

val Chunk.positionY: IntRange
	get() = world.minHeight..world.maxHeight

val Chunk.positionZ: IntRange
	get() = z * 16 until (z + 1) * 16

val Chunk.locations: Set<Location>
	get() = buildSet {
		for (x in positionX) {
			for (y in positionY) {
				for (z in positionZ) {
					add(location(world, x, y, z))
				}
			}
		}
	}

val Chunk.blocks: Set<Block>
	get() = buildSet {
		for (x in positionX) {
			for (y in positionY) {
				for (z in positionZ) {
					add(world.getBlockAt(x, y, z))
				}
			}
		}
	}

fun Chunk.blocks(filter: (Block) -> Boolean): Set<Block> = buildSet {
	for (x in positionX) {
		for (y in positionY) {
			for (z in positionZ) {
				val block = world.getBlockAt(x, y, z)
				if (filter(block)) {
					add(block)
				}
			}
		}
	}
}

inline fun <reified T> Chunk.blocks(): Set<T> = buildSet {
	for (x in positionX) {
		for (y in positionY) {
			for (z in positionZ) {
				val block = world.getBlockAt(x, y, z)
				if (block is T) {
					add(block)
				}
			}
		}
	}
}
