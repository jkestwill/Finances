plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
}

android {
    namespace = "com.jk.currency_exchange"
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.hilt.android)
    implementation(project(":core:settings"))
    implementation(project(":common:common-data:money"))

    kapt(libs.hilt.android.compiler)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation (libs.mockito.kotlin)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":core:money:exchange-rate-data"))
    implementation(project(":core:money:currencyExchangeApi"))
    implementation(project(":common:common-ui:money"))
    implementation(project(":common:common-utils-ui"))
    implementation(project(":common:common-utils"))

}