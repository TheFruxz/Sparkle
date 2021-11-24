//[JET-Discord](../../index.md)/[de.jet.discord.extension](index.md)/[server](server.md)

# server

[jvm]\
fun DiscordApi.[server](server.md)(id: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)): Server?

Get the server with the given [id](server.md) or null if the bot is not part of that server (or the server-id doesn't exist).

#### Return

the server with the given [id](server.md) or null if the bot is not part of that server

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| id | the id of the searched server |

[jvm]\
fun DiscordApi.[server](server.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Server?

Get the first server with the given [name](server.md).

#### Return

the first server wich appeared in the search query with the given [name](server.md)

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| name | the name of the searched server |
