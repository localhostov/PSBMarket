import com.android.build.gradle.internal.api.ApkVariantOutputImpl

plugins {
    android
    `kotlin-android`
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    id(libs.plugins.android.application.get().pluginId)
}

composeCompiler {
    enableStrongSkippingMode = true
    metricsDestination = layout.buildDirectory.dir("compose_compiler")
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
}

setupApplicationModule()

android {
    namespace = "me.localx.psbmarket"

    dependenciesInfo.includeInApk = false

    applicationVariants.configureEach {
        outputs.configureEach {
            (this as? ApkVariantOutputImpl)?.outputFileName =
                "PSBMarket-$versionName-$versionCode-$name.apk"
        }
    }
}

dependencies {
    implementation(projects.uikit)
    implementation(projects.core)

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.compose.activity)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.screenmodel)
    implementation(libs.voyager.transitions)
    implementation(libs.voyager.hilt)
    implementation(libs.voyager.tabs)
    implementation(libs.icons)
    implementation(libs.graphql.runtime)
    implementation(libs.hilt)
    implementation(libs.sonner)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.splash)

    ksp(libs.hilt.compiler)
}