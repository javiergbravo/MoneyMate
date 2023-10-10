import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.io.FileInputStream
import java.util.Properties

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.codingfeline.buildkonfig")
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

//                implementation("com.google.firebase:firebase-auth-ktx:22.1.2")
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
                implementation("androidx.activity:activity-compose:1.7.2")
                implementation("androidx.navigation:navigation-compose:2.7.4")
                implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
                implementation("com.google.accompanist:accompanist-navigation-animation:0.33.2-alpha")
                implementation("io.coil-kt:coil-compose:2.2.2")

                implementation("io.insert-koin:koin-android:3.5.0")
                implementation("io.insert-koin:koin-androidx-compose:3.5.0")

                implementation("com.google.firebase:firebase-auth-ktx:22.1.2")
                implementation("com.google.android.gms:play-services-auth:20.7.0")
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
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
}

buildkonfig {
    packageName = "com.jgbravo.moneymate.user"

    defaultConfigs {
        buildConfigField(type = STRING, name = "GOOGLE_AUTH_KEY", value = getGoogleAuthKey())
    }
}

fun getGoogleAuthKey(): String {
    val propFile = rootProject.file("./././secrets.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties["GOOGLE_AUTH_KEY"] as? String ?: ""
}