plugins {
    kotlin("jvm") version "2.0.20"
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
}

group = "ad.kata.shoppingbasket"
version = "0.0.0"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.3")
    testImplementation("io.strikt:strikt-core:0.34.0")
}

/* Source sets by Kotlin conventions /src and /test */
sourceSets.main { kotlin.srcDirs("src/") }
sourceSets.test { kotlin.srcDirs("test/") }

/* Resources */
sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("test-resources")

/* Compiler Options */
kotlin {
    compilerOptions {
        allWarningsAsErrors.set(true)
    }
}

/* Detekt */
detekt {
    source.setFrom("src/", "test/")
    config.setFrom("detekt.yml")
}

/* Check with JUnit 5 */
tasks.test {
    useJUnitPlatform {
        includeEngines("junit-jupiter")
        excludeEngines("junit-vintage")
    }
}


tasks.withType<Test>().configureEach {
    reports.html.required = false
}

/* Gradle wrapper */
tasks.withType<Wrapper> {
    gradleVersion = "8.10"
    distributionType = Wrapper.DistributionType.BIN
}
