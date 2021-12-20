package de.jet.jvm.tool.data

/**
 * This class helps to easily manage tabular data.
 * @param headers the headers of the table, which are used to identify the columns.
 * @param rows the rows of the table, which are used to store the data.
 * @author Fruxz
 * @since 1.0
 */
data class TabularData(
	val headers: List<String> = listOf(),
	val rows: List<List<String>> = listOf(),
) {

	constructor(rawCSV: String) : this(
		headers = rawCSV.lines().first().split(",").map { it.trim() },
		rows = rawCSV.lines().drop(1).map { it.split(",").map { it.trim() } },
	)

	/**
	 * Returns the data from the given column as a list of strings.
	 * @param column the name of the column
	 * @author Fruxz
	 * @since 1.0
	 */
	fun getDataFromColumn(column: String): List<String> {
		return rows.map { it[headers.indexOf(column)] }
	}

}
