package de.jet.jvm.tool.conversion

/**
 * This object provides methods to convert a CSV file to a list of arrays of strings.
 */
object CSV {

	fun convertCSV(csvContent: String): List<Array<String>> = buildList {
		csvContent.lines().forEach {
			add(it.split(";").toTypedArray())
		}
	}

}