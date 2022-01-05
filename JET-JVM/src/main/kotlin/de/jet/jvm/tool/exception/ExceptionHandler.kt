package de.jet.jvm.tool.exception

data class ExceptionHandler<O>(
    var tryBlock: () -> O,
    var catchBlock: ((exception: Exception) -> O)?,
) {

    infix fun doTry(tryBlock: () -> O) = apply {
        this.tryBlock = tryBlock
    }

    infix fun doCatch(catchBlock: (Exception) -> O) = apply {
        this.catchBlock = catchBlock
    }

    fun doExecute() {
        try {
            tryBlock()
        } catch (exception: Exception) {
            catchBlock?.let { it(exception) }
        }
    }

}