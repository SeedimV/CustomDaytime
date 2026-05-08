/*
 *     Copyright (c) 2026 Seedim
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
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
