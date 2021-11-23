//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.app.component.essentials.world.tree](../index.md)/[WorldRenderer](index.md)/[renderOverview](render-overview.md)

# renderOverview

[jvm]\
fun [renderOverview](render-overview.md)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = "/", base: [WorldRenderer.WorldStructure](-world-structure/index.md) = JetData.worldStructure.content): [WorldRenderer.RenderBranchResult](-render-branch-result/index.md)

Path-Structure:

- 
   / home-dir
- 
   /favorites/*favorites* dir in the home dir

## Throws

| | |
|---|---|
| [kotlin.NoSuchElementException](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-no-such-element-exception/index.html) | if the target-directory not exist |
