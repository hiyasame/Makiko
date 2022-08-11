plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
    
    // mirai 热修插件 https://github.com/985892345/mirai-hotfix
    id("io.github.985892345.mirai-hotfix") version "1.1"
}

group = "team.redrock"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.aliyun.com/repository/public")
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

hotfix {
    createHotfix("Interceptors")
    createHotfix("ServiceCleanMember")
    createHotfix("ServiceDirtyWord")
    createHotfix("ServiceJoin")
    createHotfix("ServiceRepeatMessage")
}
