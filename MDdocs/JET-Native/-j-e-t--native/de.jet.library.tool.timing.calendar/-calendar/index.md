//[JET-Native](../../../index.md)/[de.jet.library.tool.timing.calendar](../index.md)/[Calendar](index.md)

# Calendar

[jvm]\
class [Calendar](index.md) : [Producible](../../de.jet.library.tool.smart/-producible/index.md)&lt;[Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html)&gt; , [Cloneable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-cloneable/index.html)

This class is a calender, which can be from & to a [JavaUtilCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html) transformed.

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| origin | the java base of the calendar |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |
| [TimeField](-time-field/index.md) | [jvm]<br>enum [TimeField](-time-field/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Calendar.TimeField](-time-field/index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [add](add.md) | [jvm]<br>fun [add](add.md)(duration: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)): [Calendar](index.md)<br>This function adds some time to the calendar. It takes the time from the [duration](add.md) and adds it to the calendar using the internal units.<br>[jvm]<br>fun [add](add.md)(timeField: [Calendar.TimeField](-time-field/index.md), amount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Calendar](index.md)<br>This function adds some time to the calendar. It takes the [amount](add.md), takes the time unit [timeField](add.md) and adds it to the calendar. |
| [durationTo](duration-to.md) | [jvm]<br>fun [durationTo](duration-to.md)(jetCalendar: [Calendar](index.md)): [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)<br>Gets the duration from this to the [jetCalendar](duration-to.md).<br>[jvm]<br>fun [durationTo](duration-to.md)(javaCalendar: [Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html)): [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)<br>Gets the duration from this to the [javaCalendar](duration-to.md). |
| [editInJavaEnvironment](edit-in-java-environment.md) | [jvm]<br>fun [editInJavaEnvironment](edit-in-java-environment.md)(action: [Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html))<br>Gets this calendar, internally converts it, with its contents, to a [JavaUtilCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html), edit it with the [action](edit-in-java-environment.md) in the [JavaUtilCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html)-Environment and returns the [JavaUtilCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html) converted back to a [Calendar](index.md) with the new values containing inside the [Calendar](index.md). |
| [equals](equals.md) | [jvm]<br>open operator override fun [equals](equals.md)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [get](get.md) | [jvm]<br>fun [get](get.md)(timeField: [Calendar.TimeField](-time-field/index.md)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>This function returns the time of the calendar. It takes the [timeField](get.md) and returns the time using it. |
| [getTicks](get-ticks.md) | [jvm]<br>fun [getTicks](get-ticks.md)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>This function returns the time of the calendar in Minecraft-Ticks. |
| [hashCode](hash-code.md) | [jvm]<br>open override fun [hashCode](hash-code.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [isAfter](is-after.md) | [jvm]<br>fun [isAfter](is-after.md)(it: [Calendar](index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>This function returns, if this calendar is after the [it](is-after.md) calendar. |
| [isBefore](is-before.md) | [jvm]<br>fun [isBefore](is-before.md)(it: [Calendar](index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>This function returns, if this calendar is before the [it](is-before.md) calendar. |
| [isInputExpired](is-input-expired.md) | [jvm]<br>fun [isInputExpired](is-input-expired.md)(latest: [Calendar](index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>This function returns, if this calendar is after the [latest](is-input-expired.md) calendar. This basically means, this calendar is the expiration date and [latest](is-input-expired.md) is the current date, to check, if the expiration data (this) is expired. |
| [minus](minus.md) | [jvm]<br>operator fun [minus](minus.md)(duration: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)): [Calendar](index.md) |
| [minusAssign](minus-assign.md) | [jvm]<br>operator fun [minusAssign](minus-assign.md)(duration: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)) |
| [plus](plus.md) | [jvm]<br>operator fun [plus](plus.md)(duration: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)): [Calendar](index.md) |
| [plusAssign](plus-assign.md) | [jvm]<br>operator fun [plusAssign](plus-assign.md)(duration: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)) |
| [produce](produce.md) | [jvm]<br>open override fun [produce](produce.md)(): [Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html)<br>Produces the product T |
| [set](set.md) | [jvm]<br>fun [set](set.md)(timeField: [Calendar.TimeField](-time-field/index.md), amount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Calendar](index.md)<br>This function sets the time of the calendar. It takes the [amount](set.md), takes the time unit [timeField](set.md) and sets it to the calendar. |
| [take](take.md) | [jvm]<br>fun [take](take.md)(duration: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)): [Calendar](index.md)<br>This function takes some time from the calendar. It takes the time from the [duration](take.md) and takes it from the calendar using the internal units.<br>[jvm]<br>fun [take](take.md)(timeField: [Calendar.TimeField](-time-field/index.md), amount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Calendar](index.md)<br>This function takes some time from the calendar. It takes the [amount](take.md), takes the time unit [timeField](take.md) and takes it from the calendar. |
| [toString](to-string.md) | [jvm]<br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>This function returns the result of the [toString](to-string.md) function of the [origin](../../../../JET-Native/de.jet.library.tool.timing.calendar/-calendar/origin.md) object. |

## Properties

| Name | Summary |
|---|---|
| [isExpired](is-expired.md) | [jvm]<br>val [isExpired](is-expired.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>This value returns, if this calendar is after [now](-companion/now.md) calendar. This basically means, this calendar is the expiration date and [now](-companion/now.md) is the current date, to check, if the expiration data (this) is expired. |
| [javaCalendar](java-calendar.md) | [jvm]<br>val [javaCalendar](java-calendar.md): [Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html)<br>This value returns this calendar as a [JavaUtilCalendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html) object. |
| [javaDate](java-date.md) | [jvm]<br>val [javaDate](java-date.md): [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html)<br>This value returns this calendar as a [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) object. |
