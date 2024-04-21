import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.myaxa.avito_kinopoisk_test"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.myaxa.avito_kinopoisk_test"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        val properties = Properties().apply {
            load(rootProject.file("local.properties").inputStream())
        }

        buildConfigField("String", "BASE_URL", properties.getProperty("BASE_URL"))
        buildConfigField("String", "API_KEY", properties.getProperty("API_KEY"))

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.paging)

    implementation(libs.jakarta.inject.api)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.leakcanary)

    implementation(project(":common"))

    implementation(project(":features:movies-catalog"))
    implementation(project(":features:movie-details-api"))
    implementation(project(":features:movie-details-impl"))

    implementation(project(":domain:movies-catalog"))
    implementation(project(":domain:movie-details"))

    implementation(project(":data:movies-data"))
    implementation(project(":data:movie-details-data"))
    implementation(project(":data:movies-remote"))
    implementation(project(":data:movie-details-remote"))
    implementation(project(":data:network"))
    implementation(project(":data:database"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}