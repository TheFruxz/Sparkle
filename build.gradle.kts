plugins {
    kotlin("jvm") version "1.9.20-Beta"
    kotlin("plugin.serialization") version "1.9.0"
    id("io.papermc.paperweight.userdev") version "1.5.5"
    `maven-publish`
}

version = "2023.3-dev"
group = "dev.fruxz"

val minecraftVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("$minecraftVersion-R0.1-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}