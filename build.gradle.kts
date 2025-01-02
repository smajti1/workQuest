import de.undercouch.gradle.tasks.download.Download
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.adarshr.test-logger") version "4.0.0"
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.spring") version "2.1.0"
    kotlin("plugin.jpa") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"
    id("de.undercouch.download") version "5.6.0"
}

group = "it.dziubinski"
version = "0.9.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.shell:spring-shell-starter:3.4.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.1.0")
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.seleniumhq.selenium:selenium-java:4.27.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
    testImplementation("io.mockk:mockk:1.13.14")
    testImplementation("org.reflections:reflections:0.10.2")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.shell:spring-shell-dependencies:3.2.0")
    }
}

tasks.named("compileKotlin", org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask::class.java) {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

kotlin {
    compilerOptions {
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_1)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val newrelicFile = "newrelic-java-8.17.0.zip"
val downloadNewrelic by tasks.registering(Download::class) {
    description = "Download New Relic Java Agent"
    onlyIfNewer(true)
    src("https://download.newrelic.com/newrelic/java-agent/newrelic-agent/8.17.0/$newrelicFile")
    dest(file("newrelic/$newrelicFile"))
}

val unzipNewrelic by tasks.registering(Copy::class) {
    description = "Unzip New Relic Java Agent"
    from(zipTree("newrelic/$newrelicFile"))
    into(layout.buildDirectory.dir("libs"))
    includeEmptyDirs = false
}

tasks.named("unzipNewrelic") {
    dependsOn(downloadNewrelic)
}