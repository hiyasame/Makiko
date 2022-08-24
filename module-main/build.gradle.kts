plugins {
    kotlin("jvm")
    id("io.github.985892345.mirai-hotfix") version "1.3"
    id("org.jetbrains.dokka") version "1.7.10"
}

group = "team.redrock"
version = "module-main"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    maven("https://jitpack.io")
    mavenCentral()
}

dependencies {
    implementation(project(":base"))
}

hotfix {
    createHotfix("Interceptors")
    createHotfix("ServiceCleanMember")
    createHotfix("ServiceDirtyWord")
    createHotfix("ServiceJoin")
    createHotfix("ServiceRepeatMessage")
    createHotfix("JsShell") {
        compileOnly("org.mozilla:rhino:1.7.14")
    }
}



