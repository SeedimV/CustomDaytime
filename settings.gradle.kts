pluginManagement {
    repositories {
        maven {
            url = uri("https://repo.spongepowered.org/repository/maven-public/")
        }
    }
}

rootProject.name = "customdaytime"
include("api")
include("common")
include("paper")
include("sponge")