plugins {
    id("net.fabricmc.fabric-loom")
}

val minecraftVersion = rootProject.extra["minecraftVersion"] as String
val fabricLoaderVersion = rootProject.extra["fabricLoaderVersion"] as String

dependencies {
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    compileOnly("net.fabricmc:fabric-loader:${fabricLoaderVersion}")
    compileOnly("net.fabricmc:sponge-mixin:0.17.4+mixin.0.8.7")
}

loom {
    mods {
        create("main") {
            sourceSet(sourceSets.main.get())
        }
    }
}
