plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
}

android {
    namespace = "com.jk.transaction_data"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjvm-default=all")
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
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    implementation (libs.androidx.hilt.navigation.compose)
    implementation(project(":core:transaction-database"))
    implementation(project(":common:common-data:transaction"))
    implementation(project(":common:common-data:money"))
    implementation(project(":common:common-utils"))
    implementation(project(":common:common-data:category"))
    implementation(project(":common:common-data:goods"))
    implementation(project(":common:common-data:goods"))

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    // mapper
    kapt(libs.mapstruct.kotlin)
    implementation(libs.mapstruct)
    kapt(libs.mapstruct.processor)

    androidTestImplementation(libs.androidx.core)
    androidTestImplementation(libs.core.ktx)
    androidTestImplementation(libs.junit.ktx)
    androidTestImplementation(libs.androidx.truth)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.espresso.intents)
    androidTestImplementation(libs.androidx.espresso.accessibility)
    androidTestImplementation(libs.androidx.espresso.web)
    androidTestImplementation(libs.androidx.idling.concurrent)

}