plugins {
    val kotlinVersion = "1.8.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.15.0"
}

group = "net.misaka.bot.groupexport"
version = "0.1.0"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

dependencies {
    testImplementation("net.mamoe:mirai-core-mock:2.15.0")
}

mirai {
    jvmTarget = JavaVersion.VERSION_1_8
}
