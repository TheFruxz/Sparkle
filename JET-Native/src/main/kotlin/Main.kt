import de.jet.library.extension.input.buildConsoleInterchange

fun main() {

    buildConsoleInterchange("test") {

        branch("1")
        branch("2")
        branch("3")
        branch("4") {

            branch("5") {

                content {
                    println("Hell World $it")
                }

            }
            branch("6") {

                branch("7")
                branch("8")
                branch("9") {

                    content {
                        println("afsddas")
                    }

                }

            }
            branch("7")
            branch("8")
            branch("9")

            content {
                println("none!")
            }

        }

    }.let {

        it.performInterchange("test test 4 6 9 t")

    }

}