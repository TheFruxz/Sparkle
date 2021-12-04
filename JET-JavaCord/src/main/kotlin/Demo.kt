import de.jet.jvm.extension.input.buildConsoleInterchange
import de.jet.jvm.extension.input.requestTerminalInterchangeInput

fun main() {

	requestTerminalInterchangeInput(buildConsoleInterchange("test") {

		content {
			println("root content with $it")
		}

		branch("test") {
			content {
				println("test content with $it")
			}
		}

		branch("demo") {
			content {
				println("demo content with $it")
			}
		}

	})

}