/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

plugins {
    id("java")
}

group = "xyz.mayahive.customdaytime"
version = "2.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val javaTarget = 21 // Sponge targets a minimum of Java 21
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(javaTarget))
}