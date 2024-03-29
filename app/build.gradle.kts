plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.edts.tmdroid"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.edts.tmdroid"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    coreLibraryDesugaring(Android.tools.desugarJdkLibs)

    implementation(AndroidX.core.ktx)

    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraintLayout)
    implementation(Google.android.material)

    implementation(AndroidX.dataStore.preferences)

    implementation(AndroidX.lifecycle.runtime.ktx)

    implementation(AndroidX.navigation.fragmentKtx)
    implementation(AndroidX.navigation.uiKtx)

    implementation(AndroidX.preference.ktx)

    implementation(AndroidX.room.ktx)
    kapt(AndroidX.room.compiler)

    implementation(AndroidX.swipeRefreshLayout)

    implementation(Google.dagger.hilt.android)
    kapt(Google.dagger.hilt.compiler)

    implementation(Square.okHttp3)
    implementation(Square.retrofit2)
    implementation(Square.retrofit2.converter.gson)

    implementation(libs.combineTupleLiveData)
    implementation(libs.glide)
    implementation(libs.kotlinResult)
    implementation(libs.liveEvent)
    implementation(libs.shimmer)

    testImplementation(Testing.junit4)
    androidTestImplementation(AndroidX.test.ext.junit)
    androidTestImplementation(AndroidX.test.espresso.core)
}
