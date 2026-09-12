pluginManagement {
    repositories {
        mavenCentral()
        maven(url = "https://maven.fabricmc.net/") { name = "Fabric" }
        maven(url = "https://maven.neoforged.net/releases") { name = "NeoForge" }
        maven(url = "https://maven.minecraftforge.net/") { name = "MinecraftForge" }
        gradlePluginPortal()
    }
}

include("common")
include("fabric")
include("forge")
include("neoforge")
