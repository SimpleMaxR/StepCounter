// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}

//buildscript{
//    ext {
//        compose_version = "1.4.3"
//    }
//
//    repositories {
//        google()
//        mavenCentral()
//    }
//
//    dependencies {
//        classpath(libs.gradle)
//        classpath(libs.kotlin.gradle.plugin)
//    }
//}
//
//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//    }
//}