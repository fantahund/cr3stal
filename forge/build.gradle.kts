plugins {
    id("net.minecraftforge.gradle")
}

val minecraftVersion = rootProject.extra["minecraftVersion"] as String
val forgeVersion = rootProject.extra["forgeVersion"] as String
val fullVersion = rootProject.extra["fullVersion"] as String

base {
    archivesName.set("cr3stal-forge")
}

sourceSets {
    all {
        val buildDir = layout.buildDirectory.dir("sourcesSets/${this.name}")
        output.setResourcesDir(buildDir)
        java.destinationDirectory.set(buildDir)
    }
}

repositories {
    minecraft.mavenizer(this)
    maven(fg.forgeMaven)
    maven(fg.minecraftLibsMaven)
    maven { url = uri("https://maven.minecraftforge.net/") }
}

dependencies {
    implementation(minecraft.dependency("net.minecraftforge:forge:${minecraftVersion}-${forgeVersion}"))
    compileOnly(project.project(":common").sourceSets.getByName("main").output)
}

minecraft {
    runs {
        create("client") {
            workingDir.set(project.file("run"))
            args("--mixin.config=cr3stal.mixins.json")
            mods {
                create("cr3stal") {
                    source(sourceSets.main.get())
                    source(project.project(":common").sourceSets.getByName("main"))
                }
            }
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
    filesMatching("META-INF/mods.toml") {
        expand(mapOf("version" to fullVersion))
    }
}

tasks.withType<Jar>().configureEach {
    destinationDirectory.set(rootDir.resolve("build").resolve("libs"))
    
    manifest {
        attributes["MixinConfigs"] = "cr3stal.mixins.json"
    }
}

tasks.withType<Test> {
    enabled = false
}
