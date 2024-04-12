plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.myaxa.data.actors_remote"
    compileSdk = 34

    defaultConfig {
        minSdk = 27
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.adapters.result)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.annotation)

    implementation(project(":data:network"))
    implementation(project(":domain:movie-details"))
}