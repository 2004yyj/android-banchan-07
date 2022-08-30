plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("com.google.firebase.appdistribution")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.woowahan.ordering"
        minSdk = 27
        targetSdk = 32
        versionCode = 1
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    project(":data")
    project(":domain")
    project(":constants")

    implementation ("androidx.core:core-ktx:1.8.0")
    implementation ("androidx.appcompat:appcompat:1.5.0")
    implementation ("com.google.android.material:material:1.6.1")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation ("com.squareup.okhttp3:mockwebserver:4.10.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    //Hilt
    implementation ("com.google.dagger:hilt-android:2.42")
    kapt ("com.google.dagger:hilt-android-compiler:2.42")
    kapt ("androidx.hilt:hilt-compiler:1.0.0")

    //Flow
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    //Coil
    implementation ("io.coil-kt:coil:2.1.0")

    //Room
    implementation ("androidx.room:room-runtime:2.4.3")
    kapt ("androidx.room:room-compiler:2.4.3")
    testImplementation ("androidx.room:room-testing:2.4.3")
    implementation ("androidx.room:room-paging:2.5.0-alpha02")

    //Retrofit2
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    implementation ("androidx.recyclerview:recyclerview:1.3.0-beta02")

    //ktx
    implementation ("androidx.activity:activity-ktx:1.5.1")
    implementation ("androidx.fragment:fragment-ktx:1.5.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0-alpha01")

    //Splash Screen
    implementation ("androidx.core:core-splashscreen:1.0.0")

    //WorkManager
    implementation ("androidx.work:work-runtime-ktx:2.7.1")

    //Hilt-WorkManager
    implementation ("androidx.hilt:hilt-work:1.0.0")

    //SwipeRefreshLayout
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //Paging3
    implementation ("androidx.paging:paging-runtime:3.1.1")
    implementation ("androidx.paging:paging-runtime-ktx:3.1.1")

}
