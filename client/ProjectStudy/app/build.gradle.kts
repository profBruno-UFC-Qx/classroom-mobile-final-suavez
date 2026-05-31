plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.projectstudy"

    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.projectstudy"

        minSdk = 29
        targetSdk = 35

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    // =========================
    // ICONS
    // =========================

    implementation(libs.androidx.compose.material.icons.extended)

    // =========================
    // CORE
    // =========================

    implementation(libs.androidx.core.ktx)

    // =========================
    // LIFECYCLE
    // =========================

    implementation(libs.androidx.lifecycle.runtime.ktx)

    // =========================
    // COMPOSE
    // =========================

    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)

    debugImplementation(libs.androidx.compose.ui.tooling)

    // =========================
    // NAVIGATION
    // =========================

    implementation(libs.androidx.navigation.compose)

    // =========================
    // HILT
    // =========================

    implementation(libs.hilt.android)

    implementation(libs.androidx.hilt.navigation.compose)

    ksp(libs.hilt.compiler)

    // =========================
    // COROUTINES
    // =========================

    implementation(libs.kotlinx.coroutines.android)

    // =========================
    // ROOM
    // =========================

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    ksp(libs.androidx.room.compiler)

    // =========================
    // RETROFIT
    // =========================

    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // =========================
    // TESTS
    // =========================

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(platform(libs.androidx.compose.bom))

    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.coil.compose)
}