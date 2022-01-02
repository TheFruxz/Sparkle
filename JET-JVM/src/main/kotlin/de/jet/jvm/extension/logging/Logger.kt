package de.jet.jvm.extension.logging

import de.jet.jvm.extension.tryOrNull
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

fun getLogger(kclass: KClass<*>): java.util.logging.Logger? = tryOrNull { java.util.logging.Logger.getLogger(kclass.qualifiedName) }

fun getFactoryLogger(kclass: KClass<*>): Logger = LoggerFactory.getLogger(kclass.java)

fun <T : Any> T.getItsFactoryLogger() = getFactoryLogger(this::class)

fun <T : Any> T.getItsLogger() = getLogger(this::class)
