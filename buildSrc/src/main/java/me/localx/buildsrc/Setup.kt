import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

private fun BaseExtension.setupAndroid() {
    val currentVersionCode = (System.currentTimeMillis() / 1000).toInt()

    compileSdkVersion(34)
    defaultConfig {
        minSdk = 26
        targetSdk = 34
        resourceConfigurations += setOf("ru", "en")
        vectorDrawables.useSupportLibrary = true

        versionCode = currentVersionCode
        versionName = "1.0.0"
    }

    buildTypes {
        all {
            buildConfigField("Integer", "VERSION_CODE", "$currentVersionCode")
        }
    }

    packagingOptions.resources.excludes.add(
        "META-INF/{AL2,LGPL2.1}"
    )
}

private fun BaseExtension.setupApplicationAndroid() {
    setupAndroid()

    buildTypes {
        named("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

fun Project.setupApplicationModule() {
    val androidExtension: BaseExtension = extensions.findByType<LibraryExtension>()
        ?: extensions.findByType<AppExtension>()
        ?: error("Could not found Android application or library plugin applied on module $name")

    androidExtension.apply {
        setupApplicationAndroid()

        buildFeatures.apply {
            compose = true
            buildConfig = true
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        testOptions {
            unitTests.all {
                it.useJUnitPlatform()
            }
        }
    }
}

fun Project.setupModuleForAndroidxCompose() {
    val androidExtension: BaseExtension = extensions.findByType<LibraryExtension>()
        ?: extensions.findByType<AppExtension>()
        ?: error("Could not found Android application or library plugin applied on module $name")

    androidExtension.apply {
        setupAndroid()

        kotlinExtension.explicitApi = ExplicitApiMode.Strict

        buildFeatures.apply {
            buildConfig = true
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        testOptions {
            unitTests.all {
                it.useJUnitPlatform()
            }
        }
    }
}