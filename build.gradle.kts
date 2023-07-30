plugins {
    id("fabric-loom")
    id("com.modrinth.minotaur")
}

version = "1.2.0"
base.archivesName = "${project.name}-$version-mc${project.minecraft}"
description = "A countdown to the detonation of Minecraft TNT."

repositories {
    maven { url "https://jitpack.io" }
    maven { url "https://maven.terraformersmc.com/releases/" }
}

dependencies {
    minecraft("com.mojang:minecraft:${project.minecraft}")
    mappings("net.fabricmc:yarn:${project.yarn}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.loader}")

    modRuntimeOnly("com.terraformersmc:modmenu:5.0.2")
    //modRuntimeOnly("com.github.astei:lazydfu:0.1.3")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    withSourcesJar()
}

tasks {
    loom.mixin.defaultRefmapName.set("tnttime.refmap.json")
    compileJava {
        options.encoding = "UTF-8" // Must have!
        options.release.set(17)
    }
    jar {
        manifest.attributes("Implementation-Version": rootProject.version)
        from("LICENSE") {
            rename { "LICENSE_${project.name}" }
        }
    }
    processResources {
        filteringCharset = "UTF-8"
        filesMatching("fabric.mod.json") {
            expand("version": version)
        }
    }
}

modrinth {
    token = System.getenv("MODRINTH_TOKEN")
    projectId = "tnttime"
    gameVersions = ["1.19.3"]
    versionName = "Tnt Time for MC 1.19.3"
    versionNumber = project.version
    versionType = "release"
    uploadFile = remapJar
}
