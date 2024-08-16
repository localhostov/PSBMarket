plugins {
    `kotlin-android`
    `android-library`
    alias(libs.plugins.compose.compiler)
}

setupModuleForAndroidxCompose()

android {
    namespace = "psbmarket.uikit"
}

dependencies {
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.graphics)

    implementation(libs.material3)
}