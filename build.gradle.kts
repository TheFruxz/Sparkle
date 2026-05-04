plugins {
    kotlin("jvm") version "2.3.21"
    kotlin("plugin.serialization") version "2.2.0"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.18"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    `maven-publish`
}

var host = "github.com/TheFruxz/Sparkle"
val includedDependencies = mutableListOf<String>()

version = "2025.7-dev"
group = "dev.fruxz"

@DslMarker
annotation class DependencyDSL

@DependencyDSL
fun Dependency?.include() = this?.apply {
    val computedVersion = version ?: kotlin.coreLibrariesVersion
    includedDependencies += "${group}:${name}:${computedVersion}"
}

repositories {

    mavenCentral()

    maven("https://nexus.fruxz.dev/repository/public/") {
        name = "fruxz.dev"
    }

    maven("https://libraries.minecraft.net") {
        name = "Minecraft Libraries"
    }

}

dependencies {

    // Internal

    api("dev.fruxz:ascend:2025.7-8af65e5").include()
    api("dev.fruxz:stacked:2025.5-3733615").include()
    api("dev.fruxz:brigadikt:2025.4-48276a1-preview").include()
    api("dev.fruxz:kojang:1.1.2").include()

    // Kotlin

    implementation(kotlin("stdlib")).include()
    implementation(kotlin("reflect")).include()
    testImplementation(kotlin("test"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    // External

    paperweight.paperDevBundle("1.21.7-R0.1-SNAPSHOT")
    implementation("com.mojang:brigadier:1.0.18")

    implementation("io.ktor:ktor-client-cio:3.1.1").include()
    implementation("io.ktor:ktor-client-core-jvm:3.1.1").include()
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.1").include()
    implementation("io.ktor:ktor-client-content-negotiation:3.1.1").include()

}

tasks {

    processResources {
        expand(
            "version" to project.version,
            "name" to project.name,
            "website" to "https://$host",
        )
    }

    test {
        useJUnitPlatform()
    }

    runServer {
        this.minecraftVersion("1.21.7")
    }

    val generateRepositoriesFile = register("generateRepositoriesFile") {
        val outputFile = file(layout.buildDirectory.dir("generated/resources/repositories.list"))
        doLast {
            outputFile.parentFile.mkdirs()
            outputFile.writeText(repositories.filterIsInstance<MavenArtifactRepository>().map { it.url }.joinToString("\n"))
        }
    }

    val generateDependenciesFile = register("generateDependenciesFile") {
        val outputFile = file(layout.buildDirectory.dir("generated/resources/dependencies.list"))
        doLast {
            outputFile.parentFile.mkdirs()
            outputFile.writeText(includedDependencies.joinToString("\n"))
        }
    }

    jar {
        dependsOn(generateRepositoriesFile)
        dependsOn(generateDependenciesFile)
        from(layout.buildDirectory.dir("generated/resources/dependencies.list")) {
            into("") // Top-Level der JAR
        }
        from(layout.buildDirectory.dir("generated/resources/repositories.list")) {
            into("") // Top-Level der JAR
        }
    }

}

publishing {

    repositories {
        mavenLocal()
    }

    publications.create("Sparkle", MavenPublication::class) {

        from(components["kotlin"])

        artifactId = "sparkle"
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
    jvmToolchain(21)
}