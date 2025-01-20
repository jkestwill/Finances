plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
}

android {
    namespace = "com.jk.transaction"
    compileSdk = 34
    kotlin {
        jvmToolchain(17)
    }
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


    buildFeatures{
        compose=true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(project(":core:transaction:transaction-data"))
    implementation(project(":common:common-data:transaction"))
    implementation(project(":common:common-ui:transaction"))
    implementation(project(":common:common-ui:transaction"))
    implementation(project(":common:common-data:transaction"))
    implementation(project(":common:common-data:category"))
    implementation(project(":common:common-data:goods"))
    implementation(project(":common:common-ui:goods"))
    implementation(project(":common:common-ui:money"))
    implementation(project(":common:common-data:money"))
    implementation(project(":common:common-utils-ui"))
    implementation(project(":common:shared_res"))
    implementation(project(":core:money:money-data"))
    implementation(project(":common:common-utils"))
    implementation(project(":common:common-ui:category"))
    implementation(project(":core:category:category-data"))
    implementation(project(":core:goods:goods-data"))
    implementation(project(":core:settings"))
    implementation(project(":core:money:exchange-rate-data"))
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    debugImplementation(libs.ui.tooling)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}