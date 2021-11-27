//[JET-Native](../../index.md)/[de.jet.library.extension](index.md)/[jetTry](jet-try.md)

# jetTry

[jvm]\
fun [jetTry](jet-try.md)(catchBlock: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) = { }, tryBlock: () -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))

Sourrunds the [tryBlock](jet-try.md) with a try catch block and if the try catch block catches an exception it will execute [catchException](catch-exception.md).

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| catchBlock | the block to execute if the try catch block catches an exception, before the [catchException](catch-exception.md) is executed |
| tryBlock | the block to execute |
