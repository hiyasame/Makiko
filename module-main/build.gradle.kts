plugins {
    kotlin("jvm")
    id("io.github.985892345.mirai-hotfix") version "1.3"
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



