
plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotilinx.serialization)
    id ("com.google.protobuf") version "0.9.3"
}

protobuf{
    protoc {
        artifact = "com.google.protobuf:protoc:3.23.4"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("kotlin") {
                    option("lite")
                }
            }
        }
    }


}



dependencies{
    implementation(libs.squareup.retrofit2.retrofit)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.serialization.kotlinx.converter)
    implementation(libs.retrofit.adapters.result)
    implementation  ("com.google.protobuf:protobuf-kotlin-lite:3.23.4")
    implementation( "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.slf4j.simple)
    implementation(libs.converter.simplexml)
    implementation(":core:common:common-utils")

}
java {
    targetCompatibility = JavaVersion.VERSION_17
    sourceCompatibility=JavaVersion.VERSION_17
}