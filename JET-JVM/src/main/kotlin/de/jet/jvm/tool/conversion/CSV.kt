package de.jet.jvm.tool.conversion

/**
 * This object provides methods to convert a CSV file to a list of arrays of strings.
 * @author Fruxz
 * @since 1.0
 */
object CSV {

	/**
	 * Basically converts a CSV content to a list of arrays of strings.
	 * @param csvContent The CSV content to convert.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun convertCSV(csvContent: String): List<Array<String>> = buildList {
		csvContent.lines().forEach {
			add(it.split(";").toTypedArray())
		}
	}

}