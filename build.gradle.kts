plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("net.mamoe.mirai-console") version "2.12.1"
    // mirai 热修插件 https://github.com/985892345/mirai-hotfix
    id("io.github.985892345.mirai-hotfix") version "1.2"
}

group = "team.redrock"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}
