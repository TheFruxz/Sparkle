//[JET-Native](../../../index.md)/[de.jet.library.tool.timing.calendar](../index.md)/[Calendar](index.md)/[take](take.md)

# take

[jvm]\
fun [take](take.md)(timeField: [Calendar.TimeField](-time-field/index.md), amount: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Calendar](index.md)

This function takes some time from the calendar. It takes the [amount](take.md), takes the time unit [timeField](take.md) and takes it from the calendar.

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
| timeField | the unit of time which should be taken |
| amount | the amount of time which should be taken |

[jvm]\
fun [take](take.md)(duration: [Duration](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.time/-duration/index.html)): [Calendar](index.md)

This function takes some time from the calendar. It takes the time from the [duration](take.md) and takes it from the calendar using the internal units.

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
| duration | the amount of time which should be taken |
