import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.dropwizard", "dropwizard-core", "4.0.1")
    implementation("io.dropwizard", "dropwizard-auth", "4.0.1") // Step 1 - Add DW auth dependency
    implementation("io.dropwizard", "dropwizard-client", "4.0.1")
    implementation("org.glassfish.jersey.core", "jersey-server", "3.1.2")
    implementation("org.glassfish.jersey.inject", "jersey-hk2", "3.1.2")
    implementation ("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.+")
    implementation("com.auth0:java-jwt:4.4.0") // Step 2 - Add kotlin JWT dependency
    implementation("org.bouncycastle:bcprov-jdk15on:1.70")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("DemoAppKt")
}
