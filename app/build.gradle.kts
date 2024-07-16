plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.dagger.hilt.android)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.jetbrains.kotlin.kapt)
}

android {
    namespace = "com.jk.financehelper"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jk.financehelper"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "NBRB_API_BASE_URL", "\"https://api.nbrb.by/exrates/\"")

    }

    buildTypes {

        debug {
            buildConfigField("String", "NBRB_API_BASE_URL", "\"https://api.nbrb.by/exrates/\"")

        }

        release {
            buildConfigField("String", "NBRB_API_BASE_URL", "\"https://api.nbrb.by/exrates/\"")
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")


    // charts
    implementation(libs.ycharts)
    implementation(libs.compose)


    // room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    implementation(project(":common:common-utils-ui"))
    implementation(project(":common:common-utils"))
    implementation(project(":common:common-data:category"))
    implementation(project(":common:common-data:transaction"))
    implementation(project(":common:common-ui:transaction"))
    implementation(project(":common:common-ui:category"))
    implementation(project(":core:transaction:transaction-data"))
    implementation(project(":core:goods:goods-data"))
    implementation(project(":core:money:exchange-rate-data"))
    implementation(project(":core:money:currencyExchangeApiBy"))
    implementation(project(":core:transaction-database"))
    implementation(project(":core:category:category-data"))
    implementation(project(":features:currency:currency-exchange"))
    implementation(project(":features:category:category-main"))
    implementation(project(":features:goods:goods"))
    implementation(project(":common:common-ui:money"))
    ksp(libs.androidx.room.compiler)

    //dagger hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)

    //navigation
    implementation(libs.androidx.navigation.compose)

    implementation(libs.calendar.compose)
    //pagination
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)



    implementation(libs.skydoves.colorpicker)

    testImplementation(libs.junit)
    testImplementation(libs.truth)
    //androidx test
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.core)
    androidTestImplementation(libs.core.ktx)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.androidx.truth)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.espresso.intents)
    androidTestImplementation(libs.androidx.espresso.accessibility)
    androidTestImplementation(libs.androidx.espresso.web)
    androidTestImplementation(libs.androidx.idling.concurrent)

    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
    //noinspection UseTomlInstead
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}