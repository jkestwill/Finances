plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
}

android {
    namespace = "com.jk.exchange_rate_data"
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

        debug {
            buildConfigField("String", "NBRB_API_BASE_URL", "\"https://api.nbrb.by/exrates/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.material)
    implementation(libs.hilt.android)
    implementation(project(":common:common-data:money"))
    kapt(libs.hilt.android.compiler)
    implementation(libs.junit.ktx)
    implementation(libs.junit.ktx)
    implementation(libs.androidx.room.ktx)
    implementation(libs.ktor.client.core)
    testImplementation("androidx.test:runner:1.6.2")
    testImplementation(libs.junit)

    implementation(project(":core:transaction-database"))
    implementation(project(":common:common-utils"))
    implementation(project(":core:money:currencyExchangeApi"))

}