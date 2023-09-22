plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("io.papermc.paperweight.userdev")
    `maven-publish`
}

group = "${parent!!.group}.sparkle"
version = parent!!.version

repositories {
    mavenCentral()

    maven("https://jitpack.io") {
        name = "JitPack"
    }

    maven("https://libraries.minecraft.net") {
        name = "Minecraft Libraries"
    }

}

val minecraftVersion: String by project
val ascendVersion: String by project
val stackedVersion: String by project
val kojangVersion: String by project
val brigadiktVersion: String by project
val serializationVersion: String by project
val coroutinesVersion: String by project
val brigadierVersion: String by project
val ktorVersion: String by project

dependencies {

    // Internal

    api("com.github.TheFruxz:Ascend:$ascendVersion")
    api("com.github.TheFruxz:Stacked:$stackedVersion")
    api("com.github.TheFruxz:Kojang:$kojangVersion")
    api("com.github.TheFruxz:BrigadiKt:$brigadiktVersion")

    // Kotlin

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // External

    paperweight.paperDevBundle("$minecraftVersion-R0.1-SNAPSHOT")
    implementation("com.mojang:brigadier:$brigadierVersion")

    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")

}

tasks.test {
    useJUnitPlatform()
}

publishing {

    repositories {
        mavenLocal()
    }

    publications.create("SparkleCore", MavenPublication::class) {

        from(components["kotlin"])

        artifactId = "sparkle-core"
        version = version.lowercase()

    }

}

configure<SourceSetContainer> { // allowing java files appearing next to kotlin files
    named("main") {
        java.srcDir("src/main/kotlin")
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

kotlin {
    jvmToolchain(17)
}