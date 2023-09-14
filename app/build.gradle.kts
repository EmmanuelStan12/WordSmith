plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.codedev.dictionary"
    compileSdk = Dependencies.compileSDK

    defaultConfig {
        applicationId = "com.codedev.dictionary"
        minSdk = Dependencies.minSDK
        targetSdk = Dependencies.targetSDK
        versionCode = Dependencies.versionCode
        versionName = Dependencies.versionName

        testInstrumentationRunner = Dependencies.tiRunner

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }

        release {
            isMinifyEnabled = Dependencies.isMinifyEnabled
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(mapOf("path" to ":features:base")))
    implementation(project(mapOf("path" to ":features:home")))

    implementation(project(mapOf("path" to ":libraries:ui-base-lib")))
    implementation(project(mapOf("path" to ":libraries:data-lib")))
    implementation(project(mapOf("path" to ":libraries:room-lib")))
    implementation(project(mapOf("path" to ":libraries:context-provider-lib")))
    implementation(project(mapOf("path" to ":libraries:utils-lib")))


    implementation(Dependencies.splashscreen)
}