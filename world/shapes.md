---
description: Check this 3-Dimensional room out!
---

# 🏙 Shapes

## The purpose

Working with BoundingBoxes is extremely helpful, to working with the 3-Dimensional space, which Minecraft gives us. But, BoundingBoxes is only the entrace of working with this available complex space, the Shapes API of Sparkle brings this to the next level!

With Sparkles shapes, you can define lines, spheres, pyramids, and cylinders, and even combine multiple shapes into new complex shapes.

## The technical side

To allow the computation of shapes, even with no relation to a real 3-Dimensional room, like Minecraft, we split the shape API into 2 parts. The (independent) shapes and the dependent shapes. Each dependent shape is based on the independent shape. This allows the computation like volume, to happen entirely inside the independent shapes code and the dependent computation, like the exact positioning computation, happens entirely inside the dependent shapes.

The following type of shapes are currently available to use:

| Shape       | Description                                                                                                                                                                                                                                                                                                                |
| ----------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Linear      | This shape is a line drawn into the 3D space. Its volume is only the way this line is following (distance from A to B).                                                                                                                                                                                                    |
| Cubical     | This shape is a typical box with, which is drawn from point A to point B                                                                                                                                                                                                                                                   |
| Sphere      | This shape is a ball with a center A and a radius, defining the width and height in space                                                                                                                                                                                                                                  |
| Cylindrical | This shape is a cylinder, with a floor-center A, a radius, and a height in space                                                                                                                                                                                                                                           |
| Pyramidal   | This shape is a pyramid, with an floor-width, a floor length and a height.                                                                                                                                                                                                                                                 |
| Complex     | This shape is the only shape, which is not also an (independent) Shape. This shape is the result of combining multiple dependent Shapes together. So if you have a cube with a volume of 10 $$Blocks^3$$ and a sphere with a volume of 20 $$Blocks^3$$​you gain a DependentComplexShape with a volume of 30 $$Blocks^3$$​! |

{% hint style="danger" %}
**NOTE!** DependentComplexShapes are not respecting the multiple uses of space, inside the computation of volume. This mean, 2 cubical shapes, in the exact same spot, are having both a volume of 10 $$Blocks^3$$​resulting in a complex shape with a volume of 20 $$Blocks^3$$​, non the less that they are only occupying $$10 Blocks^3$$​ real-world space!
{% endhint %}

The shapes (excluding the complex shape) are having an Interface and a Dependent\<NAME> serializable data class. For example:

Pyramidal-Shape: `[Interface: PyramidalShape]` + `[data-class: DependentPyramidalShape]`

## The code

### Creating a shape

#### Via the Shape interface

You can simply create shapes via the Shape interface of Sparkle. Via this interface companion object, you can choose, which type of shape you want to create and how you want to create it.

Here is a small example, of how you can create a cubical-dependent shape out of 2 locations:

```kotlin

val location1 = location("world", 1, 2, 3)
val location2 = location("world", 4, 5, 6)

val shape = Shape.cube(
   fromLocation = location1,
   toLocation = location2
)

```

This is it, now you have a cubical shape located inside your real Minecraft world, now start to compute with it!

#### Via the constructor

Each Dependent shape has its own constructors, with these you can create your shapes too, but you have to know, what shapes are existing, or look inside the table above!

```kotlin
val location1 = location("world", 1, 2, 3)
val location2 = location("world", 1, 2, 3)

val shape = DependentCubicalShape(location1, location2)
```

### Compute with shapes

With the dependent shapes, you can compute, which size the shapes are generally occupying, which blocks are contained inside the shape, and much more general and specific stuff, just look at the available properties and functions of your shape!

### Serialize shapes

DependentShapes are compatible with the KotlinX Serialization system, so do not worry about saving your dependent shapes inside your JSON-based configuration files!
