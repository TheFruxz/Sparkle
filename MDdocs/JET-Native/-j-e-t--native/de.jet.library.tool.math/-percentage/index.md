//[JET-Native](../../../index.md)/[de.jet.library.tool.math](../index.md)/[Percentage](index.md)

# Percentage

[jvm]\
data class [Percentage](index.md)(decimal: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html))

This data class represents a percentage value, which can be used to calculate a value from a given base value.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| decimal | the percentage value in decimal notation *(0.2 = 20%)* |

## Constructors

| | |
|---|---|
| [Percentage](-percentage.md) | [jvm]<br>fun [Percentage](-percentage.md)(percentage: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Constructs a new [Percentage](index.md) object using the percentage directly. |
| [Percentage](-percentage.md) | [jvm]<br>fun [Percentage](-percentage.md)(percentage: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html))<br>Constructs a new [Percentage](index.md) object using the percentage directly. |
| [Percentage](-percentage.md) | [jvm]<br>fun [Percentage](-percentage.md)(percentage: [Short](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html))<br>Constructs a new [Percentage](index.md) object using the percentage directly. |
| [Percentage](-percentage.md) | [jvm]<br>fun [Percentage](-percentage.md)(percentage: [Byte](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte/index.html))<br>Constructs a new [Percentage](index.md) object using the percentage directly. |
| [Percentage](-percentage.md) | [jvm]<br>fun [Percentage](-percentage.md)(decimal: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)) |

## Functions

| Name | Summary |
|---|---|
| [displayPercentageString](display-percentage-string.md) | [jvm]<br>fun [displayPercentageString](display-percentage-string.md)(filled: [CharSequence](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html), empty: [CharSequence](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html), displaySize: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), percentPrefix: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = " ", percentSuffix: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = "%"): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Computes a beautiful displayable percentage bar (e.g. 60% -> ||||||::::) from [displayString](display-string.md) and appends a [percentPrefix](display-percentage-string.md), percentage and a [percentSuffix](display-percentage-string.md). |
| [displayString](display-string.md) | [jvm]<br>fun [displayString](display-string.md)(filled: [CharSequence](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html), empty: [CharSequence](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html), displaySize: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Computes a beautiful displayable percentage bar (e.g. 60% -> ||||||::::) It takes [displaySize](display-string.md) as the size of the bar, [filled](display-string.md) as the filled part and [empty](display-string.md) as the empty part. It appends the [filled](display-string.md) and [empty](display-string.md) to the bar and returns it. |
| [percentageString](percentage-string.md) | [jvm]<br>fun [percentageString](percentage-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>This function returns the [percentage](percentage.md) as a string, with an % symbol at the end. |
| [toString](to-string.md) | [jvm]<br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>This function returns the [percentageString](percentage-string.md) as a string. |

## Properties

| Name | Summary |
|---|---|
| [decimal](decimal.md) | [jvm]<br>val [decimal](decimal.md): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [percentage](percentage.md) | [jvm]<br>val [percentage](percentage.md): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)<br>Computes the percentage value directly *(e.g.: 20%; 25.5%; ...)* |
