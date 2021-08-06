package de.jet.app

import de.jet.app.JetData.File.CONFIG
import de.jet.library.extension.data.jetPath
import de.jet.library.tool.data.JetFile
import de.jet.library.tool.data.Preference

object JetData {

	val systemPrefix = Preference(
		file = CONFIG,
		useCache = true,
		path = jetPath("prefix"),
		default = "§6JET §8» ",
	)

	val systemLanguage = Preference(
		file = CONFIG,
		path = jetPath("language"),
		default = "en_general",
	)

	object File {

		val CONFIG = JetFile.rootFile("system-config")

	}

}