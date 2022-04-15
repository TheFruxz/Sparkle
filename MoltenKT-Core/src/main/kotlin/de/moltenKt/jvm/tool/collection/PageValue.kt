package de.moltenKt.jvm.tool.collection

data class PageValue<T>(
	val content: List<T>,
	val page: Int,
	val pages: Int,
)
