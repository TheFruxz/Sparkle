//[JET-Discord](../../index.md)/[de.jet.discord.extension](index.md)/[channels](channels.md)

# channels

[jvm]\
fun DiscordApi.[channels](channels.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Channel&gt;

Gets every Channel with the given [name](channels.md) searched discord-api wide as a list.

#### Return

Every Channel with the given [name](channels.md) or an empty list if not found.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| name | The name of the Channel to get. |

[jvm]\
fun DiscordApi.[channels](channels.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Channel&gt;

Gets every Channel searched discord-api wide as a list.

#### Return

Every Channel or an empty list if not found.

#### Author

Fruxz

#### Since

1.0

[jvm]\
fun Server.[channels](channels.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;ServerChannel&gt;

Gets every Channel with the given [name](channels.md) searched in the given server [this](../../../JET-Discord/de.jet.discord.extension/index.md) as a list.

#### Return

Every Channel with the given [name](channels.md) or an empty list if not found.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| name | The name of the Channel to get. |

[jvm]\
fun Server.[channels](channels.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;ServerChannel&gt;

Gets every Channel searched in the given server [this](../../../JET-Discord/de.jet.discord.extension/index.md) as a list.

#### Return

Every Channel or an empty list if not found.

#### Author

Fruxz

#### Since

1.0
