plugins {
    alias(libs.plugins.compose.compiler)
}

buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.plugin.kotlin)
    }
}