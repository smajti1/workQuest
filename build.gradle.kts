import de.undercouch.gradle.tasks.download.Download
import org.gradle.kotlin.dsl.register

plugins {
    id("org.springframework.boot") version "4.1.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.adarshr.test-logger") version "4.0.0"
    kotlin("jvm") version "2.4.21"
    kotlin("plugin.spring") version "2.4.21"
    kotlin("plugin.jpa") version "2.4.21"
    kotlin("plugin.serialization") version "2.4.21"
    id("de.undercouch.download") version "5.7.0"

    /**
     * list dependency update
     * ./gradlew dependencyUpdates
     * @see https://github.com/ben-manes/gradle-versions-plugin#gradle-versions-plugin
     */
    id("io.github.ben-manes.versions") version "0.67.0"
}

group = "it.dziubinski"
version = "0.9.0"

java {
    sourceCompatibility = JavaVersion.VERSION_25
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.shell:spring-shell-starter:4.0.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.4.21")
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.12.0")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.seleniumhq.selenium:selenium-java:4.52.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest:kotest-runner-junit5:6.2.6")
    testImplementation("io.kotest:kotest-extensions-spring:6.2.6")
    testImplementation("io.mockk:mockk:1.14.12")
    testImplementation("org.reflections:reflections:0.10.2")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.shell:spring-shell-dependencies:4.0.3") // https://github.com/spring-projects/spring-shell
    }
}

tasks.named("compileKotlin", org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask::class.java) {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

kotlin {
    compilerOptions {
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_4)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val newrelicVersion = "9.5.0"
val newrelicFile = "newrelic-java-$newrelicVersion.zip"
val downloadNewrelic = tasks.register<Download>("downloadNewRelicJavaAgent") {
    description = "Download New Relic Java Agent"
    onlyIfNewer(true)
    src("https://download.newrelic.com/newrelic/java-agent/newrelic-agent/$newrelicVersion/$newrelicFile")
    dest(file("newrelic/$newrelicFile"))
}

val unzipNewrelic = tasks.register<Copy>("unzipNewRelicJavaAgent") {
    description = "Unzip New Relic Java Agent"
    from(zipTree("newrelic/$newrelicFile"))
    into(layout.buildDirectory.dir("libs"))
    includeEmptyDirs = false
}

tasks.named("unzipNewRelicJavaAgent") {
    dependsOn(downloadNewrelic)
}