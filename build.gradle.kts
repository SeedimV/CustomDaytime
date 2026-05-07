/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

subprojects {

    group = "xyz.mayahive.customdaytime"
    version = "2.1.0"

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }

    if (name != "api") {
        plugins.withId("java") {
            dependencies {
                add("compileOnly", libs.lombok)
                add("annotationProcessor", libs.lombok)
            }
        }
    }
}