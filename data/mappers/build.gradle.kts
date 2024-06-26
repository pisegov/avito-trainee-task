plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.myaxa.data.mappers"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":domain:movies-catalog"))
    implementation(project(":domain:movie-details"))
    implementation(project(":data:movies-remote"))
    implementation(project(":data:movie-details-remote"))
    implementation(project(":data:database"))
}