package de.jet.library.extension.data

val kotlinVersion = KotlinVersion.CURRENT

val javaVersion = "" + System.getProperty("java.version")

val javaVmVersion = "" + System.getProperty("java.vm.version")

val javaVmVendor = "" + System.getProperty("java.vm.vendor")

val javaVmName = "" + System.getProperty("java.vm.name")

val javaVmSpecificationVersion = "" + System.getProperty("java.vm.specification.version")

val javaLevel = javaVmSpecificationVersion.toIntOrNull() ?: 0

val javaVmSpecificationVendor = "" + System.getProperty("java.vm.specification.vendor")

val javaVmSpecificationName = "" + System.getProperty("java.vm.specification.name")

val javaRuntimeSpecificationVersion = "" + System.getProperty("java.specification.version")

val javaRuntimeSpecificationVendor = "" + System.getProperty("java.specification.vendor")

val javaRuntimeSpecificationName = "" + System.getProperty("java.specification.name")