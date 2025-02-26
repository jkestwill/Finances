plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
}

android {
    namespace = "com.jk.goods"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    kotlin {
        jvmToolchain(17)
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    // mapper
    kapt(libs.mapstruct.kotlin)
    implementation(libs.mapstruct)
    kapt(libs.mapstruct.processor)
    implementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)
    implementation(libs.androidx.core)
    implementation(libs.hilt.android)
    implementation(project(":common:common-data:goods"))
    implementation(project(":common:common-data:money"))
    implementation(project(":core:transaction-database"))
    implementation(project(":common:common-utils"))
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.paging.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}