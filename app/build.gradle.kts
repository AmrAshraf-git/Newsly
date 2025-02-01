plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    // Added plugins
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dagger.hilt.android)}

android {
    namespace = "com.ipro.newsly"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ipro.newsly"
        minSdk = 24
        targetSdk = 35
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit - make type safe requests
    implementation(libs.retrofit)
    // converter - make retrofit use serialization json
    implementation(libs.converter.kotlinx.serialization)
    // JSON - serialize Kotlin objects <-> JSON
    implementation(libs.kotlinx.serialization.json)
    // okhttp - fire http calls
    implementation(libs.okhttp)
    // logging interceptor - log calls for debugging
    implementation(libs.logging.interceptor)
    // datastore - make type safe local storage
    implementation(libs.androidx.datastore)
    implementation (libs.androidx.datastore.preferences)
    // room - local database
    implementation(libs.androidx.room.runtime)
    // room ksp - annotation processor
    ksp(libs.androidx.room.compiler)
    // room - coroutine support
    implementation(libs.androidx.room.ktx)
    // splash screen
    implementation(libs.androidx.core.splashscreen.v100)
    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)
    // Saved state module for ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    // Annotation processor
    kapt(libs.androidx.lifecycle.compiler)
    // paging
    implementation(libs.paging.runtime)
    //dagger-hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    //coroutines
    implementation(libs.kotlinx.coroutines.android)
    //coil
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)

}
//kapt plugin
kapt {
    // improve type inference accuracy
    correctErrorTypes = true
}