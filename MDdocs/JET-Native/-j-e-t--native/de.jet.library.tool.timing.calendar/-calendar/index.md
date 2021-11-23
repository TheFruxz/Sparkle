//[JET-Native](../../../index.md)/[de.jet.library.tool.timing.calendar](../index.md)/[Calendar](index.md)

# Calendar

[jvm]\
class [Calendar](index.md) : [Producible](../../de.jet.library.tool.smart/-producible/index.md)&lt;[Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html)&gt; , [Cloneable](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-cloneable/index.html)

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |
| [TimeField](-time-field/index.md) | [jvm]<br>enum [TimeField](-time-field/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[Calendar.TimeField](-time-field/index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [add](add.md) | [jvm]<br>fun [add](add.md)(duration: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)): [Calendar](index.md)<br>fun [add](add.md)(timeField: [Calendar.TimeField](-time-field/index.md), amount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Calendar](index.md) |
| [durationTo](duration-to.md) | [jvm]<br>fun [durationTo](duration-to.md)(jetCalendar: [Calendar](index.md)): [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)<br>fun [durationTo](duration-to.md)(javaCalendar: [Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html)): [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html) |
| [editInJavaEnvironment](edit-in-java-environment.md) | [jvm]<br>fun [editInJavaEnvironment](edit-in-java-environment.md)(action: [Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html).() -&gt; [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)) |
| [equals](equals.md) | [jvm]<br>open operator override fun [equals](equals.md)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [get](get.md) | [jvm]<br>fun [get](get.md)(timeField: [Calendar.TimeField](-time-field/index.md)): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [getTicks](get-ticks.md) | [jvm]<br>fun [getTicks](get-ticks.md)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html) |
| [hashCode](hash-code.md) | [jvm]<br>open override fun [hashCode](hash-code.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [isAfter](is-after.md) | [jvm]<br>fun [isAfter](is-after.md)(it: [Calendar](index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isBefore](is-before.md) | [jvm]<br>fun [isBefore](is-before.md)(it: [Calendar](index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isInputExpired](is-input-expired.md) | [jvm]<br>fun [isInputExpired](is-input-expired.md)(latest: [Calendar](index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [minus](minus.md) | [jvm]<br>operator fun [minus](minus.md)(duration: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)): [Calendar](index.md) |
| [minusAssign](minus-assign.md) | [jvm]<br>operator fun [minusAssign](minus-assign.md)(duration: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)) |
| [plus](plus.md) | [jvm]<br>operator fun [plus](plus.md)(duration: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)): [Calendar](index.md) |
| [plusAssign](plus-assign.md) | [jvm]<br>operator fun [plusAssign](plus-assign.md)(duration: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)) |
| [produce](produce.md) | [jvm]<br>open override fun [produce](produce.md)(): [Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html) |
| [set](set.md) | [jvm]<br>fun [set](set.md)(timeField: [Calendar.TimeField](-time-field/index.md), amount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Calendar](index.md) |
| [take](take.md) | [jvm]<br>fun [take](take.md)(duration: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)): [Calendar](index.md)<br>fun [take](take.md)(timeField: [Calendar.TimeField](-time-field/index.md), amount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Calendar](index.md) |
| [toString](to-string.md) | [jvm]<br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

## Properties

| Name | Summary |
|---|---|
| [isExpired](is-expired.md) | [jvm]<br>val [isExpired](is-expired.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [javaCalendar](java-calendar.md) | [jvm]<br>val [javaCalendar](java-calendar.md): [Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html) |
| [javaDate](java-date.md) | [jvm]<br>val [javaDate](java-date.md): [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) |
