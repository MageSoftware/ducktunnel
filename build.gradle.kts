plugins {
    kotlin("jvm") version "2.1.20"
    application
}

group = "com.duckplay.tunnel"
version = "1.0"

repositories {
    mavenCentral()
}

application {
    mainClass.set("MainKt") // Correct main class for Main.kt
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}