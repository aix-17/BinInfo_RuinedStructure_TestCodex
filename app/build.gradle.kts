plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")

    // hilt
    id("com.google.devtools.ksp") version "2.1.21-2.0.1"
    id("com.google.dagger.hilt.android")
}
android {
    namespace = "com.example.binlookup"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.binlookup"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Navigation ✅
    val navVersion = "2.9.0"
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // ViewModel ✅
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.0")

    // Hilt ✅
    implementation("com.google.dagger:hilt-android:2.56.2")
    ksp("com.google.dagger:hilt-android-compiler:2.56.2")

    // Room ✅
    val roomVersion = "2.7.1"

    implementation("androidx.room:room-runtime:$roomVersion")

    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    ksp("androidx.room:room-compiler:$roomVersion")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")
    // optional - RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:$roomVersion")
    // optional - RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:$roomVersion")
    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:$roomVersion")
    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$roomVersion")
    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:$roomVersion")


    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Retrofit with Scalar Converter
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    // Gson converter for Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Gson for JSON parsing annotations
    implementation("com.google.code.gson:gson:2.10.1")
    // OkHttp logging interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Hilt Navigation Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Extended material icons for additional icons like Public and History
    implementation("androidx.compose.material:material-icons-extended")


    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
