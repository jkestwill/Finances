plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.parcelize)
    alias(libs.plugins.jetbrains.kotlin.kapt)
}

android {
    namespace = "com.jk.transaction_common_ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    // mapper
    kapt(libs.mapstruct.kotlin)
    implementation(libs.mapstruct)
    kapt(libs.mapstruct.processor)

    implementation(project(":common:common-ui:category"))
    implementation(project(":common:common-ui:goods"))
    implementation(project(":common:common-data:goods"))
    implementation(project(":common:common-ui:money"))
    implementation(project(":common:common-data:money"))
    implementation(project(":common:common-data:category"))
    implementation(project(":common:common-data:transaction"))
    implementation(project(":common:common-utils"))


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}