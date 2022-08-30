object Version {
    const val paging = "3.1.1"
    const val swipeRefreshLayout = "1.1.0"
    const val workHilt = "1.0.0"
    const val work = "2.7.1"
    const val splashScreen = "1.0.0"
    const val activityKtx = "1.5.1"
    const val fragmentKtx = "1.5.2"
    const val viewModelKtx = "2.6.0-alpha01"
    const val recyclerView = "1.3.0-alpha02"
    const val retrofit = "2.9.0"
    const val coil = "2.1.0"
    const val coroutines = "1.6.4"
    const val constraintLayout = "2.1.4"
    const val hilt = "2.42"
    const val hiltCompiler = "1.0.0"
    const val kotlin = "1.6.10"
    const val appDistribution = "1.6.10"
    const val core = "1.8.0"
    const val appcompat = "1.5.0"
    const val material = "1.6.1"
    const val junit = "4.13.2"
    const val androidJunit = "1.1.3"
    const val mockWebServer = "4.10.0"
    const val espresso = "3.4.0"
    const val room = "2.4.3"
}

object Project {
    const val constants = ":constants"
    const val data = ":data"
    const val domain = ":domain"
    const val presentation = ":presentation"
}

object Dependencies {
    object Androidx {
        const val core = "androidx.core:core-ktx:${Version.core}"
        const val junit = "androidx.test.ext:junit:${Version.androidJunit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Version.espresso}"
        const val appcompat = "androidx.appcompat:appcompat:${Version.appcompat}"
        const val recyclerView = "androidx.recyclerview:recyclerview:${Version.recyclerView}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
        const val activityKtx = "androidx.activity:activity-ktx:${Version.activityKtx}"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:${Version.fragmentKtx}"
        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.viewModelKtx}"
        const val splashScreen = "androidx.core:core-splashscreen:${Version.splashScreen}"
        const val work = "androidx.work:work-runtime-ktx:${Version.work}"
        const val workHilt = "androidx.hilt:hilt-work:${Version.workHilt}"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Version.swipeRefreshLayout}"
    }

    object Paging {
        const val paging = "androidx.paging:paging-runtime:${Version.paging}"
        const val pagingKtx = "androidx.paging:paging-runtime-ktx:${Version.paging}"
        const val pagingCommon = "androidx.paging:paging-common:${Version.paging}"
        const val pagingCommonKtx = "androidx.paging:paging-common-ktx:${Version.paging}"
    }

    object Hilt {
        const val hiltAndroid = "com.google.dagger:hilt-android:${Version.hilt}"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Version.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Version.hiltCompiler}"
    }

    object Network {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
        const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"
        const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Version.mockWebServer}"
    }

    object Database {
        const val roomKtx = "androidx.room:room-ktx:${Version.room}"
        const val roomRuntime = "androidx.room:room-runtime:${Version.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Version.room}"
        const val roomTesting = "androidx.room:room-testing:${Version.room}"
        const val roomPaging = "androidx.room:room-paging:${Version.room}"
    }

    object Coroutines {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutines}"
    }

    object Coil {
        const val coil = "io.coil-kt:coil:${Version.coil}"
    }

    object Test {
        const val junit = "junit:junit:${Version.junit}"
    }

    object Material {
        const val material = "com.google.android.material:material:${Version.material}"
    }
}