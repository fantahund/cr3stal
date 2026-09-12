plugins {
    id("java")
    id("net.fabricmc.fabric-loom") version "1.17.20" apply false
    id("net.neoforged.moddev") version "2.0.141" apply false
    id("net.minecraftforge.gradle") version "7.0.17" apply false
}

extra["minecraftVersion"] = "26.2"
extra["modVersion"] = "1.0.0"
extra["fabricLoaderVersion"] = "0.19.5"
extra["fabricApiVersion"] = "0.160.0+26.2"
extra["neoforgeVersion"] = "26.2.0.0-beta"
extra["forgeVersion"] = "65.0.0"

tasks.jar {
    enabled = false
}

allprojects {
    apply(plugin = "java")
    
    group = "com.rebane2001.cr3stal"
    version = rootProject.extra["modVersion"] as String

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(25))

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(25)
    }
}
