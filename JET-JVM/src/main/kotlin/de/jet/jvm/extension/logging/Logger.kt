package de.jet.jvm.extension.logging

import kotlin.reflect.KClass
import java.util.logging.Logger as JavaUtilLogger

fun getLogger(name: String): JavaUtilLogger = JavaUtilLogger.getLogger(name)

fun getLogger(name: String, resourceBundleName: String): JavaUtilLogger = JavaUtilLogger.getLogger(name, resourceBundleName)

fun getLogger(kclass: KClass<*>) =
	getLogger(kclass.simpleName ?: "generic")

fun <T : Any> T.getItsLogger() = getLogger(this::class)
