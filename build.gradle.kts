plugins {
    application
    checkstyle
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "simulation.code"
version = "1.0-SNAPSHOT"

application {
    mainClass = "simulation.Main"
}

repositories {
    mavenCentral()
}

dependencies {
    // CheckStyle
    implementation("com.puppycrawl.tools:checkstyle:10.23.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

checkstyle {
    toolVersion = "10.23.1"
    configFile = file("config/checkstyle/checkstyle.xml")
}

tasks.test {
    useJUnitPlatform()
}