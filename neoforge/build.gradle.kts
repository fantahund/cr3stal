plugins {
    id("net.neoforged.moddev")
}

val neoforgeVersion = rootProject.extra["neoforgeVersion"] as String
val fullVersion = rootProject.extra["fullVersion"] as String

base {
    archivesName.set("cr3stal-neoforge")
}

dependencies {
    compileOnly(project.project(":common").sourceSets.getByName("main").output)
}

neoForge {
    version = neoforgeVersion

    runs {
        create("client") {
            client()
        }
        create("server") {
            server()
        }
    }

    mods {
        create("cr3stal") {
            sourceSet(sourceSets.main.get())
            sourceSet(project.project(":common").sourceSets.getByName("main"))
        }
    }
}

tasks.named<JavaCompile>("compileJava") {
    val commonMain = project(":common").sourceSets.main.get()
    source(commonMain.java.srcDirs)
}

tasks.processResources {
    val commonMain = project.project(":common").sourceSets.getByName("main")
    from(commonMain.resources.srcDirs) {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    inputs.property("version", fullVersion)
    filesMatching("META-INF/neoforge.mods.toml") {
        expand(mapOf("version" to fullVersion))
    }
}

tasks.withType<Jar>().configureEach {
    destinationDirectory.set(rootDir.resolve("build").resolve("libs"))
}

tasks.withType<Test> {
    enabled = false
}

// NFRT defaults to a Java 21 launcher. moddev overrides that for
// createMinecraftArtifacts (26.3 needs Java 25) but not for downloadAssets, so
// runClient and the IntelliJ import fail on "Cannot find a Java installation
// ... 21" unless a JDK 21 happens to be installed. downloadAssets only
// downloads files, so the project toolchain works just as well - drop this once
// moddev fixes it.
val javaToolchains = extensions.getByType<JavaToolchainService>()
val nfrtLauncher = javaToolchains.launcherFor(java.toolchain)
    .map { it.executablePath.asFile.absolutePath }

tasks.withType<net.neoforged.nfrtgradle.NeoFormRuntimeTask>().configureEach {
    javaExecutable.set(nfrtLauncher)
}
