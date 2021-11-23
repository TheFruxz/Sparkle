//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.app.component.essentials.world.tree](../index.md)/[WorldRenderer](index.md)

# WorldRenderer

[jvm]\
object [WorldRenderer](index.md)

## Types

| Name | Summary |
|---|---|
| [FileSystem](-file-system/index.md) | [jvm]<br>object [FileSystem](-file-system/index.md) |
| [OpenWorldStructure](-open-world-structure/index.md) | [jvm]<br>data class [OpenWorldStructure](-open-world-structure/index.md)(smashedStructure: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[WorldRenderer.RenderObject](-render-object/index.md)&gt;) |
| [RenderBranchResult](-render-branch-result/index.md) | [jvm]<br>data class [RenderBranchResult](-render-branch-result/index.md)(currentDirectory: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), renderWorlds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[WorldRenderer.RenderWorld](-render-world/index.md)&gt;, renderFolders: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[WorldRenderer.RenderFolder](-render-folder/index.md)&gt;) |
| [RenderFolder](-render-folder/index.md) | [jvm]<br>data class [RenderFolder](-render-folder/index.md)(displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), address: [Address](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](-render-object/index.md)&gt;, labels: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, archived: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) : [WorldRenderer.RenderObject](-render-object/index.md) |
| [RenderObject](-render-object/index.md) | [jvm]<br>interface [RenderObject](-render-object/index.md) : [Identifiable](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.identification/-identifiable/index.md)&lt;[WorldRenderer.RenderObject](-render-object/index.md)&gt; , [Addressable](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-addressable/index.md)&lt;[WorldRenderer.RenderObject](-render-object/index.md)&gt; |
| [RenderWorld](-render-world/index.md) | [jvm]<br>data class [RenderWorld](-render-world/index.md)(displayName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), identity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), address: [Address](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](-render-object/index.md)&gt;, labels: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, archived: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), visitors: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) : [WorldRenderer.RenderObject](-render-object/index.md) |
| [WorldStructure](-world-structure/index.md) | [jvm]<br>data class [WorldStructure](-world-structure/index.md)(smashedStructure: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[WorldRenderer.RenderObject](-render-object/index.md)&gt;) |

## Functions

| Name | Summary |
|---|---|
| [computeFolderContents](compute-folder-contents.md) | [jvm]<br>fun [computeFolderContents](compute-folder-contents.md)(folder: [WorldRenderer.RenderFolder](-render-folder/index.md), worldStructure: [WorldRenderer.WorldStructure](-world-structure/index.md) = JetData.worldStructure.content): [WorldRenderer.RenderBranchResult](-render-branch-result/index.md) |
| [renderBase](render-base.md) | [jvm]<br>fun [renderBase](render-base.md)(base: [WorldConfig](../../de.jet.minecraft.app.component.essentials.world/-world-config/index.md) = JetData.worldConfig.content): [WorldRenderer.RenderBranchResult](-render-branch-result/index.md) |
| [renderOverview](render-overview.md) | [jvm]<br>fun [renderOverview](render-overview.md)(path: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = "/", base: [WorldRenderer.WorldStructure](-world-structure/index.md) = JetData.worldStructure.content): [WorldRenderer.RenderBranchResult](-render-branch-result/index.md)<br>Path-Structure: |
| [renderWorldStructure](render-world-structure.md) | [jvm]<br>fun [renderWorldStructure](render-world-structure.md)(worldStructure: [WorldRenderer.WorldStructure](-world-structure/index.md) = JetData.worldStructure.content, onlyBaseFolder: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, basePath: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = "/"): [WorldRenderer.RenderBranchResult](-render-branch-result/index.md)<br>Path-Structure: |
