package de.jet.jvm.tool.timing.calendar.timeunit

import java.util.Calendar as JavaUtilCalendar

interface TimeUnit {

    val javaField: Int

    companion object {

        val ERA = object : TimeUnit {
            override val javaField: Int = JavaUtilCalendar.ERA
        }

        val YEAR = object : TimeUnit {
            override val javaField: Int = JavaUtilCalendar.YEAR
        }

        val MONTH = object : TimeUnit {
            override val javaField: Int = JavaUtilCalendar.MONTH
        }

        val HOUR = object : TimeUnit {
            override val javaField: Int = JavaUtilCalendar.HOUR
        }

        val MINUTE = object : TimeUnit {
            override val javaField: Int = JavaUtilCalendar.MINUTE
        }

        val SECOND = object : TimeUnit {
            override val javaField: Int = JavaUtilCalendar.SECOND
        }

        val MILLISECOND = object : TimeUnit {
            override val javaField: Int = JavaUtilCalendar.MILLISECOND
        }

    }

}