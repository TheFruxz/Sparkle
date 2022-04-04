package de.jet.jvm.tool.timing.calendar.timeUnit

import java.util.Calendar as JavaUtilCalendar

interface TimeUnit {

    val javaField: Int

    companion object {

        @JvmStatic
        val ERA = object : TimeUnit {
            override val javaField: Int = JavaUtilCalendar.ERA
        }

        @JvmStatic
        val YEAR = object : TimeUnit {
            override val javaField: Int = JavaUtilCalendar.YEAR
        }

        @JvmStatic
        val MONTH = object : TimeUnit {
            override val javaField: Int = JavaUtilCalendar.MONTH
        }

        @JvmStatic
        val HOUR = object : TimeUnit {
            override val javaField: Int = JavaUtilCalendar.HOUR_OF_DAY
        }

        @JvmStatic
        val MINUTE = object : TimeUnit {
            override val javaField: Int = JavaUtilCalendar.MINUTE
        }

        @JvmStatic
        val SECOND = object : TimeUnit {
            override val javaField: Int = JavaUtilCalendar.SECOND
        }

        @JvmStatic
        val MILLISECOND = object : TimeUnit {
            override val javaField: Int = JavaUtilCalendar.MILLISECOND
        }

    }

}