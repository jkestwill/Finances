plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
kotlin {
    jvmToolchain(17)
}

dependencies{
    implementation(project(":common:common-data:money"))
    implementation(project(":common:common-data:category"))
    implementation(project(":common:common-data:goods"))
}