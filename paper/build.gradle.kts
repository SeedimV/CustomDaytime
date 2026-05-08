/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

plugins {
    id("java")
    alias(libs.plugins.run.paper)
    alias(libs.plugins.shadow)
    alias(libs.plugins.plugin.yml)
    alias(libs.plugins.minotaur)
}

dependencies {
    implementation(project(":common"))

    implementation(libs.bstats.bukkit)
    implementation(libs.configurate.hocon)

    compileOnly(libs.paper.api)
    compileOnly(libs.gson)
}

tasks {
  runServer {
    // Configure the Minecraft version for our task.
    // This is the only required configuration besides applying the plugin.
    // Your plugin's jar (or shadowJar if present) will be used automatically.
    minecraftVersion("26.1.2")
  }
}

val targetJavaVersion = 25
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"

    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
    }
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }

    jar {
        enabled = false
    }

    shadowJar {
        archiveBaseName.set("CustomDaytimePaper")
        archiveClassifier.set("")
        relocate("org.bstats", "xyz.mayahive.libs.bstats")
        relocate("org.spongepowered.configurate", "xyz.mayahive.customdaytime.lib.configurate")
        relocate("net.kyori.option", "xyz.mayahive.customdaytime.lib.kyori.option")
        relocate("io.leangen.geantyref", "xyz.mayahive.customdaytime.lib.geantyref")
    }
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))
    projectId.set("C7YliNqw")
    versionNumber.set(version.toString())
    versionType.set("release")
    uploadFile.set(tasks.shadowJar)
    gameVersions.addAll("26.1", "26.1.1", "26.1.2")
    loaders.addAll("paper", "folia", "purpur")
    syncBodyFrom = rootProject.file("README.md").readText()
    changelog.set(System.getenv("CHANGELOG").takeUnless { it.isNullOrBlank() } ?: "No changelog provided")
}

tasks.modrinth {
    dependsOn(tasks.modrinthSyncBody)
}

paper {
    name = "CustomDaytime"
    author = "Seedim"
    main = "xyz.mayahive.customdaytime.paper.CustomDaytimePaper"
    apiVersion = "26.1.2"
    foliaSupported = true
    contributors = listOf("PureLove")
}