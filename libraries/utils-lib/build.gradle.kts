plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.codedev.utils_lib"
    compileSdk = Dependencies.compileSDK

    defaultConfig {
        minSdk = Dependencies.minSDK
        targetSdk = Dependencies.targetSDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {

        debug {
            isMinifyEnabled = Dependencies.isMinifyEnabled
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }

        release {
            isMinifyEnabled = Dependencies.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(Dependencies.datastore)
    implementation(Dependencies.dagger)
    kapt(Dependencies.dagger_kapt)
}