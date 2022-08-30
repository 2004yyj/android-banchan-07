plugins {
    id ("java-library")
    id ("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(Project.constants))
    implementation (Dependencies.Coroutines.core)
    implementation (Dependencies.Paging.pagingCommon)
    implementation (Dependencies.Paging.pagingCommonKtx)
}