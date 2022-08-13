plugins {
    val kotlinVersion = "1.7.10"
    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false
    `maven-publish`
}

subprojects {
    repositories {
        maven("https://maven.aliyun.com/repository/public")
        maven("https://jitpack.io")
        mavenCentral()
    }
}

allprojects {
    version = "base-0.1"
    group = "team.redrock"
}




