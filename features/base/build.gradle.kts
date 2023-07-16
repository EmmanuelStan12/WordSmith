plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.codedev.base"
    compileSdk = Dependencies.compileSDK

    defaultConfig {
        minSdk = Dependencies.minSDK
        targetSdk = Dependencies.targetSDK

        testInstrumentationRunner = Dependencies.tiRunner

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {

        release {
            isMinifyEnabled = Dependencies.isMinifyEnabled
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }

        debug {
            isMinifyEnabled = false
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    api(Dependencies.core)
    api(Dependencies.constraintlayout)
    api(Dependencies.material)
    api(Dependencies.appcompat)
    api(Dependencies.timber)
    api(Dependencies.lifecycle_extensions)
    api(Dependencies.lifecycle_runtime)
    api(Dependencies.navigationui)
    api(Dependencies.fragmentnavigation)

    api(project(mapOf("path" to ":libraries:ui-base-lib")))
    api(project(mapOf("path" to ":libraries:room-lib")))
    api(project(mapOf("path" to ":libraries:context-provider-lib")))
    api(project(mapOf("path" to ":libraries:data-lib")))
    api(project(mapOf("path" to ":libraries:utils-lib")))

    api(Dependencies.dagger)
    kapt(Dependencies.dagger_kapt)

    api(Dependencies.shimmer)

}