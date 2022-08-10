plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "team.redrock"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    compileOnly("com.google.code.gson:gson:2.9.0")
    compileOnly("net.mamoe:mirai-core:2.11.1")
    compileOnly("net.mamoe:mirai-console:2.11.1")
    compileOnly(kotlin("stdlib"))
    testImplementation("net.mamoe:mirai-console-terminal:2.11.1")
}
