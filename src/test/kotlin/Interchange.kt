import de.jet.library.extension.collection.mapToString
import de.jet.library.structure.command.CompletionVariable
import de.jet.library.structure.command.buildCompletion
import de.jet.library.structure.command.infinite
import de.jet.library.structure.command.isRequired
import de.jet.library.structure.command.label
import de.jet.library.structure.command.mustMatchOutput
import de.jet.library.structure.command.next
import de.jet.library.structure.command.plus
import de.jet.library.structure.smart.Identifiable
import org.junit.jupiter.api.Test

internal class InterchangeTest {

	@Test
	fun `Interchange Completion Test `() {

		val randoms = (0..200).shuffled().take(5)

		val completionVariable = CompletionVariable(
			vendor = Identifiable.dummy(),
			label = "demo",
			refreshing = false,
			generator = { randoms.mapToString() }
		)

		val completion =
			buildCompletion {
				this next "This" plus "is" plus "the" plus "Test!"
				this next "The" plus "Numbers:" plus completionVariable
				this next "WOW!"
			}

		val first = completion.sections.flatMap { completionComponentSection -> completionComponentSection.components.flatMap { it.completion() } }.joinToString(" ")
		val second = "This is the Test! The Numbers: ${randoms.joinToString(" ")} WOW!"

		assert(first == second) { "The completion went wrong, '$first' is not '$second'!" }

		println("""
			| CHECK SUCCESS:
			| 
			| '$first'
			| -> equals ->
			| '$second'
			|
		""".trimIndent())

	}

}