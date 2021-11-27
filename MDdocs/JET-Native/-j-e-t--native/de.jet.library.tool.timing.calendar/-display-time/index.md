//[JET-Native](../../../index.md)/[de.jet.library.tool.timing.calendar](../index.md)/[DisplayTime](index.md)

# DisplayTime

[jvm]\
data class [DisplayTime](index.md)(ticks: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html))

This data class helps to easily create a displayable Time-String, which can be set up to display the data you want to display it.

## Parameters

jvm

| | |
|---|---|
| ticks | the amount of ticks used in this time |

## Constructors

| | |
|---|---|
| [DisplayTime](-display-time.md) | [jvm]<br>fun [DisplayTime](-display-time.md)(format: [DisplayTime.Format](-format/index.md), timeValue: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)) |
| [DisplayTime](-display-time.md) | [jvm]<br>fun [DisplayTime](-display-time.md)(duration: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)) |
| [DisplayTime](-display-time.md) | [jvm]<br>fun [DisplayTime](-display-time.md)(ticks: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)) |

## Types

| Name | Summary |
|---|---|
| [Format](-format/index.md) | [jvm]<br>~~enum~~ [~~Format~~](-format/index.md) ~~:~~ [~~Enum~~](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)~~&lt;~~[DisplayTime.Format](-format/index.md)~~&gt;~~ <br>The time formats |

## Functions

| Name | Summary |
|---|---|
| [getClockDisplay](get-clock-display.md) | [jvm]<br>fun [getClockDisplay](get-clock-display.md)(vararg views: [DisplayTime.Format](-format/index.md)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Generates a displayable string, but only contains the values of the time units of [views](get-clock-display.md). |

## Properties

| Name | Summary |
|---|---|
| [ticks](ticks.md) | [jvm]<br>var [ticks](ticks.md): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
