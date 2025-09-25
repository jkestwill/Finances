import com.android.utils.cxx.os.quoteCommandLineArgument
import org.jetbrains.kotlin.fir.expressions.builder.buildArgumentList

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotilinx.serialization)
    alias(libs.plugins.google.protobuf)
    alias(libs.plugins.jetbrains.kotlin.kapt)
}

android {
    namespace = "com.jk.settings"
    compileSdk = 35

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
        jvmToolchain(21)
    }
}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation( libs.google.protobuf.javalite)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
   
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
protobuf {
    protoc {
        artifact ="com.google.protobuf:protoc:3.15.0"
        buildArgumentList {
            quoteCommandLineArgument("--experimental_allow_proto3_optional")
        }

    }
    generateProtoTasks{
        all().forEach {task->

            task.builtins{
                create("java") {
                    option("lite")
                }
            }
        }
    }
}