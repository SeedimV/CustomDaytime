/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    `java-library`
    alias(libs.plugins.spongegradle)
    alias(libs.plugins.spongevanilla)
    alias(libs.plugins.shadow)
}

dependencies {
    api(project(":common"))

    compileOnly(libs.configurate.hocon)
    compileOnly(libs.gson)
}

sponge {
    apiVersion(libs.versions.sponge.api.get())
    minecraftVersion(libs.versions.spongeminecraft.get())
    license("GPL-3.0")
    loader {
        name(PluginLoaders.JAVA_PLAIN)
        version("2.0.0")
    }
    plugin("customdaytime") {
        displayName("Custom Daytime")
        entrypoint("xyz.mayahive.customdaytime.sponge.CustomDaytimeSponge")
        description("Customise Minecraft's day-night-cycle")
        links {
            source("https://github.com/SeedimV/CustomDaytime/")
            issues("https://github.com/SeedimV/CustomDaytime/issues")
        }
        contributor("Seedim") {
            description("Author")
        }
        dependency("spongeapi") {
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
    }
}

val javaTarget = 21 // Sponge targets a minimum of Java 21
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(javaTarget))
}

tasks.withType<JavaCompile>().configureEach {
    options.apply {
        encoding = "utf-8" // Consistent source file encoding
        if (JavaVersion.current().isJava10Compatible) {
            release.set(javaTarget)
        }
    }
}

// Make sure all tasks which produce archives (jar, sources jar, javadoc jar, etc) produce more consistent output
tasks.withType<AbstractArchiveTask>().configureEach {
    isReproducibleFileOrder = true
    isPreserveFileTimestamps = false
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
}

minecraft {
    version(libs.versions.spongeminecraft.get())
}
