package de.jet.jvm.extension.logging

import kotlin.reflect.KClass
import java.util.logging.Logger as JavaUtilLogger

fun getLogger(kclass: KClass<*>): JavaUtilLogger = JavaUtilLogger.getLogger(kclass.simpleName)

fun <T : Any> T.getItsLogger() = getLogger(this::class)
