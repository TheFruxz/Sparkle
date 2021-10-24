package de.jet.library.tool.conversion

object CSV {

	fun convertCSV(csvContent: String): List<Array<String>> = buildList {
		csvContent.lines().forEach {
			add(it.split(";").toTypedArray())
		}
	}

}