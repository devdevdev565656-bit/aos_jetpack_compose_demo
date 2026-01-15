plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "app.aos.jp.demo"
    compileSdk = 36

    defaultConfig {
        applicationId = "app.aos.jp.demo"
        minSdk = 29
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.adaptive)
    implementation(libs.androidx.compose.material3.window.size.class1)
    implementation(libs.androidx.compose.adaptive.navigation3)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.compose.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.navigation3.runtime)  // 最新穩定版
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)  // ViewModel 支援
    // 如需深層連結，可加 kotlinx-serialization
    implementation(libs.kotlinx.serialization.core)
    implementation("androidx.compose.foundation:foundation:1.7.0")
    implementation("androidx.compose.material3:material3:1.4.0")
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.chart)
    implementation("com.github.bumptech.glide:compose:1.0.0-beta08")
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.lottie.compose)
    implementation(libs.compose.progressindicator)

    implementation("io.github.grizzi91:bouquet:1.1.3")








}