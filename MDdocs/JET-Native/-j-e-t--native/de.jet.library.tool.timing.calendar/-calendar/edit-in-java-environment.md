//[JET-Native](../../../index.md)/[de.jet.library.tool.timing.calendar](../index.md)/[Calendar](index.md)/[editInJavaEnvironment](edit-in-java-environment.md)

# editInJavaEnvironment

[jvm]\
fun [editInJavaEnvironment](edit-in-java-environment.md)(action: [Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))

Gets this calendar, internally converts it, with its contents, to a [JavaUtilCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html), edit it with the [action](edit-in-java-environment.md) in the [JavaUtilCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html)-Environment and returns the [JavaUtilCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html) converted back to a [Calendar](index.md) with the new values containing inside the [Calendar](index.md).

#### Return

the [Calendar](index.md) with the new values

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| action | the edit process, which is executed in the [JavaUtilCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html)-Environment |
