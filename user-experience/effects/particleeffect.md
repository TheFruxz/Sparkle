---
description: Use particles
---

# 🗯 ParticleEffect

## The ParticleData

The particle effect is mainly used by the ParticleData object, which is based on the paper ParticleBuilder API.

So you can use the ParticleEffect, by creating a new ParticleData. You can use the ParticleData like a ParticleBuilder, but to display the particle, you should use the play(...) function instead.

The additional positive effect, of using the ParticleData instead of the normal ParticleBuilder is, that the type of data is respected, instead of thrown away. This prevents errors and confusion.

Use the putData(...) function, to limit the input to the requested type of the particle, so no issues will appear during the data use!

## An example

```kotlin
ParticleData(ParticleType.BLOCK_MARKER)
   .putData(Material.BARREL.createBlockData())
   .count(20)
   .offset(10)
   .extra(.0)
   .playParticleEffect(10)
```
