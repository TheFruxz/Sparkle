//[JET-Discord](../../index.md)/[de.jet.discord.extension](index.md)/[textChannels](text-channels.md)

# textChannels

[jvm]\
fun DiscordApi.[textChannels](text-channels.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;TextChannel&gt;

Gets every TextChannel with the given [name](text-channels.md) searched discord-api wide as a list.

#### Return

Every TextChannel with the given [name](text-channels.md) or an empty list if not found.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| name | The name of the TextChannel to get. |

[jvm]\
fun DiscordApi.[textChannels](text-channels.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;TextChannel&gt;

Gets every TextChannel searched discord-api wide as a list.

#### Return

Every TextChannel or an empty list if not found.

#### Author

Fruxz

#### Since

1.0

[jvm]\
fun Server.[textChannels](text-channels.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;ServerTextChannel&gt;

Gets every TextChannel with the given [name](text-channels.md) searched in the given server [this](../../../JET-Discord/de.jet.discord.extension/index.md) as a list.

#### Return

Every TextChannel with the given [name](text-channels.md) or an empty list if not found.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| name | The name of the TextChannel to get. |

[jvm]\
fun Server.[textChannels](text-channels.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;ServerTextChannel&gt;

Gets every TextChannel searched in the given server [this](../../../JET-Discord/de.jet.discord.extension/index.md) as a list.

#### Return

Every TextChannel or an empty list if not found.

#### Author

Fruxz

#### Since

1.0
