//[JET-Native](../../index.md)/[de.jet.library.extension.timing](index.md)

# Package de.jet.library.extension.timing

## Functions

| Name | Summary |
|---|---|
| [editInJETEnvironment](edit-in-j-e-t-environment.md) | [jvm]<br>fun [Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html).[editInJETEnvironment](edit-in-j-e-t-environment.md)(action: [Calendar](../de.jet.library.tool.timing.calendar/-calendar/index.md).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): [Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html)<br>Gets the [JavaUtilCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html), internally converts it, with its contents, to a JET-[Calendar](../de.jet.library.tool.timing.calendar/-calendar/index.md), edit it with the [action](edit-in-j-e-t-environment.md) in the JET-Calender-Environment and returns the [Calendar](../de.jet.library.tool.timing.calendar/-calendar/index.md) converted back to a [JavaUtilCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html) with the new values containing inside the [JavaUtilCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html). |

## Properties

| Name | Summary |
|---|---|
| [jetCalendar](jet-calendar.md) | [jvm]<br>val [Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html).[jetCalendar](jet-calendar.md): [Calendar](../de.jet.library.tool.timing.calendar/-calendar/index.md)<br>Converts a [JavaUtilCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html) to a [Calendar](../de.jet.library.tool.timing.calendar/-calendar/index.md) from JET, but keeps it contents. |
