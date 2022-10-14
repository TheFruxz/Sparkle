# 🧬 Debug

## Use the `debugLog`

Sometimes, you want more information about the current state of your code, but don't want to remove and add special logging over and over again. Use the `debugLog` functionality of Sparkle, which allows you to display additional debug logging in the console, but only if you enable it inside the configuration file or via an interchange.

Just use one of the two available functions:

* `debugLog(message: String, level: Level = Level.WARNING)`
* or `debugLog(level: Level = Level.WARNING, messageProcess: this.() -> String)`

The first function just prints the message string, if the `debugMode` is enabled and the second function only executes the `messageProcess` and prints its result if the `debugMode` is enabled.

## Enable & Disable debug logs

The first option, to en- & disable the debug logging, is via the `debuglog` interchange. There you can instantly toggle the switch of debug logging, but then it is not logging from the server start.

To even log the starting state of the server, you have to need the second method, the configuration one.

Inside the server root directory, is a SparkleApps/main@Sparkle directory, which contains a settings.json file. Inside this file, you can change the `debugMode` property, which is the base state of the debug logging in Sparkle.
