plugins {
    id ("com.android.library")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        testInstrumentationRunner = Config.testInstrumentationRunner
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
        jvmTarget = Config.jvmTarget
    }
}

dependencies {
    implementation(project(Project.domain))
    implementation(project(Project.constants))

    //Hilt
    implementation (Dependencies.Hilt.hiltAndroid)
    kapt (Dependencies.Hilt.hiltAndroidCompiler)

    //Flow
    implementation (Dependencies.Coroutines.core)

    //Retrofit2
    implementation (Dependencies.Network.retrofit)
    implementation (Dependencies.Network.retrofitGson)

    //Room
    implementation (Dependencies.Database.roomRuntime)
    implementation (Dependencies.Database.roomKtx)
    kapt (Dependencies.Database.roomCompiler)
    testImplementation (Dependencies.Database.roomTesting)
    implementation (Dependencies.Database.roomPaging)
}