import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    val compileSDK = 33
    val minSDK = 23
    val targetSDK = 33
    val versionCode = 1
    val versionName = "1.0.0"

    const val isMinifyEnabled = false
    val isShrinkEnabled = false

    val kotlin_v = "1.7.10"
    val gradle_v = "7.3.1"

    val tiRunner = "androidx.test.runner.AndroidJUnitRunner"

    val kce_version = "1.3.1"

    const val shimmer = "com.facebook.shimmer:shimmer:0.5.0"

    val splashscreen = "androidx.core:core-splashscreen:1.0.0"

    val logbackclassic = "ch.qos.logback:logback-classic:1.2.11"

    val coroutinecore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0"
    val coroutinetest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1"

    val accompanistpermissions = "com.google.accompanist:accompanist-permissions:0.28.0"
    val core = "androidx.core:core-ktx:1.9.0"
    val material = "com.google.android.material:material:1.7.0"
    val appcompat = "androidx.appcompat:appcompat:1.5.1"

    val fragmentnavigation = "androidx.navigation:navigation-fragment-ktx:2.5.3"
    val fragmentnavigation2 = "android.arch.navigation:navigation-fragment-ktx:2.5.3"
    val navigationui = "androidx.navigation:navigation-ui-ktx:2.5.3"

    val javax = "javax.inject:javax.inject:1"

    val lifecycle_extensions = "android.arch.lifecycle:extensions:1.1.1"

    private val lifecycle_v = "2.4.0"
    val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_v"

    private val room_v = "2.5.0"
    val room = "androidx.room:room-ktx:$room_v"
    val room_runtime = "androidx.room:room-runtime:$room_v"
    val room_rxjava = "androidx.room:room-rxjava3:$room_v"
    val room_kapt = "androidx.room:room-compiler:$room_v"
    val room_testing = "androidx.room:room-testing:$room_v"

    val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
    val coroutines_play_services = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.1.1"

    val glide = "com.github.bumptech.glide:glide:4.13.0"

    val circleiv = "de.hdodenhof:circleimageview:3.1.0"


    private val dagger_v = "2.42"
    val dagger = "com.google.dagger:dagger:$dagger_v"
    val dagger_kapt = "com.google.dagger:dagger-compiler:$dagger_v"

    val gson = "com.google.code.gson:gson:2.9.1"

    val timber = "com.jakewharton.timber:timber:5.0.1"

    val fragment = "androidx.fragment:fragment:1.5.5"
    val fragment_testing = "androidx.fragment:fragment-testing:1.5.5"

    val datastore = "androidx.datastore:datastore-preferences:1.0.0"

    val exoplayer_core = "androidx.media3:media3-exoplayer:1.1.1"
    val exoplayer_dash =  "androidx.media3:media3-exoplayer-dash:1.1.1"
    val exoplayer_ui = "androidx.media3:media3-ui:1.1.1"

    fun DependencyHandler.implementation(list: List<String>){
        list.forEach { dependency -> add("implementation", dependency) }
    }

    fun DependencyHandler.implementation(vararg depends:String){
        depends.forEach { dependency -> add("implementation", dependency) }
    }

    fun DependencyHandler.debugImplementation(vararg depends:String){
        depends.forEach { dependency -> add("debugImplementation", dependency) }
    }

//    fun DependencyHandler.debugApi(vararg depends:String){
//        depends.forEach { dependency -> add("debugApi", dependency) }
//    }

    fun DependencyHandler.addTestDependencies(){
//        add("testImplementation", testjunit)
    }

    fun DependencyHandler.addAndroidTestDependencies(){
//        add("androidTestImplementation", androidtestjunit)
//        add("androidTestImplementation", androidespresso)
    }
}