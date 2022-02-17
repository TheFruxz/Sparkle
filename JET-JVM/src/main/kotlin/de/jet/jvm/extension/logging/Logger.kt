package de.jet.jvm.extension.logging

import org.slf4j.LoggerFactory
import kotlin.reflect.KClass
import java.util.logging.Logger as JavaUtilLogger

/**
 * Creates a logger with the given name.
 * @param name the name of the logger
 * @return the logger
 * @see JavaUtilLogger.getLogger
 * @author Fruxz
 * @since 1.0
 */
fun getLogger(name: String): JavaUtilLogger = JavaUtilLogger.getLogger(name)

/**
 * Creates a logger with the given name and resource bundle.
 * @param name the name of the logger
 * @param resourceBundleName the resource bundle
 * @return the logger
 * @see JavaUtilLogger.getLogger
 * @author Fruxz
 * @since 1.0
 */
fun getLogger(name: String, resourceBundleName: String): JavaUtilLogger = JavaUtilLogger.getLogger(name, resourceBundleName)

/**
 * Creates a logger from the name of given class.
 * @param kclass the class
 * @return the logger
 * @see JavaUtilLogger.getLogger
 * @author Fruxz
 * @since 1.0
 */
fun getLogger(kclass: KClass<*>) =
	getLogger(kclass.simpleName ?: "generic")

/**
 * Creates a logger from the [LoggerFactory] using the given name.
 * @param name the name of the logger
 * @return the logger
 * @see LoggerFactory.getLogger
 * @author Fruxz
 * @since 1.0
 */
fun getFactoryLogger(name: String) = LoggerFactory.getLogger(name)!!

/**
 * Creates a logger from the [LoggerFactory] using the given class.
 * @param kclass the class
 * @return the logger
 * @see LoggerFactory.getLogger
 * @author Fruxz
 * @since 1.0
 */
fun getFactoryLogger(kclass: KClass<*>) = LoggerFactory.getLogger(kclass.java)!!

/**
 * Creates a logger from the [JavaUtilLogger] using the class of the given object.
 * @return the logger
 * @see getLogger
 * @author Fruxz
 * @since 1.0
 */
fun <T : Any> T.getItsLogger() = getLogger(this::class)

/**
 * Creates a logger from the [LoggerFactory] using the class of the given object.
 * @return the logger
 * @see getFactoryLogger
 * @author Fruxz
 * @since 1.0
 */
fun <T : Any> T.getItsFactoryLogger() = getFactoryLogger(this::class)
