plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.itcourses.feature.profile"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.core.common)
    implementation(libs.koin.android)
    implementation(libs.adapterdelegates.kotlin)
    implementation(libs.adapterdelegates.viewbinding)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.navigation.fragment)
}

