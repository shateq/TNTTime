pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
    }

    plugins {
        id("fabric-loom") version "1.0-SNAPSHOT"
    }
}

rootProject.name = "tnttime"
