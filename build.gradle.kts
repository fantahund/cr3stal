plugins {
    id("java")
    id("net.fabricmc.fabric-loom") version "1.17.20" apply false
    id("net.neoforged.moddev") version "2.0.147" apply false
    id("net.minecraftforge.gradle") version "7.0.17" apply false
}

extra["minecraftVersion"] = "26.3"
extra["modVersion"] = "1.1.0"
extra["fabricLoaderVersion"] = "0.19.5"
extra["fabricApiVersion"] = "0.160.5+26.3"
extra["neoforgeVersion"] = "26.3.0.0-beta"
extra["forgeVersion"] = "66.0.0"

extra["fullVersion"] = "${extra["minecraftVersion"]}-${extra["modVersion"]}"

tasks.jar {
    enabled = false
}

allprojects {
    apply(plugin = "java")

    group = "com.rebane2001.cr3stal"
    version = rootProject.extra["fullVersion"] as String

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(25))

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(25)
    }
}
