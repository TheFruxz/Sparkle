package dev.fruxz.sparkle.framework.world

import org.bukkit.FluidCollisionMode
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.util.RayTraceResult
import org.bukkit.util.Vector

fun Location.rayTrace(
    direction: Vector,
    maxDistance: Double,
    raySize: Double,
    fluidCollisionMode: FluidCollisionMode = FluidCollisionMode.NEVER,
    ignorePassableBlocks: Boolean = false,
    filter: (Entity) -> Boolean = { true },
): RayTraceResult? = world.rayTrace(
    /* start = */ this,
    /* direction = */ direction,
    /* maxDistance = */ maxDistance,
    /* fluidCollisionMode = */ fluidCollisionMode,
    /* ignorePassableBlocks = */ ignorePassableBlocks,
    /* raySize = */ raySize,
    /* filter = */ filter,
)

fun Location.rayTraceBlocks(
    direction: Vector,
    maxDistance: Double,
    fluidCollisionMode: FluidCollisionMode = FluidCollisionMode.NEVER,
    ignorePassableBlocks: Boolean = false,
): RayTraceResult? = world.rayTraceBlocks(
    /* start = */ this,
    /* direction = */ direction,
    /* maxDistance = */ maxDistance,
    /* fluidCollisionMode = */ fluidCollisionMode,
    /* ignorePassableBlocks = */ ignorePassableBlocks
)

fun Location.rayTrace(
    direction: Vector,
    maxDistance: Double,
    raySize: Double? = null,
    filter: ((Entity) -> Boolean)? = null,
): RayTraceResult? = when {
    filter == null && raySize == null -> world.rayTraceEntities(
        this, direction, maxDistance
    )
    filter == null && raySize != null -> world.rayTraceEntities(
        this, direction, maxDistance, raySize.toDouble()
    )
    filter != null && raySize == null -> world.rayTraceEntities(
        this, direction, maxDistance, filter
    )
    else -> world.rayTraceEntities(
        this, direction, maxDistance, raySize ?: .0, filter
    )
}