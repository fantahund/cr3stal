plugins {
    id("net.fabricmc.fabric-loom")
}

val minecraftVersion = rootProject.extra["minecraftVersion"] as String
val fabricLoaderVersion = rootProject.extra["fabricLoaderVersion"] as String
val fullVersion = rootProject.extra["fullVersion"] as String

base {
    archivesName.set("cr3stal-fabric")
}

dependencies {
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    implementation("net.fabricmc:fabric-loader:${fabricLoaderVersion}")
    
    implementation(project.project(":common").sourceSets.getByName("main").output)
}

loom {
    runs {
        named("client") {
            client()
            configName = "Fabric Client"
            ideConfigGenerated(true)
            runDir("run")
        }
    }
}

tasks.processResources {
    from(project.project(":common").sourceSets.getByName("main").resources)
    inputs.property("version", fullVersion)

    filesMatching("fabric.mod.json") {
        expand(mapOf("version" to fullVersion))
    }
}

tasks.withType<Jar>().configureEach {
    destinationDirectory.set(rootDir.resolve("build").resolve("libs"))
    from(project.project(":common").sourceSets.getByName("main").output.classesDirs)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
