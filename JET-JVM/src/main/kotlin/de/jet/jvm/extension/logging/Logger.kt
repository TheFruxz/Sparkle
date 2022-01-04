package de.jet.jvm.extension.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass
import java.util.logging.Logger as JavaUtilLogger

fun getLogger(kclass: KClass<*>): JavaUtilLogger = JavaUtilLogger.getLogger(kclass.simpleName)

fun getFactoryLogger(kclass: KClass<*>): Logger = LoggerFactory.getLogger(kclass.java)

fun <T : Any> T.getItsFactoryLogger() = getFactoryLogger(this::class)

fun <T : Any> T.getItsLogger() = getLogger(this::class)
