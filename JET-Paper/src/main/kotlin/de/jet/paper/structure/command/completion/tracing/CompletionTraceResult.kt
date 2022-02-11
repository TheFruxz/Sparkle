package de.jet.paper.structure.command.completion.tracing

import de.jet.jvm.tree.TreeBranch
import de.jet.paper.structure.command.completion.InterchangeStructure
import de.jet.paper.structure.command.completion.tracing.CompletionTraceResult.Conclusion.*

data class CompletionTraceResult<T : TreeBranch<*, *, *>>(
	val waysMatching: List<PossibleTraceWay<T>>, // the possible ways to execute the trace
	val waysIncomplete: List<PossibleTraceWay<T>>, // the possible, but uncompleted ways to execute the trace - more input is needed to complete the trace
	val waysOverflow: List<PossibleTraceWay<T>>, // the arguments are too many, but the whole trace way path matches to the input
	val waysFailed: List<PossibleTraceWay<T>>, // the ways that died during the trace and cannot be used with the query
	val waysNoDestination: List<PossibleTraceWay<T>>, // the ways that have no destination, but the whole trace way path matches to the input
	val traceBase: InterchangeStructure, // the base of the trace
	val executedQuery: List<String>, // the original query, that was used to execute the trace
) {

	val conclusion = if ((waysMatching + waysNoDestination).size == 1) {
		RESULT
	} else if (waysMatching.isEmpty()) {
		if (waysIncomplete.isEmpty() && waysOverflow.isEmpty() && waysFailed.isEmpty() && waysNoDestination.isEmpty()) {
			EMPTY
		} else {
			NO_RESULT
		}
	} else {
		MULTIPLE_RESULTS
	}

	enum class Conclusion {

		/**
		 * Found the perfect result.
		 * ***ONE POSSIBLE WAY***
		 */
		RESULT,

		/**
		 * Many possible results.
		 * ***MANY POSSIBLE WAYS***
		 */
		MULTIPLE_RESULTS,

		/**
		 * No result, invalid request.
		 * ***NO POSSIBLE WAY***
		 */
		NO_RESULT,

		/**
		 * No ways to trace found (0 possible ways), only possible if the traceBase has no sub-branches.
		 */
		EMPTY;

	}

}
