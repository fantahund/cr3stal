plugins {
    id("net.neoforged.moddev")
}

val neoforgeVersion = rootProject.extra["neoforgeVersion"] as String
val modVersion = rootProject.extra["modVersion"] as String

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

    inputs.property("version", modVersion)
    filesMatching("META-INF/neoforge.mods.toml") {
        expand(mapOf("version" to modVersion))
    }
}

tasks.withType<Jar>().configureEach {
    destinationDirectory.set(rootDir.resolve("build").resolve("libs"))
}

tasks.withType<Test> {
    enabled = false
}
