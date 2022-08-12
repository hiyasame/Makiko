plugins {
    val kotlinVersion = "1.7.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    `maven-publish`
}

group = "team.redrock"
version = "base-0.1-alpha1"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    maven("https://jitpack.io")
    mavenCentral()
}

dependencies {
    val miraiVersion = "2.12.1"
    api("net.mamoe:mirai-core:$miraiVersion") // mirai-core 的 API
    api("net.mamoe:mirai-console:$miraiVersion") // 后端
    
    // mirai 热修插件 https://github.com/985892345/mirai-hotfix
    api("com.github.985892345:mirai-hotfix:1.3")
}

// 发布带有 mirai-hotfix 的依赖
tasks.register("JarWithHotfix", Jar::class.java) {
    group = "jar"
    exclude("META-INF/**")
    from(sourceSets.named("main").get().output)
    from(sourceSets.named("main").get().compileClasspath.filter {
        it.name.contains("mirai-hotfix")
    }.map { file -> zipTree(file) })
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
