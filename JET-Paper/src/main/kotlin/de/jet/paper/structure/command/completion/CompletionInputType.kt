package de.jet.paper.structure.command.completion

import de.jet.jvm.extension.math.isDouble
import de.jet.jvm.extension.math.isLong

enum class CompletionInputType(val check: ((String) -> Boolean)?) {

	NONE(null),
	STRING(null),
	LONG({ it.isLong() }),
	DOUBLE({ it.isDouble() }),
	ANY(null);

}