//[JET-Native](../../index.md)/[de.jet.library.extension.timing](index.md)/[editInJETEnvironment](edit-in-j-e-t-environment.md)

# editInJETEnvironment

[jvm]\
fun [Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html).[editInJETEnvironment](edit-in-j-e-t-environment.md)(action: [Calendar](../de.jet.library.tool.timing.calendar/-calendar/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html)

Gets the [JavaUtilCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html), internally converts it, with its contents, to a JET-[Calendar](../de.jet.library.tool.timing.calendar/-calendar/index.md), edit it with the [action](edit-in-j-e-t-environment.md) in the JET-Calender-Environment and returns the [Calendar](../de.jet.library.tool.timing.calendar/-calendar/index.md) converted back to a [JavaUtilCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html) with the new values containing inside the [JavaUtilCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html).

#### Return

the [JavaUtilCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html) with the new values

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| action | the edit process, which is executed in the JET-[Calendar](../de.jet.library.tool.timing.calendar/-calendar/index.md)-Environment |
