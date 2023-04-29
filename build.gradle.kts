

plugins {
    kotlin("jvm") version "1.8.20"
    kotlin("plugin.serialization") version "1.8.20"
    id("org.jetbrains.dokka") version "1.8.10"
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

val ascendVersion = "ba6c401235"
val stackedVersion = "74e454b253"

dependencies {

    // Internal

    api("com.github.TheFruxz:Ascend:$ascendVersion")
    api("com.github.TheFruxz:Stacked:$stackedVersion")

    // Kotlin

    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // External

    implementation("com.mojang:brigadier:1.0.500")
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT") // PaperMC

    // TODO -> Was shadowed

    implementation("io.ktor:ktor-client-cio:2.2.4")
    implementation("io.ktor:ktor-client-core-jvm:2.2.4")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.4")
    implementation("io.ktor:ktor-client-content-negotiation:2.2.4")

    implementation("net.kyori:adventure-api:4.12.0")
    implementation("net.kyori:adventure-text-serializer-legacy:4.13.0")
    implementation("net.kyori:adventure-text-minimessage:4.12.0")
    implementation("net.kyori:adventure-text-serializer-gson:4.12.0")

    // TODO end

    // Shadow

    // TODO implement in loader
//    shadow("com.github.TheFruxz:Ascend:$ascendVersion") {
//        isTransitive = false
//    }
//    shadow("com.github.TheFruxz:Stacked:$stackedVersion") {
//        isTransitive = false
//    }
//
//    shadow(kotlin("stdlib"))
//    shadow(kotlin("reflect"))
//
//    shadow("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
//    shadow("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
//
//    shadow("net.kyori:adventure-text-serializer-plain:4.13.0")
//
//    shadow("org.jetbrains.exposed:exposed-core:0.41.1")
//    shadow("org.jetbrains.exposed:exposed-dao:0.41.1")
//    shadow("org.jetbrains.exposed:exposed-jdbc:0.41.1")

}

tasks {


    dokkaHtml.configure {
        outputDirectory.set(buildDir.resolve("../docs/"))
    }

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

val dokkaJavadocJar by tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

val dokkaHtmlJar by tasks.register<Jar>("dokkaHtmlJar") {
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml.flatMap { it.outputDirectory })
    archiveClassifier.set("html-doc")
}

//val source by tasks.register<Jar>("sourceJar") {
//    from(sourceSets.main.get().allSource)
//    archiveClassifier.set("sources")
//}

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

java {
    withJavadocJar()
    withSourcesJar()
}

configure<SourceSetContainer> {
    named("main") {
        java.srcDir("src/main/kotlin")
    }
}

kotlin {
    jvmToolchain(17)
}