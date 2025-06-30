plugins {
    kotlin("jvm") version "2.1.20"
    application
    maven-publish
    java-library
}

group = "com.duckplay.tunnel"
version = "0.0.1-beta"

repositories {
    mavenCentral()
}

publishing {
    publications {
        maven(MavenPublication) {
            groupId = 'com.duckplay.tunnel'
            artifactId = 'tunnel'
            version = '0.0.1-beta'

            from components.java
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ssimiao/abacatepay-kotlin-sdk")
            credentials {
                username = System.getenv("USERNAME")
                password = System.getenv("TOKEN")
            }
        }
    }
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
