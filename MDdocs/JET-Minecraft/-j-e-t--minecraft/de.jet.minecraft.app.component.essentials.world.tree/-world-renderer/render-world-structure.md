//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.app.component.essentials.world.tree](../index.md)/[WorldRenderer](index.md)/[renderWorldStructure](render-world-structure.md)

# renderWorldStructure

[jvm]\
fun [renderWorldStructure](render-world-structure.md)(worldStructure: [WorldRenderer.WorldStructure](-world-structure/index.md) = JetData.worldStructure.content, onlyBaseFolder: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, basePath: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = "/"): [WorldRenderer.RenderBranchResult](-render-branch-result/index.md)

Path-Structure:

- 
   / home-dir
- 
   /favorites/*favorites* dir in the home dir

## Throws

| | |
|---|---|
| [kotlin.NoSuchElementException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-no-such-element-exception/index.html) | if the target-directory not exist |
