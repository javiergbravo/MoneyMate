plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "18"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "core"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("androidx.compose.ui:ui:1.5.3")
                implementation("androidx.compose.ui:ui-tooling:1.5.3")
                implementation("androidx.compose.ui:ui-tooling-preview:1.5.3")
                implementation("androidx.compose.foundation:foundation:1.5.3")
                implementation("androidx.compose.material3:material3:1.2.0-alpha09")
                implementation("androidx.compose.material:material-icons-extended:1.5.3")
                implementation("androidx.activity:activity-compose:1.8.0")
                implementation("androidx.navigation:navigation-compose:2.7.4")
                implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
                implementation("io.coil-kt:coil-compose:2.2.2")
            }
        }
    }
}

android {
    namespace = "com.jgbravo.moneymate.core"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
}