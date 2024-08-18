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
    api(libs.sonner)
    implementation(libs.androidx.datastore)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    ksp(libs.hilt.compiler)
}