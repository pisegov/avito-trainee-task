plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.kotlinx.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.adapters.result)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.converter)
    implementation(libs.androidx.annotation)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.jakarta.inject.api)
    implementation(libs.okhttp.logging.interceptor)

    implementation(project(":data:network"))
}