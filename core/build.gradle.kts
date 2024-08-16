plugins {
    `kotlin-android`
    `android-library`
    alias(libs.plugins.compose.compiler)
}

setupModuleForAndroidxCompose()

android {
    namespace = "psbmarket.core"
}

dependencies {
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
}