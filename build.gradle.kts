plugins {
    alias(libs.plugins.bit.convention)
    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.modrinth.minotaur)
}

group = "xyz.bitsquidd"

loom {
    splitEnvironmentSourceSets()

    mods {
        create("packet-ninja") {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets["client"])
        }
    }
}

repositories {
    mavenLocal()

    maven { url = uri("https://repo.bitsquidd.xyz/repository/bit/") }
    maven { url = uri("https://maven.fabricmc.net/") }
    maven { url = uri("https://maven.terraformersmc.com/releases") }
    maven { url = uri("https://maven.shedaniel.me/") }

    gradlePluginPortal()
}

dependencies {
    minecraft("com.mojang:minecraft:26.1.2")

    implementation(rootProject.libs.fabric.loader)
    implementation(rootProject.libs.fabric.api)

    implementation(rootProject.libs.adventure.platform)
    include(rootProject.libs.adventure.platform)
    implementation(rootProject.libs.bits.fabric)

    implementation(rootProject.libs.modmenu)
    implementation(rootProject.libs.clothconfig)
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

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

modrinth {
    token = System.getenv("MODRINTH_TOKEN")
    projectId = "eKxg8CAG"
    versionName = "Packet Ninja $version"
    versionNumber.set(project.version.toString())
    changelog.set(System.getenv("CHANGELOG") ?: "No changelog provided.")
    uploadFile.set(tasks.named<Jar>("jar").get())
    versionType.set("release")
    syncBodyFrom.set(rootProject.file("README.md").readText())
}

tasks.named("modrinth") {
    dependsOn(tasks.named("modrinthSyncBody"))
}