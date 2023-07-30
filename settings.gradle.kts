pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
    }

    plugins {
        id("fabric-loom") version "1.3-SNAPSHOT"
        id("com.modrinth.minotaur") version "2.+"
    }
}

rootProject.name = "tnttime"
