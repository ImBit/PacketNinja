import net.fabricmc.loom.task.RemapJarTask

plugins {
    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.modrinth.minotaur)
    alias(libs.plugins.bit.convention)
}

group = "com.github.imbit.ninja"

base {
    archivesName.set(project.property("archives_base_name")!!.toString())
}

repositories {
    mavenLocal()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://maven.terraformersmc.com/releases") }
    maven { url = uri("https://maven.shedaniel.me/") }
    mavenCentral()
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

    modImplementation(rootProject.libs.adventure.platform)
    include(rootProject.libs.adventure.platform)

    modCompileOnly(rootProject.libs.bits.fabric)

    include(rootProject.libs.javassist)

    modImplementation(rootProject.libs.modmenu)
    modApi(rootProject.libs.clothconfig) {
        exclude(group = "net.fabricmc.fabric-api")
    }
    include(rootProject.libs.clothconfig)
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