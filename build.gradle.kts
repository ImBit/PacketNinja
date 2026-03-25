import net.fabricmc.loom.task.RemapJarTask
import net.ltgt.gradle.errorprone.errorprone

plugins {
    alias(libs.plugins.fabric.loom)
    id("maven-publish")
    alias(libs.plugins.modrinth.minotaur)
    alias(libs.plugins.errorprone)
}

version = "0.0.6"
group = "xyz.bitsquidd"

base {
    archivesName.set(project.property("archives_base_name")!!.toString())
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven {
        name = "Terraformers"
        url = uri("https://maven.terraformersmc.com/releases")
    }
    maven { url = uri("https://maven.shedaniel.me/") }
}

loom {
    splitEnvironmentSourceSets()

    mods {
        create("packet-ninja") {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets["client"])
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")

    mappings(loom.officialMojangMappings())

    modImplementation(rootProject.libs.fabric.loader)
    modImplementation(rootProject.libs.fabric.api)

    errorprone(rootProject.libs.errorprone)

//    modApi(rootProject.libs.bits.api)

    modImplementation(rootProject.libs.adventure.platform)
    modImplementation(rootProject.libs.classgraph)

    include(rootProject.libs.javassist)
    modImplementation(rootProject.libs.reflections)?.let {
        include(it)
    }

    modImplementation(rootProject.libs.modmenu)
    modApi(rootProject.libs.clothconfig) {
        exclude(group = "net.fabricmc.fabric-api")
    }
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(mapOf("version" to project.version))
        }
    }
    jar {
        inputs.property("archivesName", project.base.archivesName.get())
        from("LICENSE") {
            rename { "${it}_${project.base.archivesName.get()}" }
        }
    }

    withType<JavaCompile> {
        options.release.set(21)
        options.encoding = "UTF-8"
        options.errorprone {
            enabled.set(true)
            disableWarningsInGeneratedCode.set(true)
            disableAllWarnings.set(true)
            errorproneArgs.addAll(
                "-Xep:CollectionIncompatibleType:ERROR",
                "-Xep:EqualsIncompatibleType:ERROR",
                "-Xep:MissingOverride:ERROR",
                "-Xep:SelfAssignment:ERROR",
                "-Xep:StreamResourceLeak:ERROR",
                "-Xep:CanonicalDuration:OFF",
                "-Xep:InlineMeSuggester:OFF",
                "-Xep:ImmutableEnumChecker:OFF"
            )
        }
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.property("archives_base_name")!!.toString()
            from(components["java"])
        }
    }

    repositories {}
}

modrinth {
    token = System.getenv("MODRINTH_TOKEN")
    projectId = "eKxg8CAG"
    versionName = "Packet Ninja $version"
    versionNumber.set(project.version.toString())
    changelog.set(System.getenv("CHANGELOG") ?: "No changelog provided.")
    uploadFile.set(tasks.named<RemapJarTask>("remapJar").get())
    versionType.set("release")
    syncBodyFrom.set(rootProject.file("README.md").readText())
}

tasks.named("modrinth") {
    dependsOn(tasks.named("modrinthSyncBody"))
}