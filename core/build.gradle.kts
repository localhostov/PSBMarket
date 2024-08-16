plugins {
    `kotlin-android`
    `android-library`
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.graphql)
}

apollo {
    service("service") {
        packageName.set("psbmarket.graphql")
    }
}

setupModuleForAndroidxCompose()

android {
    namespace = "psbmarket.core"
}

dependencies {
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.graphql.runtime)
    implementation(libs.hilt)

    ksp(libs.hilt.compiler)
}