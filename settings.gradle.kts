pluginManagement {
    repositories {
        mavenLocal()

        maven { url = uri("https://repo.bitsquidd.xyz/repository/bit/") }
        maven { url = uri("https://maven.fabricmc.net/") }

        gradlePluginPortal()
    }
}
