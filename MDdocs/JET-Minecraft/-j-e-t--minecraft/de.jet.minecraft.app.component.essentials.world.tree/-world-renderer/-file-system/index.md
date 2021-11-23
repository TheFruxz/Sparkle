//[JET-Minecraft](../../../../index.md)/[de.jet.minecraft.app.component.essentials.world.tree](../../index.md)/[WorldRenderer](../index.md)/[FileSystem](index.md)

# FileSystem

[jvm]\
object [FileSystem](index.md)

## Functions

| Name | Summary |
|---|---|
| [createDirectory](create-directory.md) | [jvm]<br>fun [createDirectory](create-directory.md)(path: [Address](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt;) |
| [createWorld](create-world.md) | [jvm]<br>fun [createWorld](create-world.md)(worldName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), worldEnvironment: World.Environment, worldType: WorldType, worldData: [WorldRenderer.RenderWorld](../-render-world/index.md)) |
| [deleteDirectory](delete-directory.md) | [jvm]<br>fun [deleteDirectory](delete-directory.md)(path: [Address](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt;, keepContent: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false) |
| [deleteWorld](delete-world.md) | [jvm]<br>fun [deleteWorld](delete-world.md)(path: [Address](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt;) |
| [directoryExists](directory-exists.md) | [jvm]<br>fun [directoryExists](directory-exists.md)(path: [Address](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getDirectory](get-directory.md) | [jvm]<br>fun [getDirectory](get-directory.md)(path: [Address](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt;): [WorldRenderer.RenderFolder](../-render-folder/index.md)? |
| [getWorld](get-world.md) | [jvm]<br>fun [getWorld](get-world.md)(path: [Address](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt;): [WorldRenderer.RenderWorld](../-render-world/index.md)? |
| [ignoreWorld](ignore-world.md) | [jvm]<br>fun [ignoreWorld](ignore-world.md)(path: [Address](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt;)<br>Removes the world from the config |
| [importWorld](import-world.md) | [jvm]<br>fun [importWorld](import-world.md)(worldName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), renderWorld: [WorldRenderer.RenderWorld](../-render-world/index.md) = RenderWorld(worldName, worldName, address("/$worldName"), emptyList(), false, emptyList())) |
| [moveObject](move-object.md) | [jvm]<br>fun [moveObject](move-object.md)(currentObjectPath: [Address](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt;, futurePath: [Address](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt;) |
| [worldExists](world-exists.md) | [jvm]<br>fun [worldExists](world-exists.md)(path: [Address](../../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart.positioning/-address/index.md)&lt;[WorldRenderer.RenderObject](../-render-object/index.md)&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
