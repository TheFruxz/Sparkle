package dev.fruxz.sparkle.framework

class TestPlugin : SparklePlugin({

    onEnable {
        println("TestPlugin was enabled!")
    }

    command<TestCommand>()

})