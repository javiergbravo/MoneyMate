plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.jgbravo.moneymate.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.jgbravo.moneymate.android"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_20
        targetCompatibility = JavaVersion.VERSION_20
    }
    kotlinOptions {
        jvmTarget = "20"
    }
}

dependencies {
    implementation(project(":core"))
    implementation("androidx.compose.ui:ui:1.5.2")
    implementation("androidx.compose.ui:ui-tooling:1.5.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.2")
    implementation("androidx.compose.foundation:foundation:1.5.2")
    implementation("androidx.compose.material:material:1.5.2")
    implementation("androidx.activity:activity-compose:1.7.2")
}