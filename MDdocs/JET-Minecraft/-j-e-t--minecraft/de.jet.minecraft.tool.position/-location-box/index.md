//[JET-Minecraft](../../../index.md)/[de.jet.minecraft.tool.position](../index.md)/[LocationBox](index.md)

# LocationBox

[jvm]\
data class [LocationBox](index.md)(first: Location, last: Location) : [Producible](../../../../JET-Native/-j-e-t--native/de.jet.library.tool.smart/-producible/index.md)&lt;BoundingBox&gt;

## Constructors

| | |
|---|---|
| [LocationBox](-location-box.md) | [jvm]<br>fun [LocationBox](-location-box.md)(both: Location) |
| [LocationBox](-location-box.md) | [jvm]<br>fun [LocationBox](-location-box.md)(locations: [Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;Location, Location&gt;) |
| [LocationBox](-location-box.md) | [jvm]<br>fun [LocationBox](-location-box.md)(locationEntry: [Map.Entry](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/-entry/index.html)&lt;Location, Location&gt;) |
| [LocationBox](-location-box.md) | [jvm]<br>fun [LocationBox](-location-box.md)(vararg locations: Location) |

## Functions

| Name | Summary |
|---|---|
| [contains](contains.md) | [jvm]<br>fun [contains](contains.md)(location: Location): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>fun [contains](contains.md)(block: Block): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>fun [contains](contains.md)(entity: Entity): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>fun [contains](contains.md)(player: Player): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>fun [contains](contains.md)(vector: Vector): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [produce](produce.md) | [jvm]<br>open override fun [produce](produce.md)(): @NotNullBoundingBox |
| [updateFirstLocation](update-first-location.md) | [jvm]<br>fun [updateFirstLocation](update-first-location.md)(newLocation: Location): [LocationBox](index.md) |
| [updateLastLocation](update-last-location.md) | [jvm]<br>fun [updateLastLocation](update-last-location.md)(newLocation: Location): [LocationBox](index.md) |
| [updateLocations](update-locations.md) | [jvm]<br>fun [updateLocations](update-locations.md)(locations: [Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;Location, Location&gt;): [LocationBox](index.md)<br>fun [updateLocations](update-locations.md)(firstLocation: Location, secondLocation: Location): [LocationBox](index.md) |

## Properties

| Name | Summary |
|---|---|
| [blockVolume](block-volume.md) | [jvm]<br>val [blockVolume](block-volume.md): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [center](center.md) | [jvm]<br>val [center](center.md): Vector |
| [distance](distance.md) | [jvm]<br>val [distance](distance.md): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [first](first.md) | [jvm]<br>var [first](first.md): Location |
| [last](last.md) | [jvm]<br>var [last](last.md): Location |

## Extensions

| Name | Summary |
|---|---|
| [displayString](../../de.jet.minecraft.extension.paper/display-string.md) | [jvm]<br>fun [LocationBox](index.md).[displayString](../../de.jet.minecraft.extension.paper/display-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
