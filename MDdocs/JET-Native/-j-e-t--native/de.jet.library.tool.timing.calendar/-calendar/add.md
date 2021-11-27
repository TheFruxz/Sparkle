//[JET-Native](../../../index.md)/[de.jet.library.tool.timing.calendar](../index.md)/[Calendar](index.md)/[add](add.md)

# add

[jvm]\
fun [add](add.md)(timeField: [Calendar.TimeField](-time-field/index.md), amount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Calendar](index.md)

This function adds some time to the calendar. It takes the [amount](add.md), takes the time unit [timeField](add.md) and adds it to the calendar.

#### Return

the calendar itself

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| timeField | the unit of time which should be added |
| amount | the amount of time which should be added |

[jvm]\
fun [add](add.md)(duration: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)): [Calendar](index.md)

This function adds some time to the calendar. It takes the time from the [duration](add.md) and adds it to the calendar using the internal units.

#### Return

the calendar itself

#### Author

Fruxz

#### Since

1.0

## Parameters

jvm

| | |
|---|---|
| duration | the amount of time which should be added |
