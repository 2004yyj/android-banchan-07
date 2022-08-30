plugins {
    id ("java-library")
    id ("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    project(":constants")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    implementation ("androidx.paging:paging-common:3.1.1")
    implementation ("androidx.paging:paging-common-ktx:3.1.1")
}