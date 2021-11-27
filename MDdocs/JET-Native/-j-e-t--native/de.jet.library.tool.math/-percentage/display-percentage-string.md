//[JET-Native](../../../index.md)/[de.jet.library.tool.math](../index.md)/[Percentage](index.md)/[displayPercentageString](display-percentage-string.md)

# displayPercentageString

[jvm]\
fun [displayPercentageString](display-percentage-string.md)(filled: [CharSequence](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html), empty: [CharSequence](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html), displaySize: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), percentPrefix: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = " ", percentSuffix: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) = "%"): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Computes a beautiful displayable percentage bar (e.g. 60% -> ||||||::::) from [displayString](display-string.md) and appends a [percentPrefix](display-percentage-string.md), percentage and a [percentSuffix](display-percentage-string.md).

#### Return

the percentage bar with the percentage amount

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| filled | the symbols to use for the filled part |
| empty | the symbols to use for the empty part |
| displaySize | the size of the bar |
| percentPrefix | the prefix to append before the percentage |
| percentSuffix | the suffix to append after the percentage |
