//[JET-Minecraft](../../../../index.md)/[de.jet.minecraft.app.component.essentials.world.tree](../../index.md)/[WorldRenderer](../index.md)/[RenderBranchResult](index.md)

# RenderBranchResult

[jvm]\
data class [RenderBranchResult](index.md)(currentDirectory: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), renderWorlds: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[WorldRenderer.RenderWorld](../-render-world/index.md)&gt;, renderFolders: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[WorldRenderer.RenderFolder](../-render-folder/index.md)&gt;)

## Functions

| Name | Summary |
|---|---|
| [folderExists](folder-exists.md) | [jvm]<br>fun [folderExists](folder-exists.md)(path: [Address](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getFolder](get-folder.md) | [jvm]<br>fun [getFolder](get-folder.md)(path: [Address](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt;): [WorldRenderer.RenderFolder](../-render-folder/index.md)? |
| [getWorld](get-world.md) | [jvm]<br>fun [getWorld](get-world.md)(path: [Address](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt;): [WorldRenderer.RenderWorld](../-render-world/index.md)? |
| [smash](smash.md) | [jvm]<br>fun [smash](smash.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt; |
| [structure](structure.md) | [jvm]<br>fun [structure](structure.md)(): [WorldRenderer.WorldStructure](../-world-structure/index.md) |
| [worldExists](world-exists.md) | [jvm]<br>fun [worldExists](world-exists.md)(path: [Address](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

## Properties

| Name | Summary |
|---|---|
| [currentDirectory](current-directory.md) | [jvm]<br>val [currentDirectory](current-directory.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [renderFolders](render-folders.md) | [jvm]<br>val [renderFolders](render-folders.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[WorldRenderer.RenderFolder](../-render-folder/index.md)&gt; |
| [renderWorlds](render-worlds.md) | [jvm]<br>val [renderWorlds](render-worlds.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[WorldRenderer.RenderWorld](../-render-world/index.md)&gt; |
