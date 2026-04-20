pluginManagement {
    repositories {
        mavenLocal()

        maven { url = uri("https://repo.bitsquidd.xyz/repository/bit/") }
        maven { url = uri("https://maven.fabricmc.net/") }

        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()

        maven { url = uri("https://repo.bitsquidd.xyz/repository/bit/") }
        maven { url = uri("https://maven.fabricmc.net/") }

        mavenCentral()
        gradlePluginPortal()
    }
}
