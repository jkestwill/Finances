plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.jk.transaction"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
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

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}