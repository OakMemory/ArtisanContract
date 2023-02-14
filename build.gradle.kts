import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "dev.krysztal"
version = "1.0-SNAPSHOT"

val targetJavaVersion = 17
val compileKotlin: KotlinCompile by tasks
val compileTestKotlin: KotlinCompile by tasks

val paperVersion = "1.19.2-R0.1-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
}


dependencies {


    compileOnly("io.papermc.paper:paper-api:$paperVersion")
    compileOnly("org.spigotmc:spigot:$paperVersion")

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")

    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
    shadow(kotlin("stdlib"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.processResources {
    inputs.property("version", version)
    filesMatching("plugin.yml") { expand(mutableMapOf("version" to version)) }
}

compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}