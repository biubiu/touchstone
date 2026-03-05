plugins {
    alias(libs.plugins.kotlin.jvm)
    java
}

group = "com.shawn"
version = "1.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.guava)
    implementation(libs.guava.testlib)
    implementation(libs.rxjava)
    implementation(libs.reactor.core)
    implementation(libs.commons.lang3)
    implementation(libs.gson)
    implementation(libs.jakarta.mail)
    implementation(libs.jakarta.activation)
    implementation(libs.angus.mail)
    implementation(libs.xstream)
    implementation(libs.jedis)
    implementation(libs.snakeyaml)
    implementation(libs.temporal.sdk)
    runtimeOnly(libs.slf4j.simple)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    
    testImplementation(libs.bundles.testing)
}

tasks.test {
    useJUnitPlatform()
}
