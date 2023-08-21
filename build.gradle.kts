plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
    `maven-publish`
}

var host = "github.com/TheFruxz/Sparkle"

version = "2023.3-dev"
group = "dev.fruxz"

repositories {

    mavenCentral()

    maven("https://jitpack.io") {
        name = "JitPack"
    }

    maven("https://papermc.io/repo/repository/maven-public/") {
        name = "PaperMC"
    }

    maven("https://libraries.minecraft.net") {
        name = "Minecraft Libraries"
    }

}

val ascendVersion = "2023.3"
val stackedVersion = "2023.3"
val kojangVersion = "1.0"
val serializationVersion = "1.5.1"
val coroutinesVersion = "1.7.1"
val brigadierVersion = "1.0.500"
val paperVersion = "1.20.1-R0.1-SNAPSHOT"
val ktorVersion = "2.3.1"

dependencies {

    // Internal

    api("com.github.TheFruxz:Ascend:$ascendVersion")
    api("com.github.TheFruxz:Stacked:$stackedVersion")
    api("com.github.TheFruxz:Kojang:$kojangVersion")

    // Kotlin

    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    // External

    implementation("com.mojang:brigadier:$brigadierVersion")
    compileOnly("io.papermc.paper:paper-api:$paperVersion") // PaperMC

    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")

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