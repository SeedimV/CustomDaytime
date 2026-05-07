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
}

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    implementation(project(":common"))

    implementation(libs.bstats.bukkit)

    compileOnly(libs.paper.api.get())
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

    shadowJar {
        relocate("org.bstats", "xyz.mayahive.libs.bstats")
        relocate("org.spongepowered.configurate", "xyz.mayahive.customdaytime.lib.configurate")
        relocate("net.kyori.option", "xyz.mayahive.customdaytime.lib.kyori.option")
    }
}

paper {
    name = "CustomDaytime"
    author = "Seedim"
    main = "xyz.mayahive.customdaytime.paper.CustomDaytimePaper"
    apiVersion = "26.1.2"
    foliaSupported = true
    contributors = listOf("PureLove")
}