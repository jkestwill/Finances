@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.jetbrains.kotlin.kapt)
}

ksp {
    arg(RoomSchemaArgProvider(File(projectDir, "schemas")))
}

android {
    namespace = "com.jk.transaction_database"
    compileSdk = 35


    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

    }
   // schemaDirectory("$projectDir/schemas")
    buildTypes {
        debug {
            isMinifyEnabled = false

            this.buildConfigField("String","PREPOPULATE_DB_PATH","\"transaction_database/transaction_db.db\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            this.isDefault = true
            this.buildConfigField("String","PREPOPULATE_DB_PATH","\"transaction_db.db\"")

        }

    }
    compileOptions{
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures{
        buildConfig = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.hilt.android)

    kapt(libs.hilt.android.compiler)
    implementation(project(":common:common-utils"))
    ksp(libs.androidx.room.compiler)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.junit.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.truth)
    testImplementation (libs.core.ktx)
}


class RoomSchemaArgProvider(
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    val schemaDir: File
) : CommandLineArgumentProvider {

    override fun asArguments(): Iterable<String> {
        // Note: If you're using KAPT and javac, change the line below to
        // return listOf("-Aroom.schemaLocation=${schemaDir.path}").
        return listOf("room.schemaLocation=${schemaDir.path}")
    }
}