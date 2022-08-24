plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("net.mamoe.mirai-console") version "2.12.1"
}

dependencies {
    val miraiVersion = "2.12.1"
    api("net.mamoe:mirai-core:$miraiVersion") // mirai-core 的 API
    api("net.mamoe:mirai-console:$miraiVersion") // 后端

    // mirai 热修插件 https://github.com/985892345/mirai-hotfix
    api("com.github.985892345:mirai-hotfix:1.3")
    "shadowLink"("com.github.985892345:mirai-hotfix")
    implementation("org.mozilla:rhino:1.7.14")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            from(components["java"])
        }
        repositories {
            maven { url = uri("$buildDir/local") }
        }
    }
}

// 发布带有 mirai-hotfix 的依赖
tasks.register("JarWithHotfix", Jar::class.java) {
    group = "jar"
    from(sourceSets.named("main").get().output)
    from(sourceSets.named("main").get().compileClasspath.filter {
        it.name.contains("mirai-hotfix")
    }.map { file -> zipTree(file) })
}