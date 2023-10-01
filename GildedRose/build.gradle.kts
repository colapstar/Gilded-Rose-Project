plugins {
    `java-library`
    jacoco
    checkstyle
    pmd
    id("com.github.spotbugs") version "5.0.12"
    id("info.solidsoft.pitest") version "1.7.0" // Added PITest plugin
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("--enable-preview")
    testLogging {
        showStandardStreams = true
        events("passed", "skipped", "failed")
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-Xlint:all")
    options.compilerArgs.add("-Xlint:-serial")
    options.compilerArgs.add("--enable-preview")
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required.set(false)
        html.required.set(true)
        html.stylesheet = resources.text.fromFile("config/checkstyle/checkstyle.xsl")
    }
}

pmd {
    isIgnoreFailures = true
    isConsoleOutput = true
    toolVersion = "6.21.0"
    rulesMinimumPriority.set(5)
    ruleSets = listOf(
        "category/java/bestpractices.xml",
        "category/java/codestyle.xml",
        "category/java/design.xml",
        "category/java/documentation.xml",
        "category/java/errorprone.xml",
        "category/java/multithreading.xml",
        "category/java/performance.xml",
        "category/java/security.xml",
    )
}

// Added PITest configuration
pitest {
    targetClasses = setOf("com.gildedrose.*")  // specify the classes or packages you want to mutate
    threads = 4  // number of threads to use, adjust as per your machine
    outputFormats = setOf("HTML", "XML")  // output formats
    timestampedReports = false
}
