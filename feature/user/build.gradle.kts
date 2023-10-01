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
                jvmTarget = "20"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "logger"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core"))
                implementation(project(":logger"))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                implementation("io.insert-koin:koin-core:3.5.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.compose.ui:ui:1.5.2")
                implementation("androidx.compose.ui:ui-tooling:1.5.2")
                implementation("androidx.compose.ui:ui-tooling-preview:1.5.2")
                implementation("androidx.compose.foundation:foundation:1.5.2")
                implementation("androidx.compose.material:material:1.5.2")
                implementation("androidx.activity:activity-compose:1.7.2")

                implementation("io.insert-koin:koin-android:3.5.0")
                implementation("io.insert-koin:koin-androidx-compose:3.5.0")
            }
        }
    }
}

android {
    namespace = "com.jgbravo.moneymate.user"
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
        sourceCompatibility = JavaVersion.VERSION_20
        targetCompatibility = JavaVersion.VERSION_20
    }
}