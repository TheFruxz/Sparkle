---
description: Messages with impact
---

# 📨 Transmission

## General

You want to send a message with style, notify the player about something, or just want these little cool sound effects? 😄

Sparkle presents the Transmissions, a way to communicate with the player with style.

## Features

The Transmissions are using prefixes, which are adapting to the [Level](transmission.md#undefined), on which the transmission is set to. Also, a sound can be specified, which will be played on the arrival of the message to the receivers.

## Prefix

The prefix will be firstly grabbed by the default-null prefix parameter, then by the level. So if you want to configure your own custom prefix, define it in the constructor or change the default ones inside the configuration file of Sparkle, located inside the SparkleApps folder of your server.

The prefix will be applied to every line of your message, to keep your transmission messages consistent and uniform.

## Level

You can define a level for the interchange, on which the default prefix will be grabbed, to be displayed.

## The use

Here are two possible ways, to send a message Transmission:

```kotlin
private val helloWorldMessage =
	"<green>Hello World!".message()

fun tellHello(receiver: Player) {
   helloWorldMessage
      .participants(arrayOf(receiver))
      .display()
}
```

```kotlin

fun tellHello(receiver: Player) {
   "<green>Hello World!"
      .message(receiver)
      .display()
}

```

But we already talked about playing a sound? Sparkle calls it notifications, try this to send notifications:

```kotlin
fun tellHello(receiver: Player) {
   "<green>Hello World!"
      .notification(Level.GENERAL, receiver)
      .display()
}

```

Now the message is transmitted with a small, friendly sound!

There are multiple different notification levels available, with their own sounds played:

* GENERAL -> every normal message
* PROCESS -> indicating, something is now in the work
* FAIL -> something has not worked, maybe some wrong input? (mostly user related)
* ERROR -> something crashed, maybe some exception going on? (mostly system related)
* LEVEL -> indicating, something is leveling up
* WARNING -> something meaningful happens/happened
* ATTENTION -> some action requires your immediate action
* PAYMENT -> a financial transaction happened
* APPLIED -> a change was successfully applied to something
