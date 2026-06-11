plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.praktam_2417051022"

    // Memperbaiki format compileSdk 36 agar rapi dan standar tanpa blok minorApiLevel
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.praktam_2417051022"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // AndroidX & UI Compose Utama (Menggunakan Version Catalog bawaan)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Menggunakan variable catalog yang sudah terdeteksi ada di libs project kamu
    // Ini otomatis menghilangkan semua warning "Use version catalog instead"
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)

    // Ikon material tambahan (Opsional, jika tidak ada di catalog, kita tulis manual yang paling update)
    implementation("androidx.compose.material:material-icons-extended:1.7.6")

    // Library Network Retrofit & Data Parser Gson (Menggunakan versi stabil terbaru untuk hilangkan warning)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.google.code.gson:gson:2.11.0")

    // Dependensi Room Database Lokal (Menggunakan versi stabil terbaru)
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // Testing & Debugging
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}