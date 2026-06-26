// ── atpkt build configuration ──────────────────────
// AT Protocol SDK for Kotlin — JVM-first, Ktor-based

plugins {
    kotlin("jvm") version "2.3.0"
    kotlin("plugin.serialization") version "2.3.0"
    `maven-publish`
}

group = "uk.ewancroft"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    val ktorVersion = "3.5.0"

    // Serialisation and async
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.16")

    // Code generation (KotlinPoet — used by the lexicon generator tool)
    implementation("com.squareup:kotlinpoet:1.18.1")

    // Ktor HTTP client: CIO engine, WebSocket support, content negotiation,
    // CBOR (for subscription streams) and JSON (for XRPC)
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-websockets:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-client-mock:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-cbor:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation("io.mockk:mockk:1.13.14")
}

kotlin {
    sourceSets.main {
        // Generated code lives alongside the library source but is excluded
        // from compilation — the generator output is tracked for reference
        kotlin.exclude("uk/ewancroft/atpkt/generated/**")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

// ── Lexicon code generation task ───────────────────

tasks.register<JavaExec>("generateLexiconClasses") {
    description = "Runs the Lexicon -> Kotlin code generator (KotlinPoet AST)."
    group = "build"
    dependsOn("processResources")
    mainClass.set("uk.ewancroft.atpkt.gen.GeneratorTool")
    classpath = sourceSets.main.get().runtimeClasspath
}

// ── Test configuration ─────────────────────────────

tasks.test {
    useJUnitPlatform()
}
