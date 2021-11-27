//[JET-Native](../../../index.md)/[de.jet.library.tool.math](../index.md)/[Percentage](index.md)/[displayString](display-string.md)

# displayString

[jvm]\
fun [displayString](display-string.md)(filled: [CharSequence](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html), empty: [CharSequence](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-char-sequence/index.html), displaySize: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Computes a beautiful displayable percentage bar (e.g. 60% -> ||||||::::) It takes [displaySize](display-string.md) as the size of the bar, [filled](display-string.md) as the filled part and [empty](display-string.md) as the empty part. It appends the [filled](display-string.md) and [empty](display-string.md) to the bar and returns it.

#### Return

the percentage bar

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
