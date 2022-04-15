package de.moltenKt.jvm.extension.data

/**
 * Returning the currently used kotlin runtime version.
 * @author Fruxz
 * @since 1.0
 */
val kotlinVersion = KotlinVersion.CURRENT

/**
 * Returning the currently used java version, which the
 * [System].getProperty("java.version") returns.
 * @author Fruxz
 * @since 1.0
 */
val javaVersion = "" + System.getProperty("java.version")

/**
 * Returning the currently used java jvm version, which the
 * [System].getProperty("java.jvm.version") returns.
 * @author Fruxz
 * @since 1.0
 */
val javaVmVersion = "" + System.getProperty("java.vm.version")

/**
 * Returning the currently used java jvm vendor, which the
 * [System].getProperty("java.jvm.vendor") returns.
 * @author Fruxz
 * @since 1.0
 */
val javaVmVendor = "" + System.getProperty("java.vm.vendor")

/**
 * Returning the currently used java jvm name, which the
 * [System].getProperty("java.jvm.name") returns.
 * @author Fruxz
 * @since 1.0
 */
val javaVmName = "" + System.getProperty("java.vm.name")

/**
 * Returning the currently used java vm specification version, which the
 * [System].getProperty("java.vm.specification.version") returns.
 * @author Fruxz
 * @since 1.0
 */
val javaVmSpecificationVersion = "" + System.getProperty("java.vm.specification.version")

/**
 * Returning the currently used java level or 0 if the java level
 * is not a number.
 */
val javaLevel = javaVmSpecificationVersion.toIntOrNull() ?: 0

/**
 * Returning the currently used java vm specification vendor, which the
 * [System].getProperty("java.vm.specification.vendor") returns.
 * @author Fruxz
 * @since 1.0
 */
val javaVmSpecificationVendor = "" + System.getProperty("java.vm.specification.vendor")

/**
 * Returning the currently used java vm specification name, which the
 * [System].getProperty("java.vm.specification.name") returns.
 * @author Fruxz
 * @since 1.0
 */
val javaVmSpecificationName = "" + System.getProperty("java.vm.specification.name")

/**
 * Returning the currently used java specification version, which the
 * [System].getProperty("java.specification.version") returns.
 * @author Fruxz
 * @since 1.0
 */
val javaRuntimeSpecificationVersion = "" + System.getProperty("java.specification.version")

/**
 * Returning the currently used java specification vendor, which the
 * [System].getProperty("java.specification.vendor") returns.
 * @author Fruxz
 * @since 1.0
 */
val javaRuntimeSpecificationVendor = "" + System.getProperty("java.specification.vendor")

/**
 * Returning the currently used java specification name, which the
 * [System].getProperty("java.specification.name") returns.
 * @author Fruxz
 * @since 1.0
 */
val javaRuntimeSpecificationName = "" + System.getProperty("java.specification.name")