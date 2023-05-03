plugins {
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.serialization") version "1.8.21"
    `maven-publish`
}

var host = "github.com/TheFruxz/Sparkle"

version = "2023.2-dev"
group = "dev.fruxz"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://papermc.io/repo/repository/maven-public/") // PaperMC
    maven("https://libraries.minecraft.net") // Minecraft (Brigadier)
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") // PlaceholderAPI
}

val ascendVersion = "3ca319bfda"
val stackedVersion = "74e454b253"

dependencies {

    // Internal

    api("com.github.TheFruxz:Ascend:$ascendVersion")
    api("com.github.TheFruxz:Stacked:$stackedVersion")
    api("com.github.TheFruxz:Kojang:1.0-RC2")

    // Kotlin

    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // External

    implementation("com.mojang:brigadier:1.0.500")
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT") // PaperMC

    implementation("io.ktor:ktor-client-cio:2.3.0")
    implementation("io.ktor:ktor-client-core-jvm:2.3.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.0")

    implementation("net.kyori:adventure-api:4.13.1")
    implementation("net.kyori:adventure-text-serializer-legacy:4.13.1")
    implementation("net.kyori:adventure-text-minimessage:4.13.1")
    implementation("net.kyori:adventure-text-serializer-gson:4.13.1")

}

tasks {

    processResources {
        expand(
            "version" to project.version,
            "name" to project.name,
            "website" to "https://$host"
        )
    }

    test {
        useJUnitPlatform()
    }

}

publishing {
    repositories {
        mavenLocal()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.$host")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
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
    jvmToolchain(17)
}