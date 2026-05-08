/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

pluginManagement {
    repositories {
        maven {
            url = uri("https://repo.spongepowered.org/repository/maven-public/")
        }
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/") {
            name = "papermc-repo"
        }
        maven("https://repo.spongepowered.org/repository/maven-public/") {
            name = "spongepowered-repo"
        }
    }
}

rootProject.name = "customdaytime"
include("api")
include("common")
include("paper")
include("sponge")