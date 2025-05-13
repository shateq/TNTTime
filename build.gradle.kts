plugins {
    `java-library`
    id("fabric-loom")
    id("com.modrinth.minotaur")
}

fun p(key: String): String = properties[key] as String

version = "1.4.1"
base.archivesName.set("${rootProject.name}-mc${p("minecraft")}")
description = "A countdown to the detonation of a TNT."

repositories {
    //maven("https://jitpack.io")
    //maven("https://maven.terraformersmc.com/releases/")
}

dependencies {
    minecraft("com.mojang:minecraft:${p("minecraft")}")
    mappings("net.fabricmc:yarn:${p("yarn")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${p("loader")}")

    //modRuntimeOnly("com.terraformersmc:modmenu:5.0.2")
    //modRuntimeOnly("com.github.astei:lazydfu:0.1.3")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    withSourcesJar()
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    jar {
        from("LICENSE") {
            rename { "LICENSE_${rootProject.name}" }
        }
    }

    processResources {
        filteringCharset = "UTF-8"

        filesMatching("fabric.mod.json") {
            expand(
                "version" to project.version
            )
        }
    }
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))
    projectId.set("tnttime")
    versionNumber.set("mc$version")

    versionName.set("Tnt Time $version for 1.21.4")

    versionType.set("release")
    gameVersions.addAll("1.21.4")
    uploadFile.set(tasks["remapJar"])
}
